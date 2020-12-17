package com.english.teacherapp.ui.chat.util

import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.english.teacherapp.databinding.RowChatListBinding
import com.english.teacherapp.util.getTimeToShow
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ChatListRecyclerViewAdapter(
    options: FirestoreRecyclerOptions<ModelChat>,
    private val listener: ChatListClickListener
) : FirestoreRecyclerAdapter<ModelChat, ChatListRecyclerViewAdapter.ViewHolderChat>(options) {

    private var _binding: RowChatListBinding? = null

    inner class ViewHolderChat(private val binding: RowChatListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val db = FirebaseFirestore.getInstance()

        fun onBind(modelChat: ModelChat) {

            listener.sizeNotZero()

            getUserLogoAndName(modelChat.userId)
            val messageText = if (modelChat.lastMessage.length < 28)
                modelChat.lastMessage
            else
                modelChat.lastMessage.take(28) + "..."

            binding.messageTextViewRowChatList.text = messageText

            val user = FirebaseAuth.getInstance().currentUser!!

            val diff = Timestamp.now().seconds - modelChat.lastMessageTime.seconds
            binding.timeTextViewLastMessageRowChat.text = getTimeToShow(diff, binding.root.context)

            if (user.uid != modelChat.lastMessageSender) {
                if (!modelChat.isLastMessageRead) {
                    binding.dotRowChat.visibility = View.VISIBLE
                    binding.messageTextViewRowChatList.setTypeface(null, Typeface.BOLD)
                } else {
                    binding.dotRowChat.visibility = View.GONE
                    binding.messageTextViewRowChatList.setTypeface(null, Typeface.NORMAL)
                }
            } else {
                binding.dotRowChat.visibility = View.GONE
                binding.messageTextViewRowChatList.setTypeface(null, Typeface.NORMAL)
            }

            binding.root.setOnClickListener {
                listener.onChatClick(modelChat.teacherId)
            }
        }

        private fun getUserLogoAndName(userId: String?) {

            if (userId != null) {
                db.collection("users").document(userId).get().addOnSuccessListener {
                    if (it.exists()) {
                        val logo = it.getString("logo")
                        val name = it.getString("name")
                        if (!logo.isNullOrEmpty())
                            Glide.with(binding.root).load(logo).into(binding.logoRowChatList)
                        binding.userNameRowChatList.text = name
                    }
                }
            } else {
                val ti = snapshots.getSnapshot(adapterPosition).getString("teacherId")
                Log.d("NURIKO", "getUserLogoAndName: $ti")
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderChat {
        _binding = RowChatListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderChat(_binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolderChat, position: Int, model: ModelChat) {
        holder.onBind(model)
    }

    interface ChatListClickListener {
        fun onChatClick(teacherId: String)
        fun sizeNotZero()
    }

}