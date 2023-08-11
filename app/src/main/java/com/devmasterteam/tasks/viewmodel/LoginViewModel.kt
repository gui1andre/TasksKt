package com.devmasterteam.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devmasterteam.tasks.service.constants.TaskConstants
import com.devmasterteam.tasks.service.helper.BiometricHelper
import com.devmasterteam.tasks.service.listener.ApiListener
import com.devmasterteam.tasks.service.model.PersonModel
import com.devmasterteam.tasks.service.model.PriorityModel
import com.devmasterteam.tasks.service.model.ValidationModel
import com.devmasterteam.tasks.service.repository.PersonRepository
import com.devmasterteam.tasks.service.repository.PriorityRepository
import com.devmasterteam.tasks.service.repository.SecurityPreferences
import com.devmasterteam.tasks.service.repository.remote.RetrofitClient

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val prefs = SecurityPreferences(application.applicationContext)
    private val personRepository = PersonRepository(application.applicationContext)
    private val priorityRepository = PriorityRepository(application.applicationContext)
    private val _login = MutableLiveData<ValidationModel>()
    val login: LiveData<ValidationModel> = _login

    private val _loggedUser = MutableLiveData<Boolean>()
    val loggedUser: LiveData<Boolean> = _loggedUser

    /**
     * Faz login usando API
     */
    fun doLogin(email: String, password: String) {
        personRepository.login(email, password, object : ApiListener<PersonModel> {
            override fun onSucess(result: PersonModel) {
                prefs.store(TaskConstants.SHARED.PERSON_NAME, result.name)
                prefs.store(TaskConstants.SHARED.PERSON_KEY, result.personKey)
                prefs.store(TaskConstants.SHARED.TOKEN_KEY, result.token)

                RetrofitClient.addHeaders(result.token, result.personKey)
                _login.value = ValidationModel()
            }

            override fun onFailure(message: String) {
                _login.value = ValidationModel(message)
            }
        })
    }

    /**
     * Verifica se usuário está logado
     */
    fun verifyAuthentication() {
        val token = prefs.get(TaskConstants.SHARED.TOKEN_KEY)
        val personKey = prefs.get(TaskConstants.SHARED.PERSON_KEY)

        RetrofitClient.addHeaders(token, personKey)
        val logged = (token.isNotEmpty() && personKey.isNotEmpty())
        if (!logged) {
            priorityRepository.list(object : ApiListener<List<PriorityModel>> {
                override fun onSucess(result: List<PriorityModel>) {
                    priorityRepository.save(result)
                }

                override fun onFailure(message: String) {
                    TODO("Not yet implemented")
                }
            })
        }
        _loggedUser.value = (logged && BiometricHelper.isBiometricAvailable(getApplication()))
    }
}