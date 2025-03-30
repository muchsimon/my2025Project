package jetbrains.kotlin.course.alias.card

data class Card(
    val id: Identifier,
    val words: List<Word>
)

@JvmInline
value class Word(val word: String)

fun main() {
    // Example usage
    val wordsList = listOf(Word("apple"), Word("banana"), Word("cherry"))
    val card = Card(id = Identifier(), words = wordsList)

    println(card)
}
