package notesapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import notesapp.model.ExpenseEventEntity
import notesapp.viewmodel.ExpenseEventViewModel
import java.util.Date
import androidx.compose.runtime.setValue



@Composable
fun LoggedExpenseEventsList(viewModel: ExpenseEventViewModel) {
    val context = LocalContext.current
    var events by remember { mutableStateOf(listOf<ExpenseEventEntity>()) }

    // Load events once when Composable is shown
    LaunchedEffect(Unit) {
        events = viewModel.getAllLoggedExpenses()
    }

    if (events.isEmpty()) {
        Text("No events yet.")
    } else {
        Column(modifier = Modifier.fillMaxWidth()) {
            events.forEach { event ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text("💬 ${event.description}")
                        Text("💰 ${event.amount} ${event.currency}")
                        Text("📍 ${event.location}")
                        Text("🕒 ${Date(event.timestamp)}")
                    }
                }
            }
        }
    }
}
