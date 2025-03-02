package com.example.notesapp;


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.NoteAdapter
import com.example.notesapp.R
import com.example.notesapp.model.Note

class MainActivity : AppCompatActivity() {
    private lateinit var editTextNote: EditText
    private lateinit var buttonAdd: Button
    private lateinit var editTextSearch: EditText
    private lateinit var recyclerViewNotes: RecyclerView
    private lateinit var noteAdapter: NoteAdapter
    private val notes = mutableListOf<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextNote = findViewById(R.id.editTextNote)
        buttonAdd = findViewById(R.id.buttonAdd)
        editTextSearch = findViewById(R.id.editTextSearch)
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes)

        noteAdapter = NoteAdapter(notes)
        recyclerViewNotes.adapter = noteAdapter
        recyclerViewNotes.layoutManager = LinearLayoutManager(this)

        buttonAdd.setOnClickListener {
            val noteContent = editTextNote.text.toString()
            if (noteContent.isNotEmpty()) {
                val note = Note(notes.size, noteContent)
                notes.add(note)
                noteAdapter.updateNotes(notes)
                editTextNote.text.clear()
            }
        }

        editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filterNotes(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun filterNotes(query: String) {
        val filteredNotes = notes.filter { it.content.contains(query, ignoreCase = true) }
        noteAdapter.updateNotes(filteredNotes)
    }
}