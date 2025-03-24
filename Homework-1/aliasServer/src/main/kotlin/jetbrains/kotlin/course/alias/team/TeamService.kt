package jetbrains.kotlin.course.alias.team

import jetbrains.kotlin.course.alias.util.Identifier
import jetbrains.kotlin.course.alias.util.IdentifierFactory
import org.springframework.stereotype.Service

@Service
class TeamService {
    private val identifierFactory: IdentifierFactory = IdentifierFactory()

    companion object {
        val teamsStorage: MutableMap<Identifier, Team> = mutableMapOf()
    }

    fun generateTeamsForOneRound(teamsNumber: Int): List<Team> {
        val teams = mutableListOf<Team>()
        repeat(teamsNumber) {
            val id = identifierFactory.uniqueIdentifier()

            val team = Team(id)

            teams.add(team)

            teamsStorage[id] = team
        }
        return teams
    }
}

