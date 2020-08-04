package com.example.android.architecture.blueprints.todoapp.presentation.entity

data class PresentationEntity(
        val title: String,
        val description: String,
        var completed: Boolean,
        var entryid: String
)