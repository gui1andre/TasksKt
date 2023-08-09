package com.devmasterteam.tasks.service.model

import com.google.gson.annotations.SerializedName
import retrofit2.http.Field

class PersonModel(
    @SerializedName("token")
    var token:String,
    @SerializedName("personKey")
    var personKey: String,
    @SerializedName("name")
    var name: String
)