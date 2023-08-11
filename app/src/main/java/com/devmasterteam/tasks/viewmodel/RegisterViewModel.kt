package com.devmasterteam.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devmasterteam.tasks.service.constants.TaskConstants
import com.devmasterteam.tasks.service.listener.ApiListener
import com.devmasterteam.tasks.service.model.PersonModel
import com.devmasterteam.tasks.service.model.ValidationModel
import com.devmasterteam.tasks.service.repository.PersonRepository
import com.devmasterteam.tasks.service.repository.SecurityPreferences
import com.devmasterteam.tasks.service.repository.remote.RetrofitClient

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val prefs = SecurityPreferences(application.applicationContext)
    private val personRepository = PersonRepository(application.applicationContext)
    private val _valid = MutableLiveData<ValidationModel>()
    val valid: LiveData<ValidationModel> = _valid

    fun create(name: String, email: String, password: String) {
        personRepository.create(name, email, password, object : ApiListener<PersonModel> {
            override fun onSucess(result: PersonModel) {
                prefs.store(TaskConstants.SHARED.PERSON_NAME, result.name)
                prefs.store(TaskConstants.SHARED.PERSON_KEY, result.personKey)
                prefs.store(TaskConstants.SHARED.TOKEN_KEY, result.token)

                RetrofitClient.addHeaders(result.token, result.personKey)
                _valid.value = ValidationModel()
            }

            override fun onFailure(message: String) {
                _valid.value = ValidationModel(message)
            }
        })

    }

}