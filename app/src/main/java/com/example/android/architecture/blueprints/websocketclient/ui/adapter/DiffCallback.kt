package com.example.android.architecture.blueprints.websocketclient.ui.adapter

import androidx.recyclerview.widget.DiffUtil

class DiffCallback : DiffUtil.ItemCallback<Contents>() {
    override fun areItemsTheSame(oldItem: Contents, newItem: Contents): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Contents, newItem: Contents): Boolean {
        return oldItem == newItem
    }
}