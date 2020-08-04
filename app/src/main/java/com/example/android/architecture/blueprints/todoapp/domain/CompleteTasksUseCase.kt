package com.example.android.architecture.blueprints.todoapp.domain

import com.example.android.architecture.blueprints.todoapp.domain.entity.Task
import com.example.android.architecture.blueprints.todoapp.domain.repository.TasksRepository
import com.example.android.architecture.blueprints.todoapp.presentation.util.wrapEspressoIdlingResource

class CompleteTaskUseCase(
    private val tasksRepository: TasksRepository
) {
    suspend operator fun invoke(task: Task) {

        wrapEspressoIdlingResource {
            tasksRepository.completeTask(task)
        }
    }
}