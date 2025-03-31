package notesapp.model

import androidx.room.*
import com.example.notesapp.model.Note

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note)

    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAllNotes(): List<Note>

    @Query("SELECT * FROM notes WHERE content LIKE :query")
    fun searchNotes(query: String): List<Note>
}