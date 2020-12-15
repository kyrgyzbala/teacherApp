package com.english.teacherapp.ui.videos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.english.teacherapp.R
import com.english.teacherapp.databinding.CustomDeleteBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CustomDelete(
    private val ref: String,
    private val text: String,
    private val listener: CustomDeleteClickListener
) :
    BottomSheetDialogFragment() {

    private var _binding: CustomDeleteBinding? = null
    private val binding: CustomDeleteBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CustomDeleteBinding.inflate(inflater, container, false)

        binding.textViewDelete.text = text

        binding.topButtonDialogDelete.setOnClickListener {
            dismiss()
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        binding.deleteButton.setOnClickListener {
            listener.onDeleteConfirm(ref)
            dismiss()
        }

        return binding.root
    }

    interface CustomDeleteClickListener {
        fun onDeleteConfirm(ref: String)
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

}