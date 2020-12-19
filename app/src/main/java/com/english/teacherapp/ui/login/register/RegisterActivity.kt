package com.english.teacherapp.ui.login.register

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.english.teacherapp.databinding.ActivityRegisterBinding
import com.english.teacherapp.ui.main.MainActivity
import com.english.teacherapp.util.CHOOSE_IMAGE_REQUEST
import com.english.teacherapp.util.hide
import com.english.teacherapp.util.show
import com.english.teacherapp.util.toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private var loadedImg = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgViewAdd.setOnClickListener {
            openImageChooser()
        }
        binding.imgViewLogoRegisterBusiness.setOnClickListener {
            openImageChooser()
        }

        binding.nextButton.setOnClickListener {
            when {
                binding.prBarImgLoading.visibility == View.VISIBLE -> {
                    toast("Please wait for image load")
                }
                loadedImg == "" -> {
                    toast("Please upload profile Image")
                }
                binding.nameEditTextRegister2.text.toString().isEmpty() -> {
                    binding.nameErrorReg2.visibility = View.VISIBLE
                }
                else -> {
                    registerNewUser()
                }
            }
        }
    }

    private fun registerNewUser() {
        val displayName = binding.nameEditTextRegister2.text.toString()
        val user = FirebaseAuth.getInstance().currentUser!!
        val request = UserProfileChangeRequest.Builder().setDisplayName(displayName).build()
        user.updateProfile(request)

        val model = ModelUser(displayName, loadedImg, true)
        model.uid = user.uid
        model.phoneNumber = user.phoneNumber
        FirebaseFirestore.getInstance().collection("teachers").document(user.uid)
            .set(model, SetOptions.merge())
            .addOnSuccessListener {
                toast("Welcome $displayName")
                goToMain()
            }
            .addOnFailureListener {
                toast("Error! Please try again!")
            }
    }

    private fun openImageChooser() {
        val intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }
        startActivityForResult(
            Intent.createChooser(intent, "Choose profile photo"),
            CHOOSE_IMAGE_REQUEST
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CHOOSE_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
            && data != null && data.data != null
        ) {
            val uri = data.data
            if (uri != null) {
                binding.imgViewLogoRegisterBusiness.visibility = View.VISIBLE
                binding.imgViewAdd.visibility = View.GONE
                binding.imgViewLogoRegisterBusiness.setImageURI(uri)
                binding.prBarImgLoading.show()
                val ref =
                    FirebaseStorage.getInstance().getReference("logos/${Date().time}.jpg")
                ref.putFile(uri).addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener { log ->
                        loadedImg = log.toString()
                        binding.prBarImgLoading.hide()
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun goToMain() {
        val edit = getSharedPreferences("USER", Context.MODE_PRIVATE).edit()
        edit.putString("ISLOGGEDIN", "DONE")
        edit.apply()
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        finish()
        startActivity(intent)
    }
}