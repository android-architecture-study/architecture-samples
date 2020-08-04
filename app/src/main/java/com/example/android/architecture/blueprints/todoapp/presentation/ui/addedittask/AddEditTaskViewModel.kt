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

package com.example.android.architecture.blueprints.todoapp.presentation.ui.addedittask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.architecture.blueprints.todoapp.Event
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.data.mapper.TaskEntityMapper
import com.example.android.architecture.blueprints.todoapp.data.mapper.TaskListMapper
import com.example.android.architecture.blueprints.todoapp.domain.utils.Result.Success
import com.example.android.architecture.blueprints.todoapp.data.source.local.TaskModel
import com.example.android.architecture.blueprints.todoapp.domain.GetTaskUseCase
import com.example.android.architecture.blueprints.todoapp.domain.SaveTaskUseCase
import kotlinx.coroutines.launch

/**
 * ViewModel for the Add/Edit screen.
 * Todo Title과 task를 입력하는 곳 (New Task)
 * add 와 edit 이 같은 뷰모델과 프래그먼트를 사용
 *  https://vagabond95.me/2020/03/13/live_data_with_event_issue/
 */
class AddEditTaskViewModel(
    private val getTaskUseCase: GetTaskUseCase,
    private val saveUseCase: SaveTaskUseCase
) : ViewModel() {

    private val taskEntityMapper = TaskEntityMapper()

    // Two-way databinding, exposing MutableLiveData
    val title = MutableLiveData<String>()

    // Two-way databinding, exposing MutableLiveData
    val description = MutableLiveData<String>()

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    // IO 의 경우 데이터를 Event 로 한번 더 래핑 (리스너)
    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    private val _taskUpdatedEvent = MutableLiveData<Event<Unit>>()
    val taskUpdatedEvent: LiveData<Event<Unit>> = _taskUpdatedEvent //event 를 감지하여, 변화를 감지하면 addEditTaskFragment 에서 TasksFragment 로 이동한다.

    private var taskId: String? = null

    private var isNewTask: Boolean = false

    private var isDataLoaded = false

    private var taskCompleted = false

    fun start(taskId: String?) {
        if (_dataLoading.value == true) {
            return
        }

        this.taskId = taskId
        if (taskId == null) {
            // No need to populate, it's a new task
            isNewTask = true
            return
        }
        if (isDataLoaded) {
            // No need to populate, already have data.
            return
        }

        isNewTask = false
        _dataLoading.value = true

        viewModelScope.launch {
            getTaskUseCase(taskId).let { result ->
                if (result is Success) {
                    onTaskLoaded(taskEntityMapper.toModel(result.data))
                } else {
                    onDataNotAvailable()
                }
            }
        }
    }

    private fun onTaskLoaded(taskModel: TaskModel) {
        title.value = taskModel.title
        description.value = taskModel.description
        taskCompleted = taskModel.isCompleted
        _dataLoading.value = false
        isDataLoaded = true
    }

    private fun onDataNotAvailable() {
        _dataLoading.value = false
    }

    // Called when clicking on fab.
    //todo save
    fun saveTask() {
        val currentTitle = title.value
        val currentDescription = description.value

        if (currentTitle == null || currentDescription == null) {
            _snackbarText.value = Event(R.string.empty_task_message) //래핑 한번 해주넹
            return
        }
        if (TaskModel(currentTitle, currentDescription).isEmpty) {
            _snackbarText.value = Event(R.string.empty_task_message)
            return
        }

        val currentTaskId = taskId //taskid 는 tasks fragment 에서 addedittask fragment로 넘어올 때 bundle마냥 가지고 옴.
        if (isNewTask || currentTaskId == null) {
            createTask(TaskModel(currentTitle, currentDescription)) //새로운 Todo 라면 create
        } else {
            val task = TaskModel(currentTitle, currentDescription, taskCompleted, currentTaskId)
            updateTask(task) //기존 Todo 라면 update (title, desc, check boc 여부, 저장된 id)
        }
    }

    private fun createTask(newTaskModel: TaskModel) = viewModelScope.launch {
        saveUseCase(taskEntityMapper.toEntity(newTaskModel))
        _taskUpdatedEvent.value = Event(Unit)
    }

    private fun updateTask(taskModel: TaskModel) {
        if (isNewTask) {
            throw RuntimeException("updateTask() was called but task is new.")
        }
        viewModelScope.launch { //lifecycle scope를 viewmodel로 제한한다.
            saveUseCase(taskEntityMapper.toEntity(taskModel))
            _taskUpdatedEvent.value = Event(Unit)
        }
    }
}
