package com.devmasterteam.tasks.service.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Priority")
class PriorityModel(
    @SerializedName("Id")
    @ColumnInfo(name = "Id")
    @PrimaryKey
    var id: Int,
    @SerializedName("Description")
    @ColumnInfo(name = "Description")
    var description: String
)