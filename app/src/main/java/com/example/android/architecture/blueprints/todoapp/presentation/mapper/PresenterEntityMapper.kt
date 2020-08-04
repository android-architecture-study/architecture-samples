package com.example.android.architecture.blueprints.todoapp.presentation.mapper

import com.example.android.architecture.blueprints.todoapp.domain.entity.Task
import com.example.android.architecture.blueprints.todoapp.presentation.entity.PresentationEntity

open class PresenterEntityMapper : PresenterMapper<Task, PresentationEntity> {
    override fun toView(type: Task): PresentationEntity {
       return PresentationEntity(type.title, type.description, type.completed, type.entryid)
    }
}