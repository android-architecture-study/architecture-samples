package com.example.android.architecture.blueprints.todoapp.data.mapper

interface TaskMapper <E, D> {

    fun toEntity(type: E): D

    fun toModel(type: D): E

}