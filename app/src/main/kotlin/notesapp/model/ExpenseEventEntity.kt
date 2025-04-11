package notesapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "expense_events")
data class ExpenseEventEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val description: String,
    val amount: String,
    val currency: String,
    val location: String,
    val timestamp: Long = System.currentTimeMillis()
)
