package jetbrains.kotlin.course.alias.team

import jetbrains.kotlin.course.alias.util.Identifier

// Data class to present a team
data class Team(
    val id: Identifier,
    var points: Int = 0
) {
    //automatically initialize name properties
    val name: String = "Team#${id + 1}"
}

import jetbrains.kotlin.course.alias.util.Identifier

// Data class to present a team
data class Team(
    val id: Identifier,
    var points: Int = 0
) {
    //automatically initialize name properties
    val name: String = "Team#${id + 1}"
}
