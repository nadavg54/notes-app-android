package notesapp;


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import notesapp.NoteAdapter
import com.example.notesapp.R
import com.example.notesapp.model.Note
import notesapp.model.NoteDao
import notesapp.model.NoteDatabase
import androidx.lifecycle.viewmodel.compose.viewModel

//class MainActivity : AppCompatActivity() {
//    private lateinit var editTextNote: EditText
//    private lateinit var buttonAdd: Button
//    private lateinit var editTextSearch: EditText
//    private lateinit var recyclerViewNotes: RecyclerView
//    private lateinit var noteAdapter: NoteAdapter
//    private val notes = mutableListOf<Note>()
//    private lateinit var database: NoteDatabase
//    private lateinit var noteDao: NoteDao
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//
//        setContentView(R.layout.activity_main)
//
//        database = NoteDatabase.getDatabase(this)
//        noteDao = database.noteDao()
//
//        Thread {
//            val savedNotes = noteDao.getAllNotes()
//            notes.addAll(savedNotes)
//            runOnUiThread {
//                noteAdapter.updateNotes(notes)
//            }
//        }.start()
//
//        editTextNote = findViewById(R.id.editTextNote)
//        buttonAdd = findViewById(R.id.buttonAdd)
//        editTextSearch = findViewById(R.id.editTextSearch)
//        recyclerViewNotes = findViewById(R.id.recyclerViewNotes)
//
//        noteAdapter = NoteAdapter(notes)
//        recyclerViewNotes.adapter = noteAdapter
//        recyclerViewNotes.layoutManager = LinearLayoutManager(this)
//
//        buttonAdd.setOnClickListener {
//            val noteContent = editTextNote.text.toString()
//            if (noteContent.isNotEmpty()) {
//                val note = Note(content = noteContent)
//
//                Thread {
//                    noteDao.insert(note)
//                    val updatedNotes = noteDao.getAllNotes()
//                    runOnUiThread {
//                        noteAdapter.updateNotes(updatedNotes)
//                    }
//                }.start()
//
//                editTextNote.text.clear()
//            }
//        }
//
//        editTextSearch.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//                filterNotes(s.toString())
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//        })
//    }
//
//    private fun filterNotes(query: String) {
//        Thread {
//            val filteredNotes = if (query.isEmpty()) {
//                noteDao.getAllNotes()
//            } else {
//                noteDao.searchNotes("%$query%")
//            }
//            runOnUiThread {
//                noteAdapter.updateNotes(filteredNotes)
//            }
//        }.start()
//    }
//}


import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import notesapp.ui.LogExpenseEvent
import notesapp.ui.theme.NotesAppTheme
import notesapp.viewmodel.ExpenseEventViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NotesAppTheme {
                val viewModel: ExpenseEventViewModel = viewModel()
                LogExpenseEvent(viewModel = viewModel)
            }
        }
    }
}
