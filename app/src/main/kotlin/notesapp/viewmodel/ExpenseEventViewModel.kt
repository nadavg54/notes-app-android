package notesapp.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import notesapp.domain.ExpenseEvent
import notesapp.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExpenseEventViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = NoteDatabase.getDatabase(application).expenseEventDao()

    private val _amounts = MutableStateFlow<List<String>>(ExpenseEvent.defaultAmounts)
    val amounts: StateFlow<List<String>> = _amounts

    private val _currencies = MutableStateFlow<List<String>>(ExpenseEvent.defaultCurrencies)
    val currencies: StateFlow<List<String>> = _currencies

    private val _locations = MutableStateFlow<List<String>>(ExpenseEvent.defaultLocations)
    val locations: StateFlow<List<String>> = _locations

    init {
        viewModelScope.launch {
            _amounts.value = (dao.getRecentAmounts() + ExpenseEvent.defaultAmounts).distinct()
            _currencies.value = (dao.getRecentCurrencies() + ExpenseEvent.defaultCurrencies).distinct()
            _locations.value = (dao.getRecentLocations() + ExpenseEvent.defaultLocations).distinct()
        }
    }

    fun saveEvent(event: ExpenseEvent) {
        viewModelScope.launch {
            dao.insert(
                ExpenseEventEntity(
                    description = event.description,
                    amount = event.amount,
                    currency = event.currency,
                    location = event.location
                )
            )
        }
    }

    suspend fun getAllLoggedExpenses(): List<ExpenseEventEntity> {
        return dao.getAllEvents()
    }
}
