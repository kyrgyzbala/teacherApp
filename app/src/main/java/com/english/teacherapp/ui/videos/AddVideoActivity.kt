package com.english.teacherapp.ui.videos

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.english.teacherapp.databinding.ActivityAddVideoBinding
import com.english.teacherapp.ui.home.util.ModelLevel
import com.english.teacherapp.util.EXTRA_LEVEL
import com.english.teacherapp.util.show
import com.english.teacherapp.util.toast
import com.google.firebase.firestore.FirebaseFirestore

class AddVideoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddVideoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.WHITE

        binding.arrBackTaddVideo.setOnClickListener {
            onBackPressed()
        }

        val level = intent.getSerializableExtra(EXTRA_LEVEL) as ModelLevel

        binding.buttonAdd.setOnClickListener {
            if (checkFields()){
                val modelVideo = ModelVideo(binding.linkEditText.text.toString(),
                binding.descEditText.text.toString(), level.code)
                binding.prBar.show()
                uploadVideo(modelVideo)
            }
        }

    }

    private fun uploadVideo(modelVideo: ModelVideo) {
        FirebaseFirestore.getInstance().collection("videos").add(modelVideo).addOnSuccessListener {
            toast("New video added successfully!")
            onBackPressed()
        }
    }

    private fun checkFields(): Boolean {
        var ret = true

        if (binding.linkEditText.text.toString().isEmpty()) {
            ret = false
            binding.linkError.visibility = View.VISIBLE
        }

        if (binding.descEditText.text.toString().isEmpty()) {
            ret = false
            binding.descError.visibility = View.VISIBLE
        }

        return ret
    }
}