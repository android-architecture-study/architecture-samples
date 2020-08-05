package com.example.android.architecture.blueprints.todoapp.presentation.mapper

import com.example.android.architecture.blueprints.todoapp.domain.entity.Task
import com.example.android.architecture.blueprints.todoapp.presentation.entity.PresenterEntity

open class PresenterListMapper : PresenterMapper<List<Task>, List<PresenterEntity>> {
    override fun toView(type: List<Task>): List<PresenterEntity> {
        return type.map{
            PresenterEntity(it.title, it.description, it.completed, it.entryid)
        }
    }

    override fun toEntity(type: List<PresenterEntity>): List<Task> {
        return type.map{
            Task(it.title, it.description, it.completed, it.entryid)
        }
    }
}