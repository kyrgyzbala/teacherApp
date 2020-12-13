package com.english.teacherapp.ui.videos

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.english.teacherapp.databinding.ActivityVideosBinding
import com.english.teacherapp.ui.home.util.ModelLevel
import com.english.teacherapp.util.EXTRA_LEVEL
import com.english.teacherapp.util.EXTRA_TYPE
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class VideosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideosBinding

    private var level: ModelLevel? = null
    private var type: ModelLevel? = null

    private  var adapter: VideosRecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.WHITE

        binding.arrBackTypes.setOnClickListener {
            onBackPressed()
        }

        level = intent.getSerializableExtra(EXTRA_LEVEL) as ModelLevel
        type = intent.getSerializableExtra(EXTRA_TYPE) as ModelLevel

        binding.textViewTitle.text = type?.name

        binding.fabAddVideo.setOnClickListener {
            val intent = Intent(this, AddVideoActivity::class.java)
            intent.putExtra(EXTRA_LEVEL, level)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        initRecyclerView()
    }

    override fun onStop() {
        super.onStop()
        adapter?.stopListening()
    }

    private fun initRecyclerView() {

        val lifecycle = lifecycle

        val db = FirebaseFirestore.getInstance()
        val query = db.collection("videos").whereEqualTo("level", level!!.code)
            .orderBy("date", Query.Direction.DESCENDING)
        val options: FirestoreRecyclerOptions<ModelVideo> =
            FirestoreRecyclerOptions.Builder<ModelVideo>().setQuery(query, ModelVideo::class.java)
                .build()
        adapter = VideosRecyclerView(options, lifecycle)
        binding.recyclerViewVideos.setHasFixedSize(true)
        binding.recyclerViewVideos.adapter = adapter
        adapter?.startListening()
    }
}