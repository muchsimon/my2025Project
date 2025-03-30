package jetbrains.kotlin.course.alias.card

import jetbrains.kotlin.course.alias.util.IdentifierFactory
import org.springframework.stereotype.Service

@Service
class CardService {
    private val identifierFactory = IdentifierFactory()

    companion object {
        const val WORDS_IN_CARD = 4
        private val words = listOf(
            "cat", "dog", "house", "car", "tree", "book", "computer", "phone",
            "table", "chair", "window", "door", "sun", "moon", "star", "cloud"
        )
        val cardsAmount = words.size / WORDS_IN_CARD
    }

    private val cards: List<Card> = generateCards()

    private fun generateCards(): List<Card> {
        return words.shuffled()
            .chunked(WORDS_IN_CARD)
            .take(cardsAmount)
            .map { wordList -> Card(identifierFactory.uniqueIdentifier(), wordList.toWords()) }
    }

    private fun List<String>.toWords(): List<Word> = map { Word(it) }

    fun getCardByIndex(index: Int): Card {
        require(index in cards.indices) { "Card index out of bounds" }
        return cards[index]
    }
}
