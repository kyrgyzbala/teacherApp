package com.english.teacherapp.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.english.teacherapp.R
import com.english.teacherapp.databinding.CustomAddNotesBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CustomAddNotes (
    private val listener: CustomAddNotesListener
) : BottomSheetDialogFragment() {

    private var _binding: CustomAddNotesBinding? = null
    private val binding: CustomAddNotesBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CustomAddNotesBinding.inflate(layoutInflater)

        binding.topButtonDialog.setOnClickListener {
            dismiss()
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        binding.confirmButton.setOnClickListener {
            if (checkInputs()) {
                listener.addNoteConfirmed(
                    binding.catNameEditText.text.toString(),
                    binding.linkEditText.text.toString()
                )
                dismiss()
            }
        }

        return binding.root
    }

    private fun checkInputs() : Boolean {
        var ret = true

        if (binding.catNameEditText.text.toString().isEmpty()) {
            ret = false
            binding.catNameEditText.error = getString(R.string.requiredField)
        }

        if (binding.linkEditText.text.toString().isEmpty()) {
            ret = false
            binding.linkEditText.error = getString(R.string.requiredField)
        }

        return ret
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

    interface CustomAddNotesListener {
        fun addNoteConfirmed(name: String, link: String)
    }

}