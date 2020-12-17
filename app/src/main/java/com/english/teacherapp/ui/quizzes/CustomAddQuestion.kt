package com.english.teacherapp.ui.quizzes

import android.graphics.Color
import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.english.teacherapp.R
import com.english.teacherapp.databinding.CustomAddQuestionBinding
import com.english.teacherapp.ui.quizzes.util.ModelQuestion
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CustomAddQuestion(
    private val listener: CustomAddQuestionListener
) : BottomSheetDialogFragment() {

    private var _binding: CustomAddQuestionBinding? = null
    private val binding: CustomAddQuestionBinding get() = _binding!!

    private var currentAnswer = 3

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CustomAddQuestionBinding.inflate(inflater, container, false)

        binding.topButtonDialog.setOnClickListener {
            dismiss()
        }

        addListeners()

        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        binding.confirmButton.setOnClickListener {
            if (checkFields()) {
                val modelQuestion = ModelQuestion(
                    binding.questionEditText.text.toString(),
                    binding.varAEditText.text.toString(),
                    binding.varBEditText.text.toString(),
                    binding.varCEditText.text.toString(),
                    binding.varDEditText.text.toString(),
                    currentAnswer
                )
                listener.onAddNewQuestion(modelQuestion)
                dismiss()
            }
        }

        return binding.root
    }

    private fun checkFields(): Boolean {
        var ret = true

        if (binding.questionEditText.text.toString().isEmpty()) {
            ret = false
            binding.questionEditText.error = getString(R.string.requiredField)
        }
        if (binding.varAEditText.text.toString().isEmpty()) {
            ret = false
            binding.varAEditText.error = getString(R.string.requiredField)
        }
        if (binding.varBEditText.text.toString().isEmpty()) {
            ret = false
            binding.varBEditText.error = getString(R.string.requiredField)
        }
        if (binding.varCEditText.text.toString().isEmpty()) {
            ret = false
            binding.varCEditText.error = getString(R.string.requiredField)
        }
        if (binding.varDEditText.text.toString().isEmpty()) {
            ret = false
            binding.varDEditText.error = getString(R.string.requiredField)
        }

        return ret
    }

    private fun addListeners() {
        binding.buttonAnswerA.setOnClickListener {
            currentAnswer = 1
            binding.buttonAnswerA.setBackgroundResource(R.drawable.back_button_blue)
            binding.buttonAnswerA.setTextColor(Color.WHITE)

            binding.buttonAnswerB.setTextColor(Color.parseColor("#12202E"))
            binding.buttonAnswerB.setBackgroundResource(R.drawable.back_edit)

            binding.buttonAnswerC.setTextColor(Color.parseColor("#12202E"))
            binding.buttonAnswerC.setBackgroundResource(R.drawable.back_edit)

            binding.buttonAnswerD.setTextColor(Color.parseColor("#12202E"))
            binding.buttonAnswerD.setBackgroundResource(R.drawable.back_edit)
        }

        binding.buttonAnswerB.setOnClickListener {
            currentAnswer = 2
            binding.buttonAnswerB.setBackgroundResource(R.drawable.back_button_blue)
            binding.buttonAnswerB.setTextColor(Color.WHITE)

            binding.buttonAnswerA.setTextColor(Color.parseColor("#12202E"))
            binding.buttonAnswerA.setBackgroundResource(R.drawable.back_edit)

            binding.buttonAnswerC.setTextColor(Color.parseColor("#12202E"))
            binding.buttonAnswerC.setBackgroundResource(R.drawable.back_edit)

            binding.buttonAnswerD.setTextColor(Color.parseColor("#12202E"))
            binding.buttonAnswerD.setBackgroundResource(R.drawable.back_edit)
        }

        binding.buttonAnswerC.setOnClickListener {
            currentAnswer = 3
            binding.buttonAnswerC.setBackgroundResource(R.drawable.back_button_blue)
            binding.buttonAnswerC.setTextColor(Color.WHITE)

            binding.buttonAnswerB.setTextColor(Color.parseColor("#12202E"))
            binding.buttonAnswerB.setBackgroundResource(R.drawable.back_edit)

            binding.buttonAnswerA.setTextColor(Color.parseColor("#12202E"))
            binding.buttonAnswerA.setBackgroundResource(R.drawable.back_edit)

            binding.buttonAnswerD.setTextColor(Color.parseColor("#12202E"))
            binding.buttonAnswerD.setBackgroundResource(R.drawable.back_edit)
        }

        binding.buttonAnswerD.setOnClickListener {
            currentAnswer = 4
            binding.buttonAnswerD.setBackgroundResource(R.drawable.back_button_blue)
            binding.buttonAnswerD.setTextColor(Color.WHITE)

            binding.buttonAnswerB.setTextColor(Color.parseColor("#12202E"))
            binding.buttonAnswerB.setBackgroundResource(R.drawable.back_edit)

            binding.buttonAnswerC.setTextColor(Color.parseColor("#12202E"))
            binding.buttonAnswerC.setBackgroundResource(R.drawable.back_edit)

            binding.buttonAnswerA.setTextColor(Color.parseColor("#12202E"))
            binding.buttonAnswerA.setBackgroundResource(R.drawable.back_edit)
        }

    }

    interface CustomAddQuestionListener {
        fun onAddNewQuestion(modelQuestion: ModelQuestion)
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }
}