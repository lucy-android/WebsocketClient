package com.example.android.architecture.blueprints.websocketclient

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket


class ChatboxActivity : AppCompatActivity() {

    private var socket: Socket? = null
    private var Nickname: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatbox)
        Nickname = intent.extras!!.getString(MainActivity.NICKNAME)
        CheckInternet.isCheck {
            Log.d("APP_TAG", "is internet reachable = $it")
        }

        CheckInternet.isCheck( {
            Log.d("APP_TAG", "is localhost reachable = $it")
        }, "10.0.2.2", 3_000, 1_500)

        try {
            socket = IO.socket("http://10.0.2.2:3000/")

            socket?.connect()
            Log.d("APP_TAG", "is the socket connected: ${socket?.connected()}")
            socket?.emit("join ", Nickname)
        } catch (e: Exception) {
            Log.d("APP_TAG", "onCreate: ${e.cause}")
            e.printStackTrace()
        }
    }
}