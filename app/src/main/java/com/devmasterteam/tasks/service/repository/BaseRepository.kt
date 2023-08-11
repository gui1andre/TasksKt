package com.devmasterteam.tasks.service.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.devmasterteam.tasks.R
import com.devmasterteam.tasks.service.constants.TaskConstants
import com.devmasterteam.tasks.service.listener.ApiListener
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class BaseRepository(val context: Context) {

    private fun failureMessage(message: String): String {
        return Gson().fromJson(message, String::class.java)
    }

    fun <T> executeCall(call: Call<T>, listener: ApiListener<T>) {
        if (connectionAvailable()) {
            call.enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    if (response.code() == TaskConstants.HTTP.SUCCESS) {
                        response.body()?.let { listener.onSucess(it) }
                    } else {
                        listener.onFailure(failureMessage(response.errorBody()!!.string()))
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    listener.onFailure(context.getString(R.string.ERROR_UNEXPECTED))
                }

            })
        } else {
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
        }
    }

    private fun connectionAvailable(): Boolean {
        var result = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNet = cm.activeNetwork ?: return false
            val netWorkCapabilities = cm.getNetworkCapabilities(activeNet) ?: return false
            result = when {
                netWorkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                netWorkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }

        } else {
            if (cm.activeNetworkInfo != null) {
                result = when (cm.activeNetworkInfo!!.type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return result
    }
}
