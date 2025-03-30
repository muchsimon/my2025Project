package jetbrains.kotlin.course.alias.card

import org.springframework.stereotype.Service

@Service
class CardService(private val identifierFactory: IdentifierFactory) {

    companion object {
        private const val WORDS_IN_CARD = 4
        private const val CARDS_AMOUNT = 10
    }

    private val cards: List<Card> = generateCards()

    private fun generateCards(): List<Card> {
        val allWords = listOf("apple", "banana", "cherry", "date", "elderberry", "fig", "grape", "honeydew")
            .shuffled()
            .toWords()

        if (allWords.size < CARDS_AMOUNT * WORDS_IN_CARD) {
            throw IllegalStateException("Not enough words to generate cards.")
        }

        return allWords.chunked(WORDS_IN_CARD).take(CARDS_AMOUNT).map { words ->
            val id = identifierFactory.createIdentifier()
            Card(id, words)
        }
    }

    private fun List<String>.toWords(): List<Word> = map { Word(it) }

    fun getCardByIndex(index: Int): Card? = cards.getOrNull(index)
}
