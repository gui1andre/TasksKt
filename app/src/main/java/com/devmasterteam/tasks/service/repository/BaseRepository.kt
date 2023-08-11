package com.devmasterteam.tasks.service.repository

import com.devmasterteam.tasks.service.constants.TaskConstants
import com.devmasterteam.tasks.service.listener.ApiListener
import com.google.gson.Gson
import retrofit2.Response

open class BaseRepository {

    private fun failureMessage(message: String): String {
        return Gson().fromJson(message, String::class.java)
    }

    fun <T> handleResponse(response: Response<T>, listener: ApiListener<T>) {
        if (response.code() == TaskConstants.HTTP.SUCCESS) {
            response.body()?.let { listener.onSucess(it) }
        } else {
            listener.onFailure(failureMessage(response.errorBody()!!.string()))
        }
    }
}