package com.example.android.architecture.blueprints.websocketclient

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import java.net.URISyntaxException

class ChatboxActivity : AppCompatActivity() {

    private var socket: Socket? = null
    private var Nickname: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatbox)
        Nickname = intent.extras!!.getString(MainActivity.NICKNAME)
        try{
            socket = IO.socket("http://localhost:3000")
            socket?.connect()
            socket?.emit("join", Nickname)
        } catch (e: URISyntaxException){
            e.printStackTrace()
        }
    }
}