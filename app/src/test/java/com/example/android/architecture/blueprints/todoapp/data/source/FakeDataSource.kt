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

package com.example.android.architecture.blueprints.todoapp.data.source

import com.example.android.architecture.blueprints.todoapp.domain.utils.Result
import com.example.android.architecture.blueprints.todoapp.domain.utils.Result.Error
import com.example.android.architecture.blueprints.todoapp.domain.utils.Result.Success
import com.example.android.architecture.blueprints.todoapp.data.source.local.TaskModel

class FakeDataSource(var taskModels: MutableList<TaskModel>? = mutableListOf()) : TasksDataSource {
    override suspend fun getTasks(): Result<List<TaskModel>> {
        taskModels?.let { return Success(it) }
        return Error(
            Exception("Tasks not found")
        )
    }

    override suspend fun getTask(taskId: String): Result<TaskModel> {
        taskModels?.firstOrNull { it.id == taskId }?.let { return Success(it) }
        return Error(
            Exception("Task not found")
        )
    }

    override suspend fun saveTask(taskModel: TaskModel) {
        taskModels?.add(taskModel)
    }

    override suspend fun completeTask(taskModel: TaskModel) {
        taskModels?.firstOrNull { it.id == taskModel.id }?.let { it.isCompleted = true }
    }

    override suspend fun completeTask(taskId: String) {
        taskModels?.firstOrNull { it.id == taskId }?.let { it.isCompleted = true }
    }

    override suspend fun activateTask(taskModel: TaskModel) {
        taskModels?.firstOrNull { it.id == taskModel.id }?.let { it.isCompleted = false }
    }

    override suspend fun activateTask(taskId: String) {
        taskModels?.firstOrNull { it.id == taskId }?.let { it.isCompleted = false }
    }

    override suspend fun clearCompletedTasks() {
        taskModels?.removeIf { it.isCompleted }
    }

    override suspend fun deleteAllTasks() {
        taskModels?.clear()
    }

    override suspend fun deleteTask(taskId: String) {
        taskModels?.removeIf { it.id == taskId }
    }
}
