package com.example.android.architecture.blueprints.websocketclient

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private var btn: Button? = null
    private var nickname: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //call UI components  by id
        btn = findViewById<View>(R.id.enterchat) as Button
        nickname = findViewById<View>(R.id.nickname) as EditText
        btn!!.setOnClickListener {
            //if the nickname is not empty go to chatbox activity and add the nickname to the intent extra
            if (nickname!!.text.toString().isNotEmpty()) {
                val i = Intent(
                    this@MainActivity,
                    ChatboxActivity::class.java
                )

                //retreive nickname from EditText and add it to intent extra
                i.putExtra(NICKNAME, nickname!!.text.toString())
                startActivity(i)
            }
        }
    }

    companion object {
        const val NICKNAME = "usernickname"
    }
}