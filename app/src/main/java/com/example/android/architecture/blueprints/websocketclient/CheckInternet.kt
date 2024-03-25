package com.example.android.architecture.blueprints.websocketclient

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

object CheckInternet {

    private const val HOST_NAME = "8.8.8.8"
    private const val PORT = 53
    private const val TIMEOUT = 1_500

    fun isCheck(listener: (connected: Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
            try {
                Socket().use { it.connect(InetSocketAddress(HOST_NAME, PORT), TIMEOUT) }
                withContext(Dispatchers.Main) {
                    listener(true)
                }
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    listener(false)
                }
            }
        }
    }

    fun isCheck(
        listener: (connected: Boolean) -> Unit,
        hostName: String,
        port: Int,
        timeOut: Int
    ) {
        CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
            try {
                Socket().use { it.connect(InetSocketAddress(hostName, port), timeOut) }
                withContext(Dispatchers.Main) {
                    listener(true)
                }
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    listener(false)
                }
            }
        }
    }
}