package jetbrains.kotlin.course.alias.card

import jetbrains.kotlin.course.alias.util.IdentifierFactory
import jetbrains.kotlin.course.alias.util.Identifier
import org.springframework.stereotype.Service
import java.io.File
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

@Service
class CardService(private val identifierFactory: IdentifierFactory) {

    companion object {
        private const val CARDS_STORAGE_FILE = "cards_storage.json"
        private const val LAST_CARD_ID_FILE = "last_card_id.json"
        private val objectMapper = jacksonObjectMapper()
    }

    private val cards: MutableList<Card> = mutableListOf()

    init {
        loadCardsStorage()
        loadLastCardId()
    }

    fun generateCards(): List<Card> {
        val words = listOf("apple", "banana", "cherry", "date", "elderberry", "fig", "grape", "honeydew")
            .shuffled()
            .map { Word(it) }

        val newCards = List(10) { index ->
            val id = identifierFactory.uniqueIdentifier()
            Card(id, words.drop(index * 4).take(4))
        }

        cards.clear()
        cards.addAll(newCards)
        saveCardsStorage()
        return newCards
    }

    private fun saveCardsStorage() {
        val file = File(CARDS_STORAGE_FILE)
        file.writeText(objectMapper.writeValueAsString(cards))
    }

    private fun loadCardsStorage() {
        val file = File(CARDS_STORAGE_FILE)
        if (file.exists()) {
            val savedCards = file.readText()
            cards.clear()
            cards.addAll(objectMapper.readValue(savedCards))
        }
    }

    private fun saveLastCardId() {
        val file = File(LAST_CARD_ID_FILE)
        file.writeText(objectMapper.writeValueAsString(identifierFactory.getLastUsedId()))
    }

    private fun loadLastCardId() {
        val file = File(LAST_CARD_ID_FILE)
        if (file.exists()) {
            val savedId = file.readText()
            identifierFactory.setCounter(objectMapper.readValue(savedId))
        }
    }
}
