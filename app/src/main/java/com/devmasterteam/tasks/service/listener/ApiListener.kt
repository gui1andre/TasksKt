package com.devmasterteam.tasks.service.listener

interface ApiListener<T> {
    fun onSucess(result: T)
    fun onFailure(message: String)
}