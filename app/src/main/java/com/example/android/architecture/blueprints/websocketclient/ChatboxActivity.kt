package com.example.android.architecture.blueprints.websocketclient

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.InetAddress


class ChatboxActivity : AppCompatActivity() {

    private var socket: Socket? = null
    private var Nickname: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatbox)
        Nickname = intent.extras!!.getString(MainActivity.NICKNAME)

        CoroutineScope(Dispatchers.Default).launch {
            val host = InetAddress.getLoopbackAddress()
            Log.d("APP_TAG", "is the address reachable: host.isReachable(1000) = ${host.isReachable(1000)}")
        }

        try {
            socket = IO.socket("http://10.0.2.2:3000")

            socket?.connect()
            Log.d("APP_TAG", "is the socket connected: ${socket?.connected()}")
            socket?.emit("join ", Nickname)
        } catch (e: Exception) {
            Log.d("APP_TAG", "onCreate: ${e.cause}")
            e.printStackTrace()
        }
    }


    fun isAddressReachable(address: String): Boolean {
        return try {
            // Start the process to ping the address
            val process = Runtime.getRuntime().exec("/system/bin/ping -c 1 $address")

            // Wait for the process to finish
            val exitCode = process.waitFor()

            // Check if the ping was successful (exit code 0 means success)
            exitCode == 0
        } catch (e: IOException) {
            e.printStackTrace()
            false
        } catch (e: InterruptedException) {
            e.printStackTrace()
            false
        }
    }
}