package com.english.teacherapp.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.english.teacherapp.R
import com.english.teacherapp.databinding.FragmentProfileBinding
import com.english.teacherapp.util.hide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()

    }

    private fun initUI() {
        val user = FirebaseAuth.getInstance().currentUser!!
        FirebaseFirestore.getInstance().collection("teachers").document(user.uid).get()
            .addOnSuccessListener {
                binding.prBarProfile.hide()
                val userName = it.getString("name")
                val logo = it.getString("logo")

                if (!logo.isNullOrEmpty())
                    Glide.with(requireActivity()).load(logo).into(binding.profileLogo)
                binding.userName.text = userName
            }

    }

}