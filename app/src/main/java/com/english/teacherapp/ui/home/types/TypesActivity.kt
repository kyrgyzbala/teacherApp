package com.english.teacherapp.ui.home.types

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.english.teacherapp.databinding.ActivityTypesBinding
import com.english.teacherapp.databinding.FragmentProfileBinding

class TypesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTypesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTypesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}