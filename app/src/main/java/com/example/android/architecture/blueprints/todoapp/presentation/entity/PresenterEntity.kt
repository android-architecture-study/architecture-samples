package com.example.android.architecture.blueprints.todoapp.presentation.entity

data class PresenterEntity(
        val title: String,
        val description: String,
        var completed: Boolean,
        var entryid: String
)