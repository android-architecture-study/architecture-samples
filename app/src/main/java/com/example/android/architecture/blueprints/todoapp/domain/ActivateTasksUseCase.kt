package com.example.android.architecture.blueprints.todoapp.domain

import com.example.android.architecture.blueprints.todoapp.domain.entity.Task
import com.example.android.architecture.blueprints.todoapp.domain.repository.TasksRepository
import com.example.android.architecture.blueprints.todoapp.presentation.util.wrapEspressoIdlingResource

class ActivateTaskUseCase(
    private val tasksRepository: TasksRepository
) {
    //이름없이 호출되는 함수
    suspend operator fun invoke(task: Task) {

        wrapEspressoIdlingResource {
            tasksRepository.activateTask(task)
        }
    }
}