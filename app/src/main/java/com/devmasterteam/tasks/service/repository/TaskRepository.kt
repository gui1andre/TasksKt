package com.devmasterteam.tasks.service.repository

import android.content.Context
import com.devmasterteam.tasks.service.listener.ApiListener
import com.devmasterteam.tasks.service.model.TaskModel
import com.devmasterteam.tasks.service.repository.remote.RetrofitClient
import com.devmasterteam.tasks.service.repository.remote.TaskService

class TaskRepository(context: Context) : BaseRepository(context) {
    val remote = RetrofitClient.getService(TaskService::class.java)

    fun create(task: TaskModel, listener: ApiListener<Boolean>) {
        val call = remote.create(task.priorityId, task.description, task.dueDate, task.complete)
        executeCall(call, listener)
    }
    fun update(task: TaskModel, listener: ApiListener<Boolean>){
        val call = remote.update(task.id,task.priorityId,task.description, task.dueDate,task.complete)
        executeCall(call, listener)
    }

    fun complete(id: Int, listener: ApiListener<Boolean>){
        executeCall(remote.complete(id), listener)
    }

    fun undo(id: Int, listener: ApiListener<Boolean>){
        executeCall(remote.undo(id), listener)
    }
    fun delete(id: Int, listener: ApiListener<Boolean>){
        executeCall(remote.delete(id), listener)
    }
    fun load(id: Int, listener: ApiListener<TaskModel>) {
        executeCall(remote.load(id), listener)
    }

    fun list(listener: ApiListener<List<TaskModel>>) {
        executeCall(remote.list(), listener)
    }

    fun listNext(listener: ApiListener<List<TaskModel>>) {
        executeCall(remote.listNext(), listener)
    }

    fun listOverdue(listener: ApiListener<List<TaskModel>>) {
        executeCall(remote.listOverdue(), listener)
    }

}