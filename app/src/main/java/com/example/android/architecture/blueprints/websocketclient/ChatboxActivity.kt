package com.example.android.architecture.blueprints.websocketclient


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.android.architecture.blueprints.websocketclient.client.WebSocketClient
import com.example.android.architecture.blueprints.websocketclient.client.WebSocketListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ChatboxActivity : AppCompatActivity(), WebSocketListener {

    private val webSocketClient = WebSocketClient("ws://10.0.2.2:3000")

    private var Nickname: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatbox)
        Nickname = intent.extras!!.getString(MainActivity.NICKNAME)
        CheckInternet.isCheck {
            Log.d("APP_TAG", "is internet reachable = $it")
        }

        CheckInternet.isCheck({
            Log.d("APP_TAG", "is localhost reachable = $it")
        }, "10.0.2.2", 3_000, 1_500)

        createWebSocketClient()
    }

    private fun createWebSocketClient() {
        GlobalScope.launch(Dispatchers.Main) {
            webSocketClient.connect(this@ChatboxActivity)
        }

    }

    override fun onConnected() {
        Log.d("APP_TAG", "connected!")
    }

    override fun onMessage(message: String) {
        Log.d("APP_TAG", "message: $message")
    }

    override fun onDisconnected() {
        Log.d("APP_TAG", "disconnected!")
    }


}