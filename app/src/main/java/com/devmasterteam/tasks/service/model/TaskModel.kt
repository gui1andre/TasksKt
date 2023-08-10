package com.devmasterteam.tasks.service.model

import com.google.gson.annotations.SerializedName

class TaskModel(
    @SerializedName("Id")
    val id: Int,
    @SerializedName("PriorityId")
    var priorityId: Int,
    @SerializedName("Description")
    var description: String,
    @SerializedName("DueDate")
    var dueDate: String,
    @SerializedName("Complete")
    var complete: Boolean
)