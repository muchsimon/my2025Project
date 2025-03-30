package jetbrains.kotlin.course.alias.util

// Type alias for Identifier as Int
typealias Identifier = Int

// IdentifierFactory to generate unique identifiers
class IdentifierFactory(private var counter: Int = 0) {

    // Method to generate a unique identifier
    fun uniqueIdentifier(): Identifier {
        counter++
        return counter
    }
}
