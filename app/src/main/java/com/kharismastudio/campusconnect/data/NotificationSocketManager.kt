package com.kharismastudio.campusconnect.data

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.util.concurrent.TimeUnit

enum class SocketStatus {
    DISCONNECTED,
    CONNECTING,
    CONNECTED,
    ERROR
}

class NotificationSocketManager {
    private val client = OkHttpClient.Builder()
        .readTimeout(0, TimeUnit.MILLISECONDS)
        .build()

    private var webSocket: WebSocket? = null

    private val _messages = MutableSharedFlow<String>(
        extraBufferCapacity = 20
    )
    val messages = _messages.asSharedFlow()

    private val _status = MutableSharedFlow<SocketStatus>(
        replay = 1,
        extraBufferCapacity = 1
    )
    val status = _status.asSharedFlow()

    fun connect() {
        if (webSocket != null) return

        _status.tryEmit(SocketStatus.CONNECTING)

        val request = Request.Builder()
            .url("wss://echo.websocket.events")
            .build()

        webSocket = client.newWebSocket(
            request,
            object : WebSocketListener() {
                override fun onOpen(webSocket: WebSocket, response: Response) {
                    _status.tryEmit(SocketStatus.CONNECTED)
                }

                override fun onMessage(webSocket: WebSocket, text: String) {
                    _messages.tryEmit(text)
                }

                override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                    _status.tryEmit(SocketStatus.DISCONNECTED)
                }

                override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                    this@NotificationSocketManager.webSocket = null
                    _status.tryEmit(SocketStatus.DISCONNECTED)
                }

                override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                    this@NotificationSocketManager.webSocket = null
                    _status.tryEmit(SocketStatus.ERROR)
                }
            }
        )
    }

    fun sendMessage(message: String): Boolean {
        return webSocket?.send(message) ?: false
    }

    fun disconnect() {
        webSocket?.close(1000, "Fitur notifikasi ditutup")
        webSocket = null
        _status.tryEmit(SocketStatus.DISCONNECTED)
    }
}
