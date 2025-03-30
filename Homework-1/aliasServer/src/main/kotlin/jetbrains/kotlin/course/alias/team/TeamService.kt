package jetbrains.kotlin.course.alias.team

import jetbrains.kotlin.course.alias.util.Identifier
import jetbrains.kotlin.course.alias.util.IdentifierFactory
import org.springframework.stereotype.Service
import java.io.File
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

@Service
class TeamService(private val identifierFactory: IdentifierFactory) {

    companion object {
        private const val TEAMS_STORAGE_FILE = "teams_storage.json"
        private const val LAST_ID_FILE = "last_id.json"
        private val teamsStorage = mutableMapOf<Identifier, Team>()
        private val objectMapper = jacksonObjectMapper()
    }

    init {
        loadTeamsStorage()
        loadLastId()
    }

    fun generateTeamsForOneRound(teamsNumber: Int): List<Team> {
        val teams = List(teamsNumber) {
            val id = identifierFactory.uniqueIdentifier()
            Team(id)
        }
        teams.forEach { team ->
            teamsStorage[team.id] = team
        }
        saveTeamsStorage()
        return teams
    }

    private fun saveTeamsStorage() {
        val file = File(TEAMS_STORAGE_FILE)
        file.writeText(objectMapper.writeValueAsString(teamsStorage))
    }

    private fun loadTeamsStorage() {
        val file = File(TEAMS_STORAGE_FILE)
        if (file.exists()) {
            val savedTeams = file.readText()
            teamsStorage.clear()
            teamsStorage.putAll(objectMapper.readValue(savedTeams))
        }
    }

    private fun saveLastId() {
        val file = File(LAST_ID_FILE)
        file.writeText(objectMapper.writeValueAsString(identifierFactory.getLastUsedId()))
    }

    private fun loadLastId() {
        val file = File(LAST_ID_FILE)
        if (file.exists()) {
            val savedId = file.readText()
            identifierFactory.setCounter(objectMapper.readValue(savedId))
        }
    }
}
