package com.english.teacherapp.ui.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.english.teacherapp.databinding.RowNotesBinding
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class NotesRecyclerViewAdapter(
    options: FirestoreRecyclerOptions<ModelNote>,
    private val listener: NotesClickListener
) : FirestoreRecyclerAdapter<ModelNote, NotesRecyclerViewAdapter.ViewHolderN>(options) {


    private var _binding: RowNotesBinding? = null

    inner class ViewHolderN(private val binding: RowNotesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(modelNote: ModelNote) {
            binding.topicName.text = modelNote.topic

            binding.root.setOnClickListener {
                listener.onClickNote(modelNote)
            }

            binding.root.setOnLongClickListener {
                listener.onDeleteNote(adapterPosition)
                return@setOnLongClickListener true
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderN {
        _binding = RowNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderN(_binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolderN, position: Int, model: ModelNote) {
        holder.onBind(model)
    }

    interface NotesClickListener {
        fun onDeleteNote(position: Int)
        fun onClickNote(modelNote: ModelNote)
    }
}