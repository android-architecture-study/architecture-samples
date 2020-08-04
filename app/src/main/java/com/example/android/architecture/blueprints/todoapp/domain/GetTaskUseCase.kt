package com.example.android.architecture.blueprints.todoapp.domain

import com.example.android.architecture.blueprints.todoapp.domain.entity.Task
import com.example.android.architecture.blueprints.todoapp.domain.repository.TasksRepository
import com.example.android.architecture.blueprints.todoapp.domain.utils.Result
import com.example.android.architecture.blueprints.todoapp.presentation.util.wrapEspressoIdlingResource

class GetTaskUseCase(
    private val tasksRepository: TasksRepository
) {
    suspend operator fun invoke(taskId: String, forceUpdate: Boolean = false): Result<Task> {

        wrapEspressoIdlingResource {
            return tasksRepository.getTask(taskId, forceUpdate)
        }
    }

}