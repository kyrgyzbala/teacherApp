package com.english.teacherapp.ui.quizzes

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.english.teacherapp.databinding.ActivityQuestionsBinding
import com.english.teacherapp.ui.quizzes.util.ModelQuestion
import com.english.teacherapp.ui.quizzes.util.ModelQuiz
import com.english.teacherapp.ui.quizzes.util.QuestionRecyclerViewAdapter
import com.english.teacherapp.ui.videos.CustomDelete
import com.english.teacherapp.util.EXTRA_QUIZ
import com.english.teacherapp.util.EXTRA_QUIZ_REF
import com.english.teacherapp.util.toast
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class QuestionsActivity : AppCompatActivity(), QuestionRecyclerViewAdapter.QuestionClickListener,
    CustomAddQuestion.CustomAddQuestionListener, CustomDelete.CustomDeleteClickListener {

    private lateinit var binding: ActivityQuestionsBinding

    private var ref: String? = ""

    private var adapter: QuestionRecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.WHITE

        val quiz = intent.getParcelableExtra<ModelQuiz>(EXTRA_QUIZ) as ModelQuiz
        ref = intent.getStringExtra(EXTRA_QUIZ_REF)

        binding.questionTextView.text = quiz.name

        initRecyclerView()

        binding.swipeRefresh.setOnRefreshListener {
            initRecyclerView()
        }

        binding.arrBack.setOnClickListener {
            onBackPressed()
        }

        binding.fabAddQuestion.setOnClickListener {
            val dialog = CustomAddQuestion(this)
            dialog.show(supportFragmentManager, "Add Question")
        }
    }

    /**
     * @Function Adds new question for current quiz
     */
    override fun onAddNewQuestion(modelQuestion: ModelQuestion) {
        val docRef = FirebaseFirestore.getInstance().document(ref!!).collection("questions")
        docRef.add(modelQuestion).addOnSuccessListener {
            toast("New question has been added successfully")
            initRecyclerView()
            FirebaseFirestore.getInstance().document(ref!!).get().addOnSuccessListener {
                val count = it.getLong("questions")
                if (count != null) {
                    val map = mutableMapOf<String, Any>()
                    map["questions"] = count.toInt() + 1
                    FirebaseFirestore.getInstance().document(ref!!).set(map, SetOptions.merge())
                        .addOnSuccessListener {
                            toast("Add Count Success!")
                        }
                } else {
                    toast("Count == NULL")
                }
            }
        }
    }

    private fun initRecyclerView() {
        val db = FirebaseFirestore.getInstance().document(ref!!)
        val query = db.collection("questions").orderBy("question")
        val options: FirestoreRecyclerOptions<ModelQuestion> =
            FirestoreRecyclerOptions.Builder<ModelQuestion>()
                .setQuery(query, ModelQuestion::class.java).build()
        adapter = QuestionRecyclerViewAdapter(options, this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter

        binding.swipeRefresh.isRefreshing = false
        adapter?.startListening()
    }

    override fun onOnDeleteQuestion(position: Int) {
        val ref = adapter!!.snapshots.getSnapshot(position).reference.path
        val dialog = CustomDelete(ref, "Delete this question?", this)
        dialog.show(supportFragmentManager, "DeleteQuestion")
    }

    override fun onStop() {
        super.onStop()
        adapter?.stopListening()
    }

    override fun onDeleteConfirm(ref: String) {
        FirebaseFirestore.getInstance().document(ref).delete()
    }

}