package jetbrains.kotlin.course.alias.results

import jetbrains.kotlin.course.alias.team.TeamService
import jetbrains.kotlin.course.alias.util.IdentifierFactory
import org.springframework.stereotype.Service

@Service
class GameResultsService {
    private val identifierFactory = IdentifierFactory()

    companion object {
        private val gameHistory: MutableList<GameResult> = mutableListOf()
    }

    fun saveGameResults(teams: GameTeamsList) {
        require(teams.isNotEmpty()) { "Game result cannot be empty" }
        require(teams.all { it.id in TeamService.teamsStorage }) { "All teams must be registered" }
        val result = GameResult(
            id = identifierFactory.uniqueIdentifier(),
            teams = teams,
            timestamp = System.currentTimeMillis()
        )
        gameHistory.add(result)
    }

    fun getAllGameResults(): List<GameResult> = gameHistory.reversed()
}
