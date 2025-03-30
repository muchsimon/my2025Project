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
        private val gameHistory = mutableListOf<GameResult>()
        private val objectMapper = jacksonObjectMapper()
    }

    init {
        loadGameHistory()
    }

    fun saveGameResults(result: GameResult) {
        gameHistory.add(result)
        saveGameHistory()
    }

    fun getAllGameResults(): List<GameResult> {
        return gameHistory.reversed()  // Return the reversed history
    }

    private fun saveGameHistory() {
        val file = File(GAME_HISTORY_FILE)
        file.writeText(objectMapper.writeValueAsString(gameHistory))
    }

    private fun loadGameHistory() {
        val file = File(GAME_HISTORY_FILE)
        if (file.exists()) {
            val savedHistory = file.readText()
            gameHistory.clear()
            gameHistory.addAll(objectMapper.readValue(savedHistory))
        }
    }
}
