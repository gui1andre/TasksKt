package com.devmasterteam.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devmasterteam.tasks.service.constants.TaskConstants
import com.devmasterteam.tasks.service.repository.SecurityPreferences

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val securityPreferences = SecurityPreferences(application.applicationContext)
    private val _user = MutableLiveData<String>()
    val user: LiveData<String> = _user
    fun logout(){
        securityPreferences.remove(TaskConstants.SHARED.PERSON_KEY)
        securityPreferences.remove(TaskConstants.SHARED.TOKEN_KEY)
        securityPreferences.remove(TaskConstants.SHARED.PERSON_NAME)
    }
    fun getUserName(){
        val name = securityPreferences.get(TaskConstants.SHARED.PERSON_NAME)
        _user.value = name

    }
}