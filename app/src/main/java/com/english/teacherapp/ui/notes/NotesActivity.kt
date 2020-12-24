package com.english.teacherapp.ui.notes

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.english.teacherapp.databinding.ActivityNotesBinding
import com.english.teacherapp.ui.home.util.ModelLevel
import com.english.teacherapp.ui.quizzes.CustomAddQuiz
import com.english.teacherapp.ui.quizzes.util.ModelQuiz
import com.english.teacherapp.ui.videos.CustomDelete
import com.english.teacherapp.util.EXTRA_LEVEL
import com.english.teacherapp.util.EXTRA_NOTE_MODEL
import com.english.teacherapp.util.toast
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class NotesActivity : AppCompatActivity(), CustomAddNotes.CustomAddNotesListener,
    NotesRecyclerViewAdapter.NotesClickListener, CustomDelete.CustomDeleteClickListener {


    private lateinit var binding: ActivityNotesBinding

    private var level: ModelLevel? = null

    private var adapter: NotesRecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.WHITE

        level = intent.getSerializableExtra(EXTRA_LEVEL) as ModelLevel

        binding.arrBack.setOnClickListener {
            onBackPressed()
        }

        binding.levelTextView.text = level!!.name

        initRecyclerView(level!!.code)


        binding.fabAddNote.setOnClickListener {
            val dialog = CustomAddNotes( this)
            dialog.show(supportFragmentManager, "Add Quiz")
        }
    }

    override fun onStop() {
        super.onStop()
        adapter?.stopListening()
    }

    private fun initRecyclerView(code: Int) {
        binding.recyclerView.setHasFixedSize(true)
        val query = FirebaseFirestore.getInstance().collection("notes")
            .whereEqualTo("level", code)
            .orderBy("date", Query.Direction.DESCENDING)
        val options: FirestoreRecyclerOptions<ModelNote> =
            FirestoreRecyclerOptions.Builder<ModelNote>().setQuery(query, ModelNote::class.java)
                .build()
        adapter = NotesRecyclerViewAdapter(options, this)
        binding.recyclerView.adapter = adapter
        adapter?.startListening()
    }

    /**
     * Creates new note
     */
    override fun addNoteConfirmed(name: String, link: String) {
        val modelNote = ModelNote(name, link)
        modelNote.level = level!!.code
        FirebaseFirestore.getInstance().collection("notes").add(modelNote)
            .addOnSuccessListener {
                toast("New note created successfully")
            }
    }

    /**
     * Asks for confirmation on delete
     */
    override fun onDeleteNote(position: Int) {
        val ref = adapter!!.snapshots.getSnapshot(position).reference.path
        val dialog = CustomDelete(ref, "Delete this note?", this)
        dialog.show(supportFragmentManager, "Delete note")
    }

    /**
     * Deletes selected note if deletion has been confirmed
     */
    override fun onDeleteConfirm(ref: String) {
        FirebaseFirestore.getInstance().document(ref).delete()
    }


    /**
     * On Click note
     */
    override fun onClickNote(modelNote: ModelNote) {
        val intent = Intent(this, ViewNoteActivity::class.java)
        intent.putExtra(EXTRA_NOTE_MODEL, modelNote)
        startActivity(intent)
    }
}