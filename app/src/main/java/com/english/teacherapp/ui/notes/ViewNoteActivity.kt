package com.english.teacherapp.ui.notes

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.english.teacherapp.databinding.ActivityViewNoteBinding
import com.english.teacherapp.util.EXTRA_NOTE_MODEL

class ViewNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.WHITE

        val note = intent.getParcelableExtra<ModelNote>(EXTRA_NOTE_MODEL) as ModelNote

        binding.textViewTitle.text = note.topic

        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl(note.link)
        binding.arrBack.setOnClickListener {
            onBackPressed()
        }
    }
}