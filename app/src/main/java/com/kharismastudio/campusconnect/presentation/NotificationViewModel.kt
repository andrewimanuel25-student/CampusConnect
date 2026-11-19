package com.kharismastudio.campusconnect.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kharismastudio.campusconnect.data.NotificationSocketManager
import com.kharismastudio.campusconnect.data.SocketStatus
import dagger.Provides
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Singleton


@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val socketManager: NotificationSocketManager
) : ViewModel() {

    private val _messages = MutableStateFlow<List<String>>(emptyList())
    val messages: StateFlow<List<String>> = _messages

    private val _socketStatus = MutableStateFlow(SocketStatus.DISCONNECTED)
    val socketStatus: StateFlow<SocketStatus> = _socketStatus

    init {
        viewModelScope.launch {
            socketManager.messages.collect { message ->
                _messages.value = listOf(message) + _messages.value
            }
        }

        viewModelScope.launch {
            socketManager.status.collect { status ->
                _socketStatus.value = status
            }
        }
    }

    fun connect() {
        socketManager.connect()
    }

    fun sendMessage(message: String) {
        if (message.isNotBlank()) {
            socketManager.sendMessage(message)
        }
    }

    fun disconnect() {
        socketManager.disconnect()
    }

    override fun onCleared() {
        socketManager.disconnect()
        super.onCleared()
    }
}

@Provides
@Singleton
fun provideNotificationSocketManager(): NotificationSocketManager {
    return NotificationSocketManager()
}