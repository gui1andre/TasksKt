package com.devmasterteam.tasks.service.repository

import android.content.Context
import com.devmasterteam.tasks.service.listener.ApiListener
import com.devmasterteam.tasks.service.model.PersonModel
import com.devmasterteam.tasks.service.repository.remote.PersonService
import com.devmasterteam.tasks.service.repository.remote.RetrofitClient

class PersonRepository(context: Context) : BaseRepository(context) {
    private val remote = RetrofitClient.getService(PersonService::class.java)
    fun login(email: String, password: String, listener: ApiListener<PersonModel>) {
        executeCall(remote.login(email, password), listener)
    }

    fun create(name: String, email: String, password: String, listener: ApiListener<PersonModel>) {
        executeCall(remote.create(email, password, name), listener)
    }
}