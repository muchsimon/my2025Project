package jetbrains.kotlin.course.alias.api

import jetbrains.kotlin.course.alias.results.GameResultsService
import jetbrains.kotlin.course.alias.team.TeamService
import jetbrains.kotlin.course.alias.card.CardService
import jetbrains.kotlin.course.alias.util.IdentifierFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/game")
class GameController(
    private val gameResultsService: GameResultsService,
    private val teamService: TeamService,
    private val cardService: CardService,
    private val identifierFactory: IdentifierFactory
) {

    // Start a new game by generating teams
    @PostMapping("/start")
    fun startGame(@RequestParam teamsNumber: Int): String {
        val teams = teamService.generateTeamsForOneRound(teamsNumber)
        return "Game started with ${teams.size} teams."
    }

    // Save game results
    @PostMapping("/results")
    fun saveGameResults(@RequestBody gameResult: List<Team>): String {
        gameResultsService.saveGameResults(gameResult)
        return "Game results saved successfully."
    }

    // Get all game results
    @GetMapping("/results")
    fun getGameResults(): List<List<Team>> {
        return gameResultsService.getAllGameResults()
    }

    // Generate and get cards
    @GetMapping("/generateCards")
    fun generateCards(): String {
        val cards = cardService.generateCards()
        return "Generated ${cards.size} cards."
    }

    // Load the game state
    @PostMapping("/loadState")
    fun loadGameState(): String {
        gameResultsService.loadGameHistory()
        teamService.loadTeamsStorage()
        cardService.loadCardsStorage()
        identifierFactory.loadLastUsedId()
        return "Game state loaded."
    }

    // Save the current game state
    @PostMapping("/saveState")
    fun saveGameState(): String {
        gameResultsService.saveGameHistory()
        teamService.saveTeamsStorage()
        cardService.saveCardsStorage()
        identifierFactory.saveLastUsedId()
        return "Game state saved."
    }
}
