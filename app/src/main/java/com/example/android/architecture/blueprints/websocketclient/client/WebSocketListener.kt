package com.example.android.architecture.blueprints.websocketclient.client

interface WebSocketListener {
    fun onConnected()
    fun onMessage(message: String)
    fun onDisconnected()
}