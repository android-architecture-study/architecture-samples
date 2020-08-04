package com.example.android.architecture.blueprints.todoapp.data.mapper

import com.example.android.architecture.blueprints.todoapp.data.source.local.TaskModel
import com.example.android.architecture.blueprints.todoapp.domain.entity.Task

open class TaskEntityMapper : TaskMapper<TaskModel, Task> {

    override fun toEntity(type: TaskModel): Task { //TaskModel to Task
        return Task(type.title, type.description, type.isCompleted, type.id)
    }

    override fun toModel(type: Task): TaskModel { //Task to TaskModel
        return TaskModel(type.title, type.description, type.completed, type.entryid)
    }
}