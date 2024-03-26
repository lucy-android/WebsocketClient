package com.example.android.architecture.blueprints.websocketclient


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import dev.gustavoavila.websocketclient.WebSocketClient
import java.net.URI
import java.net.URISyntaxException


class ChatboxActivity : AppCompatActivity() {

    private var Nickname: String? = null

    private var webSocketClient: WebSocketClient? = null
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

        createWebSocketClient()
    }

    private fun createWebSocketClient() {
        val uri: URI = try {
            // Connect to local host
            URI("ws://10.0.2.2:3000")
        } catch (e: URISyntaxException) {
            e.printStackTrace()
            return
        }
        webSocketClient = object : WebSocketClient(uri) {
            override fun onOpen() {
                Log.i("WebSocket", "Session is starting")
                webSocketClient?.send("Hello World!")
            }

            override fun onTextReceived(s: String) {
                Log.i("WebSocket", "Message received")
                runOnUiThread {
                    try {

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onBinaryReceived(data: ByteArray?) {}
            override fun onPingReceived(data: ByteArray?) {}
            override fun onPongReceived(data: ByteArray?) {}
            override fun onException(e: Exception) {
                Log.e("WebSocket", e.localizedMessage.toString())
            }

            override fun onCloseReceived(reason: Int, description: String?) {
                Log.d("WebSocket", reason.toString())
            }


        }
        (webSocketClient as WebSocketClient).setConnectTimeout(10000)
        (webSocketClient as WebSocketClient).setReadTimeout(60000)
        (webSocketClient as WebSocketClient).enableAutomaticReconnection(5000)
        (webSocketClient as WebSocketClient).connect()
    }
}