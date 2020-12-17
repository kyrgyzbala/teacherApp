package com.english.teacherapp.ui.quizzes.util

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.english.teacherapp.databinding.RowQuestionsBinding
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class QuestionRecyclerViewAdapter (
    options: FirestoreRecyclerOptions<ModelQuestion>,
    private val listener: QuestionClickListener
) : FirestoreRecyclerAdapter<ModelQuestion, QuestionRecyclerViewAdapter.ViewHolderQ>(options) {

    private var _binding: RowQuestionsBinding? = null

    inner class ViewHolderQ(private val binding: RowQuestionsBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun onBind(modelQuestion: ModelQuestion) {

            Log.d("NURIKO", "onBind: ")
            binding.questionTextView.text = modelQuestion.question
            binding.varATextView.text = modelQuestion.varA
            binding.varBTextView.text = modelQuestion.varB
            binding.varCTextView.text = modelQuestion.varC
            binding.varDTextView.text = modelQuestion.varD

            when (modelQuestion.answer) {
                1 -> {
                    binding.varATextView.setTextColor(Color.parseColor("#03A9F4"))
                    binding.textViewA.setTextColor(Color.parseColor("#03A9F4"))

                    binding.varBTextView.setTextColor(Color.parseColor("#12202E"))
                    binding.textViewB.setTextColor(Color.parseColor("#12202E"))

                    binding.varCTextView.setTextColor(Color.parseColor("#12202E"))
                    binding.textViewC.setTextColor(Color.parseColor("#12202E"))

                    binding.varDTextView.setTextColor(Color.parseColor("#12202E"))
                    binding.textViewD.setTextColor(Color.parseColor("#12202E"))
                }
                2 -> {
                    binding.varATextView.setTextColor(Color.parseColor("#12202E"))
                    binding.textViewA.setTextColor(Color.parseColor("#12202E"))

                    binding.varBTextView.setTextColor(Color.parseColor("#03A9F4"))
                    binding.textViewB.setTextColor(Color.parseColor("#03A9F4"))

                    binding.varCTextView.setTextColor(Color.parseColor("#12202E"))
                    binding.textViewC.setTextColor(Color.parseColor("#12202E"))

                    binding.varDTextView.setTextColor(Color.parseColor("#12202E"))
                    binding.textViewD.setTextColor(Color.parseColor("#12202E"))
                }
                3 -> {
                    binding.varATextView.setTextColor(Color.parseColor("#12202E"))
                    binding.textViewA.setTextColor(Color.parseColor("#12202E"))

                    binding.varBTextView.setTextColor(Color.parseColor("#12202E"))
                    binding.textViewB.setTextColor(Color.parseColor("#12202E"))

                    binding.varCTextView.setTextColor(Color.parseColor("#03A9F4"))
                    binding.textViewC.setTextColor(Color.parseColor("#03A9F4"))

                    binding.varDTextView.setTextColor(Color.parseColor("#12202E"))
                    binding.textViewD.setTextColor(Color.parseColor("#12202E"))
                }
                else -> {
                    binding.varATextView.setTextColor(Color.parseColor("#12202E"))
                    binding.textViewA.setTextColor(Color.parseColor("#12202E"))

                    binding.varBTextView.setTextColor(Color.parseColor("#12202E"))
                    binding.textViewB.setTextColor(Color.parseColor("#12202E"))

                    binding.varCTextView.setTextColor(Color.parseColor("#12202E"))
                    binding.textViewC.setTextColor(Color.parseColor("#12202E"))

                    binding.varDTextView.setTextColor(Color.parseColor("#03A9F4"))
                    binding.textViewD.setTextColor(Color.parseColor("#03A9F4"))
                }
            }

            binding.root.setOnLongClickListener {
                listener.onOnDeleteQuestion(adapterPosition)
                return@setOnLongClickListener true
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderQ {
        _binding = RowQuestionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderQ(_binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolderQ, position: Int, model: ModelQuestion) {
        holder.onBind(model)
    }


    interface QuestionClickListener {
        fun onOnDeleteQuestion(position: Int)
    }
}