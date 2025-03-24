package jetbrains.kotlin.course.alias.card

import jetbrains.kotlin.course.alias.util.IdentifierFactory
import jetbrains.kotlin.course.alias.util.words
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException


@Service
class CardService {

    private val identifierFactory:IdentifierFactory = IdentifierFactory()

    private val cards: List<Card> = generateCards()

    companion object {
        const val WORDS_IN_CARD: Int = 4
        val cardsAmount: Int = words.size / WORDS_IN_CARD
    }

    private fun List<String>.toWords(): List<Word> {
        return this.map { Word(it) }
    }

    private fun generateCards(): List<Card> {
        val shuffledWords = words.shuffled()
        return shuffledWords.chunked(WORDS_IN_CARD)
            .take(cardsAmount)
            .mapIndexed { index, chunk ->
                Card(identifierFactory.uniqueIdentifier(), chunk.toWords())
            }
    }

    fun getCardByIndex(index: Int): Card {
        return cards.getOrNull(index) ?: throw IllegalArgumentException("Card Not Found For Index: $index")
    }
}
