package notesapp.domain

// 1. Base Event class
abstract class Event(
    val description: String,
    val timestamp: Long = System.currentTimeMillis()
) {
    abstract fun category(): String
    open fun extraFields(): Map<String, String> = emptyMap()
}

// 2. ExpenseEvent implementation
class ExpenseEvent(
    description: String, // ✅ no override here
    val amount: String,
    val currency: String,
    val location: String
) : Event(description = description)
 {
    override fun category() = "expense"
    override fun extraFields() = mapOf(
        "amount" to amount,
        "currency" to currency,
        "location" to location
    )

    companion object {
        val defaultAmounts = listOf("5.0", "20.0", "100.0")
        val defaultCurrencies = listOf("USD", "ILS", "EUR")
        val defaultLocations = listOf("Cafe X", "Supermarket", "Online Store")
    }
}

