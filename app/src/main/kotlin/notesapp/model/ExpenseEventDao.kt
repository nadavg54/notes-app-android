package notesapp.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExpenseEventDao {
    @Insert
    suspend fun insert(event: ExpenseEventEntity)

    @Query("SELECT DISTINCT amount FROM expense_events ORDER BY timestamp DESC LIMIT 10")
    suspend fun getRecentAmounts(): List<String>

    @Query("SELECT DISTINCT currency FROM expense_events ORDER BY timestamp DESC LIMIT 10")
    suspend fun getRecentCurrencies(): List<String>

    @Query("SELECT DISTINCT location FROM expense_events ORDER BY timestamp DESC LIMIT 10")
    suspend fun getRecentLocations(): List<String>

    @Query("SELECT * FROM expense_events ORDER BY timestamp DESC")
    suspend fun getAllEvents(): List<ExpenseEventEntity>

}
