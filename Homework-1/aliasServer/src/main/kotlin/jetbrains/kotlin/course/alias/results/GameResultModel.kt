package jetbrains.kotlin.course.alias.results

import jetbrains.kotlin.course.alias.team.Team
import jetbrains.kotlin.course.alias.util.Identifier

data class GameResult(
    val id: Identifier,
    val teams: List<Team>,
    val timestamp: Long
)