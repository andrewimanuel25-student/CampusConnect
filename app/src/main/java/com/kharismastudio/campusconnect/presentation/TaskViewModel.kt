package com.kharismastudio.campusconnect.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kharismastudio.campusconnect.data.repository.TaskRepository
import com.kharismastudio.shared.Tugas
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {
    private val _tasks = MutableStateFlow<List<Tugas>>(emptyList())
    val tasks: StateFlow<List<Tugas>> = _tasks.asStateFlow()
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        viewModelScope.launch {
            repository.observeTasks()
                .catch { error ->
                    _errorMessage.value =
                        error.localizedMessage
                            ?: "Gagal mengambil data tugas."
                }
                .collect { data ->
                    _tasks.value = data
                }
        }
    }
}