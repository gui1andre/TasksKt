package com.devmasterteam.tasks.service.repository

import PriorityService
import android.content.Context
import com.devmasterteam.tasks.service.repository.remote.RetrofitClient

class PriorityRepository(val context: Context) {

    val remote = RetrofitClient.getService(PriorityService::class.java)
    fun list(){
        val call = remote.list()
        TODO("Continua")
    }
}