package com.english.teacherapp.ui.chat

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.english.teacherapp.R
import com.english.teacherapp.databinding.FragmentChatBinding
import com.english.teacherapp.ui.chat.util.ChatListRecyclerViewAdapter
import com.english.teacherapp.ui.chat.util.ModelChat
import com.english.teacherapp.util.EXTRA_TEACHER_ID
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class ChatFragment : Fragment(), ChatListRecyclerViewAdapter.ChatListClickListener {

    private var _binding: FragmentChatBinding? = null
    private val binding: FragmentChatBinding get() = _binding!!

    private var adapter: ChatListRecyclerViewAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onStart() {
        super.onStart()

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null)
            initRecyclerView(user)
    }

    private fun initRecyclerView(user: FirebaseUser) {
        binding.recyclerView.setHasFixedSize(true)

        val query =
            FirebaseFirestore.getInstance().collection("chats").whereEqualTo("userId", user.uid)
                .orderBy("lastMessageTime", Query.Direction.DESCENDING)
        val options: FirestoreRecyclerOptions<ModelChat> =
            FirestoreRecyclerOptions.Builder<ModelChat>().setQuery(query, ModelChat::class.java)
                .build()
        adapter = ChatListRecyclerViewAdapter(options, this)
        binding.recyclerView.adapter = adapter
        adapter?.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter?.stopListening()
    }

    override fun onChatClick(teacherId: String) {
        val intent = Intent(requireContext(), PrivateChatActivity::class.java)
        intent.putExtra(EXTRA_TEACHER_ID, teacherId)
        startActivity(intent)
    }

    override fun sizeNotZero() {
        binding.chatEmptyTextView.visibility = View.GONE
    }

}