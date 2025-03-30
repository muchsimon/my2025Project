package jetbrains.kotlin.course.alias.card

import org.springframework.stereotype.Service

// Placeholder classes for missing definitions
data class Word(val value: String)
data class Card(val id: String, val words: List<Word>)

interface IdentifierFactory {
    fun createIdentifier(): String
}

@Service
class CardService(private val identifierFactory: IdentifierFactory) {

    companion object {
        private const val WORDS_IN_CARD = 4
        private const val CARDS_AMOUNT = 10
    }

    private val cards: List<Card> = generateCards()

    private fun generateCards(): List<Card> {
        val allWords = listOf(
            "cable", "curve", "substance", "crow", "water", "ducks", "plot", "notebook",
            "development", "grass", "daughter", "judge", "touch", "drawer", "grade", "salt",
            "angle", "pan", "nerve", "stranger", "tray", "berry", "twig", "rabbits", "shake",
            "fold", "plastic", "turkey", "part", "rainstorm", "sweater", "camp", "addition",
            "quiet", "peace", "note", "end", "low", "dirt", "zebra", "hair", "friend", "sock",
            "soda", "metal", "whistle", "experience", "sea", "things", "badge", "mint", "chance",
            "shoes", "vegetable", "account", "wash", "aunt", "seat", "toad", "boundary", "knee",
            "vein", "stage", "ticket", "images", "field", "force", "discovery", "eggnog", "number",
            "structure", "wrist", "pest", "afterthought", "cup", "square", "art", "eye", "trees",
            "credit", "wool", "match", "jewel", "dime", "apparel", "pump", "design", "kick", "spy",
            "thing", "basket", "key", "seed", "star", "creator", "pocket", "war", "paper", "year",
            "rings", "trains", "home", "spot", "mom", "stream", "adjustment", "guide", "stem",
            "name", "spade", "space", "truck", "moon", "protest", "mailbox", "milk", "uncle", "jar",
            "porter", "tramp", "income", "thought", "grandfather", "playground", "gate", "crack",
            "school", "steam", "decision", "sidewalk", "treatment", "fireman", "eyes", "burst",
            "basin", "hole", "agreement", "mask", "flesh", "coat", "mine", "side", "army", "honey",
            "mouth", "humor", "loaf", "sticks", "winter", "sort", "son", "power", "smell", "locket",
            "table", "comparison", "rate", "surprise", "boat", "error", "camera", "can", "request",
            "destruction", "limit", "minute", "profit", "way", "swing", "vest", "laborer", "authority",
            "popcorn", "skirt", "amusement", "suit", "toys", "skin", "story", "chess", "toothbrush",
            "wine", "pot", "bait", "snails", "reason", "motion", "pen", "van", "mark", "push",
            "friction", "step", "glove", "skate", "cast", "glass", "group", "toy", "anger", "mice",
            "instrument", "marble", "brother", "brass", "representative", "ladybug", "canvas",
            "debt", "downtown", "silver", "basketball", "shock", "card", "receipt", "earth",
            "fall", "thunder", "fact", "bit", "pigs", "trip", "education", "ants", "veil",
            "straw", "hands", "jellyfish", "air", "value", "needle", "houses", "bucket",
            "edge", "corn", "spiders", "snakes", "railway", "pin", "committee", "scene",
            "office", "sponge", "thrill", "fear", "party", "color", "wax", "root", "belief",
            "grain", "behavior", "impulse", "zipper", "language", "car", "sun", "smile",
            "dad", "plant", "balance", "attack", "calendar", "history", "direction",
            "curtain", "cobweb", "base", "bag", "wilderness", "crowd", "question",
            "achiever", "dogs", "start", "trick", "brake", "alarm", "trail", "steel",
            "memory", "lock", "knife", "voice", "machine", "flight", "letter", "tomatoes",
            "discussion", "books", "respect", "rhythm", "governor", "earthquake", "religion",
            "rifle", "invention", "women", "stone", "border"
        ).shuffled().toWords()

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
