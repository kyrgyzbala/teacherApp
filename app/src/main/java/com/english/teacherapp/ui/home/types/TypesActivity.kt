package com.english.teacherapp.ui.home.types

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.english.teacherapp.R
import com.english.teacherapp.databinding.ActivityTypesBinding
import com.english.teacherapp.databinding.FragmentProfileBinding
import com.english.teacherapp.ui.home.LevelRecyclerView
import com.english.teacherapp.ui.home.util.ModelLevel
import com.english.teacherapp.ui.notes.NotesActivity
import com.english.teacherapp.ui.quizzes.QuizzesActivity
import com.english.teacherapp.ui.videos.VideosActivity
import com.english.teacherapp.util.EXTRA_LEVEL
import com.english.teacherapp.util.EXTRA_TYPE
import com.english.teacherapp.util.toast

class TypesActivity : AppCompatActivity(), LevelRecyclerView.LevelClickListener {

    private lateinit var binding: ActivityTypesBinding

    private var levels = mutableListOf<ModelLevel>()

    private lateinit var adapter: LevelRecyclerView

    private var currentLevel: ModelLevel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTypesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.WHITE

        binding.arrBackTypes.setOnClickListener {
            onBackPressed()
        }

        currentLevel = intent.getSerializableExtra(EXTRA_LEVEL) as ModelLevel

        binding.textViewTitle.text = currentLevel?.name

        generateLevels()
        adapter = LevelRecyclerView(this)
        binding.recyclerViewTypes.setHasFixedSize(true)
        binding.recyclerViewTypes.adapter = adapter
        adapter.submitList(levels)
    }

    private fun generateLevels() {
        levels = mutableListOf(
            ModelLevel(1, "Lecture Notes", R.drawable.lesson),
            ModelLevel(2, "Quizzes", R.drawable.quizicon),
            ModelLevel(3, "Videos", R.drawable.tutorial)
        )
    }

    override fun onLevelClick(position: Int) {
        val type = adapter.getItemAt(position)
        when (type.code) {
            1 -> {
                val intent = Intent(this, NotesActivity::class.java)
                intent.putExtra(EXTRA_LEVEL, currentLevel)
                intent.putExtra(EXTRA_TYPE, type)
                startActivity(intent)
            }
            2 -> {
                val intent = Intent(this, QuizzesActivity::class.java)
                intent.putExtra(EXTRA_LEVEL, currentLevel)
                intent.putExtra(EXTRA_TYPE, type)
                startActivity(intent)
            }
            3 -> {
                val intent = Intent(this, VideosActivity::class.java)
                intent.putExtra(EXTRA_LEVEL, currentLevel)
                intent.putExtra(EXTRA_TYPE, type)
                startActivity(intent)
            }
        }
    }

}