package com.english.teacherapp.ui.quizzes

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.english.teacherapp.databinding.ActivityQuizzesBinding
import com.english.teacherapp.ui.home.util.ModelLevel
import com.english.teacherapp.ui.quizzes.util.ModelQuiz
import com.english.teacherapp.ui.quizzes.util.QuizzesRecyclerViewAdapter
import com.english.teacherapp.util.EXTRA_LEVEL
import com.english.teacherapp.util.EXTRA_QUIZ
import com.english.teacherapp.util.EXTRA_QUIZ_REF
import com.english.teacherapp.util.toast
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class QuizzesActivity : AppCompatActivity(), QuizzesRecyclerViewAdapter.QuizClickListener,
    CustomAddQuiz.CustomAddQuizListener {

    private lateinit var binding: ActivityQuizzesBinding

    private var adapter: QuizzesRecyclerViewAdapter? = null

    private var level: ModelLevel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizzesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.WHITE

        level = intent.getSerializableExtra(EXTRA_LEVEL) as ModelLevel

        binding.arrBack.setOnClickListener {
            onBackPressed()
        }

        binding.levelTextView.text = level!!.name

        initRecyclerView(level!!.code)

        binding.swipeRefresh.setOnRefreshListener {
            initRecyclerView(level!!.code)
        }

        binding.fabAddQuiz.setOnClickListener {
            val dialog = CustomAddQuiz(level!!, this)
            dialog.show(supportFragmentManager, "Add Quiz")
        }

    }

    override fun onAddQuiz(level: ModelLevel, name: String) {
        val modelQuiz = ModelQuiz(
            name, level.code, 0
        )
        FirebaseFirestore.getInstance().collection("quizzes").add(modelQuiz)
            .addOnSuccessListener {
                toast("New Quiz added successfully!")
                initRecyclerView(level.code)
            }
    }

    private fun initRecyclerView(code: Int) {
        binding.recyclerView.setHasFixedSize(true)
        val query = FirebaseFirestore.getInstance().collection("quizzes")
            .whereEqualTo("level", code)
            .orderBy("date", Query.Direction.DESCENDING)
        val options: FirestoreRecyclerOptions<ModelQuiz> =
            FirestoreRecyclerOptions.Builder<ModelQuiz>().setQuery(query, ModelQuiz::class.java)
                .build()
        adapter = QuizzesRecyclerViewAdapter(options, this)
        binding.recyclerView.adapter = adapter
        binding.swipeRefresh.isRefreshing = false
        adapter?.startListening()
    }

    override fun onResume() {
        super.onResume()
        initRecyclerView(level!!.code)
    }

    override fun onStop() {
        super.onStop()
        adapter?.stopListening()
    }

    override fun onQuizClick(modelQuiz: ModelQuiz, position: Int) {
        val ref = adapter!!.snapshots.getSnapshot(position).reference.path
        val intent = Intent(this, QuestionsActivity::class.java)
        intent.putExtra(EXTRA_QUIZ, modelQuiz)
        intent.putExtra(EXTRA_QUIZ_REF, ref)
        startActivity(intent)
    }


}