package notesapp.ui

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import notesapp.domain.ExpenseEvent
import notesapp.viewmodel.ExpenseEventViewModel


@Composable
fun LogExpenseEvent(viewModel: ExpenseEventViewModel) {
    val amounts by viewModel.amounts.collectAsState()
    val currencies by viewModel.currencies.collectAsState()
    val locations by viewModel.locations.collectAsState()

    var description by rememberSaveable { mutableStateOf("") }
    var amount by rememberSaveable { mutableStateOf("") }
    var currency by rememberSaveable { mutableStateOf("") }
    var location by rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Text("Log Expense", style = MaterialTheme.typography.titleLarge)

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            AutocompleteTextField("Amount", amounts, amount, { amount = it }, { amount = it })
            AutocompleteTextField("Currency", currencies, currency, { currency = it }, { currency = it })
            AutocompleteTextField("Location", locations, location, { location = it }, { location = it })

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                val event = ExpenseEvent(description, amount, currency, location)
                viewModel.saveEvent(event)
                Toast.makeText(context, "Expense saved", Toast.LENGTH_SHORT).show()

                // Reset fields
                description = ""
                amount = amounts.firstOrNull().orEmpty()
                currency = currencies.firstOrNull().orEmpty()
                location = locations.firstOrNull().orEmpty()
            }) {
                Text("Save Expense")
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Previous Expenses", style = MaterialTheme.typography.titleMedium)
        }

        item {
            LoggedExpenseEventsList(viewModel = viewModel)
        }
    }
}


@Composable
fun AutocompleteTextField(
    label: String,
    suggestions: List<String>,
    value: String,
    onValueChange: (String) -> Unit,
    onSuggestionSelected: (String) -> Unit
) {
    var showSuggestions by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    LaunchedEffect(isFocused) {
        showSuggestions = isFocused
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
                showSuggestions = true
            },
            label = { Text(label) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            interactionSource = interactionSource
        )

        if (showSuggestions) {
            val filtered = suggestions.filter {
                value.isEmpty() || it.contains(value, ignoreCase = true)
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                filtered.forEach { suggestion ->
                    Text(
                        text = suggestion,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onSuggestionSelected(suggestion)
                                showSuggestions = false
                                focusManager.clearFocus()
                            }
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}

