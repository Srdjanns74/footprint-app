package com.example.footprint.network

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.footprint.R

data class ChatMessage(
    val message: String,
    val isUser: Boolean
)

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.MessageViewHolder>() {

    private val messages = mutableListOf<ChatMessage>()

    fun submitList(newList: List<ChatMessage>) {
        messages.clear()
        messages.addAll(newList)
        notifyDataSetChanged()
    }

    fun getMessages(): List<ChatMessage> {
        return messages.toList()
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].isUser) 0 else 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val layoutId = if (viewType == 0) {
            R.layout.item_user_message
        } else {
            R.layout.item_bot_message
        }
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position].message)
    }

    override fun getItemCount(): Int = messages.size

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageText: TextView = itemView.findViewById(R.id.userMessageText)

        fun bind(text: String) {
            messageText.text = text
        }
    }
}
