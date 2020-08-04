package com.example.android.architecture.blueprints.todoapp.domain.entity

data class Task(
        val title: String,
        val description: String,
        var completed: Boolean,
        var entryid: String
)