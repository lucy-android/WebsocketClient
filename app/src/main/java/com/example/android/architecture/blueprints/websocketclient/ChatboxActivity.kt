package com.example.android.architecture.blueprints.websocketclient


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity


class ChatboxActivity : AppCompatActivity() {

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
    }
}