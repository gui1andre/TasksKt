package com.devmasterteam.tasks.service.model

class ValidationModel(
    val message: String = ""
){
    private var status = true
    private var validationMessage = ""

    init {
        if(message != ""){
            status = false
            validationMessage = message
        }
    }

    fun status() = status
    fun message() = validationMessage
}