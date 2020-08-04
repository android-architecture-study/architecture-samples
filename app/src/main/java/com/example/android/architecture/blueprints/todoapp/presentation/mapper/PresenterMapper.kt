package com.example.android.architecture.blueprints.todoapp.presentation.mapper

interface PresenterMapper <V, D> {
    fun toView(type: V): D
}