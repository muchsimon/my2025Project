package jetbrains.kotlin.course.alias.util

// Type alias for identifier
typealias Identifier = Int

// IdentifierFactory class
class IdentifierFactory {
    private var counter: Int = 0

 // Function to generate unique identifier
 fun uniqueIdentifier(): Identifier {
     counter++
     return counter
 }
}

// Type alias for identifier
typealias Identifier = Int

// IdentifierFactory class
class IdentifierFactory {
    private var counter: Int = 0

 // Function to generate unique identifier
 fun uniqueIdentifier(): Identifier {
     counter++
     return counter
 }
}
