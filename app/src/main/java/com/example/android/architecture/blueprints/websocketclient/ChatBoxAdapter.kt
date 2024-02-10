package com.example.android.architecture.blueprints.websocketclient

import android.view.LayoutInflater

import android.view.View

import android.view.ViewGroup

import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView


class ChatBoxAdapter(private val MessageList: List<Message>) :
    RecyclerView.Adapter<ChatBoxAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nickname: TextView
        var message: TextView

        init {
            nickname = view.findViewById<View>(R.id.nickname) as TextView
            message = view.findViewById<View>(R.id.message) as TextView
        }
    }

    override fun getItemCount(): Int {
        return MessageList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val (nickname, message) = MessageList[position]
        holder.nickname.text = nickname
        holder.message.text = message
    }
}