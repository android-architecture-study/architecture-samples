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

package com.example.android.architecture.blueprints.todoapp.presentation.ui.statistics

import com.example.android.architecture.blueprints.todoapp.data.source.local.TaskModel

/**
 * Function that does some trivial computation. Used to showcase unit tests.
 */
internal fun getActiveAndCompletedStats(taskModels: List<TaskModel>?): StatsResult {

    return if (taskModels == null || taskModels.isEmpty()) {
        StatsResult(0f, 0f)
    } else {
        val totalTasks = taskModels.size
        val numberOfActiveTasks = taskModels.count { it.isActive }
        StatsResult(
            activeTasksPercent = 100f * numberOfActiveTasks / taskModels.size,
            completedTasksPercent = 100f * (totalTasks - numberOfActiveTasks) / taskModels.size
        )
    }
}

data class StatsResult(val activeTasksPercent: Float, val completedTasksPercent: Float)
