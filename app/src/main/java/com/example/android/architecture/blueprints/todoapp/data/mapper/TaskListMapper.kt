package com.example.android.architecture.blueprints.todoapp.data.mapper

import com.example.android.architecture.blueprints.todoapp.data.source.local.TaskModel
import com.example.android.architecture.blueprints.todoapp.domain.entity.Task

open class TaskListMapper : TaskMapper<List<TaskModel>, List<Task>>{

    override fun toEntity(type: List<TaskModel>):  List<Task> { //TaskModel to Task
            return type.map { taskModel ->
                Task(taskModel.title, taskModel.description, taskModel.isCompleted, taskModel.id)
            }
    }

    override fun toModel(type: List<Task>): List<TaskModel> { //Task to TaskModel

            return type.map{ task ->
                TaskModel(task.title, task.description, task.completed, task.entryid)
            }
        }
    }
