package jetbrains.kotlin.course.alias.results

import org.springframework.stereotype.Service
import jetbrains.kotlin.course.alias.team.Team

typealias  GameResult = List<Team>

@Service
class GameResultsService {

    companion object {
        val gameHistory: MutableList<GameResult> = mutableListOf()
    }

    fun saveGameResults(result: GameResult): Unit = TODO("Not implemented yet")

    fun getAllGameResults(): List<GameResult> = TODO("Not implemented yet")
}
