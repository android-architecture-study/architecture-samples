package com.example.android.architecture.blueprints.todoapp.domain

import com.example.android.architecture.blueprints.todoapp.domain.utils.Result.Success
import com.example.android.architecture.blueprints.todoapp.domain.entity.Task
import com.example.android.architecture.blueprints.todoapp.domain.repository.TasksRepository
import com.example.android.architecture.blueprints.todoapp.domain.utils.Result
import com.example.android.architecture.blueprints.todoapp.presentation.ui.tasks.TasksFilterType
import com.example.android.architecture.blueprints.todoapp.presentation.ui.tasks.TasksFilterType.ACTIVE_TASKS
import com.example.android.architecture.blueprints.todoapp.presentation.ui.tasks.TasksFilterType.ALL_TASKS
import com.example.android.architecture.blueprints.todoapp.presentation.ui.tasks.TasksFilterType.COMPLETED_TASKS
import com.example.android.architecture.blueprints.todoapp.presentation.util.wrapEspressoIdlingResource

class GetTasksUseCase(
    private val tasksRepository: TasksRepository
) {
    suspend operator fun invoke(
        forceUpdate: Boolean = false,
        currentFiltering: TasksFilterType = ALL_TASKS
    ): Result<List<Task>> {

        wrapEspressoIdlingResource {
            val tasksResult = tasksRepository.getTasks(forceUpdate)

            // Filter tasks
            if (tasksResult is Success && currentFiltering != ALL_TASKS) {
                val tasks = tasksResult.data

                val tasksToShow = mutableListOf<Task>()
                // We filter the tasks based on the requestType
                for (task in tasks) {
                    when (currentFiltering) {
                        ACTIVE_TASKS -> if (!task.completed) {
                            tasksToShow.add(task)
                        }
                        COMPLETED_TASKS -> if (task.completed) {
                            tasksToShow.add(task)
                        }
                        else -> NotImplementedError()
                    }
                }
                return Success(tasksToShow)
            }
            return tasksResult
        }
    }

}