package com.example.android.architecture.blueprints.websocketclient.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.architecture.blueprints.websocketclient.databinding.ItemMessageBinding

class MessagesRecyclerAdapter :
    ListAdapter<Contents, MessagesRecyclerAdapter.MessagesViewHolder>(DiffCallback()) {

    inner class MessagesViewHolder(
        private var binding:
        ItemMessageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(contents: Contents) {
            binding.textView.text = contents.text
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MessagesRecyclerAdapter.MessagesViewHolder {
        return MessagesViewHolder(
            ItemMessageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: MessagesViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }
}