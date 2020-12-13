package com.english.teacherapp.ui.quizzes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.english.teacherapp.databinding.ActivityQuizzesBinding

class QuizzesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizzesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizzesBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}