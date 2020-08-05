package com.example.android.architecture.blueprints.todoapp.presentation.mapper

import com.example.android.architecture.blueprints.todoapp.domain.entity.Task
import com.example.android.architecture.blueprints.todoapp.presentation.entity.PresenterEntity

open class PresenterEntityMapper : PresenterMapper<Task, PresenterEntity> {
    override fun toView(type: Task): PresenterEntity {
        return PresenterEntity(type.title, type.description, type.completed, type.entryid)
    }

    override fun toEntity(type: PresenterEntity): Task {
        return Task(type.title, type.description, type.completed, type.entryid)
    }
}