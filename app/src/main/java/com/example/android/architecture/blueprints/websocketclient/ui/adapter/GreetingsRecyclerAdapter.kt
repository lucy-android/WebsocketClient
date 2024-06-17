package com.example.android.architecture.blueprints.websocketclient.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.architecture.blueprints.websocketclient.databinding.ItemGreetingBinding

class GreetingsRecyclerAdapter:
    ListAdapter<Contents, GreetingsRecyclerAdapter.GreetingsViewHolder>(DiffCallback()) {

    inner class GreetingsViewHolder(
        private var binding:
        ItemGreetingBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(contents: Contents) {
            with(binding) {
                binding.textView.text = contents.text
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GreetingsRecyclerAdapter.GreetingsViewHolder {
        return GreetingsViewHolder(ItemGreetingBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }


    override fun onBindViewHolder(holder: GreetingsViewHolder, position: Int) {
       val data = getItem(position)
        holder.bind(data)
    }
}

data class Contents(
    val text: String
)

class DiffCallback : DiffUtil.ItemCallback<Contents>() {
    override fun areItemsTheSame(oldItem: Contents, newItem: Contents): Boolean {
        return oldItem.text == newItem.text
    }

    override fun areContentsTheSame(oldItem: Contents, newItem: Contents): Boolean {
        return oldItem == newItem
    }
}