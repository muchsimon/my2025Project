package jetbrains.kotlin.course.alias.results

import jetbrains.kotlin.course.alias.team.Team
import org.springframework.stereotype.Service
import java.io.File
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

@Service
class GameResultsService {

    companion object {
        private const val GAME_HISTORY_FILE = "game_history.json"
        private val gameHistory: MutableList<GameResult> = mutableListOf()
        private val objectMapper = jacksonObjectMapper()
    }

    init {
        loadGameHistory()
    }

    fun saveGameResults(result: GameResult) {
        require(result.isNotEmpty()) { "Game result cannot be empty" }
        gameHistory.add(result)
        saveGameHistory()
    }

    fun getAllGameResults(): List<GameResult> = gameHistory.reversed()

    private fun saveGameHistory() {
        File(GAME_HISTORY_FILE).writeText(objectMapper.writeValueAsString(gameHistory))
    }

    private fun loadGameHistory() {
        val file = File(GAME_HISTORY_FILE)
        if (file.exists()) {
            val savedHistory: List<GameResult> = objectMapper.readValue(file.readText())
            gameHistory.clear()
            gameHistory.addAll(savedHistory)
        }
    }
}

typealias GameResult = List<TeamData>
