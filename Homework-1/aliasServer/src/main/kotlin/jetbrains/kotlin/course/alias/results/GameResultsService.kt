package jetbrains.kotlin.course.alias.results

import org.springframework.stereotype.Service

@Service
class GameResultsService {

    companion object {
        private val gameHistory: MutableList<GameResult> = mutableListOf()
    }

    fun saveGameResults(result: GameResult) {
        require(result.isNotEmpty()) { "Game result cannot be empty" }
        gameHistory.add(result)
    }

    fun getAllGameResults(): List<GameResult> = gameHistory.reversed()
}

typealias GameResult = List<TeamData>
