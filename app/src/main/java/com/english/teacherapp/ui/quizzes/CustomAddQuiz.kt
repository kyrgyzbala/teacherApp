package com.english.teacherapp.ui.quizzes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.english.teacherapp.R
import com.english.teacherapp.databinding.CustomAddQuizBinding
import com.english.teacherapp.ui.home.util.ModelLevel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CustomAddQuiz(
    private val level: ModelLevel,
    private val listener: CustomAddQuizListener
) : BottomSheetDialogFragment() {

    private var _binding: CustomAddQuizBinding? = null
    private val binding: CustomAddQuizBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CustomAddQuizBinding.inflate(inflater, container, false)

        binding.topButtonDialog.setOnClickListener {
            dismiss()
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        binding.confirmButton.setOnClickListener {
            if (binding.catNameEditText.text.toString().isNotEmpty()) {
                listener.onAddQuiz(level, binding.catNameEditText.text.toString())
                dismiss()
            }
        }

        return binding.root
    }

    interface CustomAddQuizListener {
        fun onAddQuiz(level: ModelLevel, name: String)
    }


    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

}