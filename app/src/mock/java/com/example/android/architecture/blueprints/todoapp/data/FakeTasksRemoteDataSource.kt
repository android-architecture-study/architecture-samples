/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.architecture.blueprints.todoapp.data

import com.example.android.architecture.blueprints.todoapp.domain.utils.Result.Error
import com.example.android.architecture.blueprints.todoapp.domain.utils.Result.Success
import com.example.android.architecture.blueprints.todoapp.data.source.TasksDataSource
import com.example.android.architecture.blueprints.todoapp.data.source.local.TaskModel
import com.example.android.architecture.blueprints.todoapp.domain.utils.Result
import java.util.LinkedHashMap

/**
 * Implementation of a remote data source with static access to the data for easy testing.
 */
object FakeTasksRemoteDataSource : TasksDataSource {

    private var tasksServiceData: LinkedHashMap<String, TaskModel> = LinkedHashMap()

    override suspend fun getTask(taskId: String): Result<TaskModel> {
        tasksServiceData[taskId]?.let {
            return Success(it)
        }
        return Error(Exception("Could not find task"))
    }

    override suspend fun getTasks(): Result<List<TaskModel>> {
        return Success(tasksServiceData.values.toList())
    }

    override suspend fun saveTask(taskModel: TaskModel) {
        tasksServiceData[taskModel.id] = taskModel
    }

    override suspend fun completeTask(taskModel: TaskModel) {
        val completedTask = TaskModel(taskModel.title, taskModel.description, true, taskModel.id)
        tasksServiceData[taskModel.id] = completedTask
    }

    override suspend fun completeTask(taskId: String) {
        // Not required for the remote data source.
    }

    override suspend fun activateTask(taskModel: TaskModel) {
        val activeTask = TaskModel(taskModel.title, taskModel.description, false, taskModel.id)
        tasksServiceData[taskModel.id] = activeTask
    }

    override suspend fun activateTask(taskId: String) {
        // Not required for the remote data source.
    }

    override suspend fun clearCompletedTasks() {
        tasksServiceData = tasksServiceData.filterValues {
            !it.isCompleted
        } as LinkedHashMap<String, TaskModel>
    }

    override suspend fun deleteTask(taskId: String) {
        tasksServiceData.remove(taskId)
    }

    override suspend fun deleteAllTasks() {
        tasksServiceData.clear()
    }
}
