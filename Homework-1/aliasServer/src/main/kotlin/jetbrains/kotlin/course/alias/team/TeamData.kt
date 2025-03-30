package jetbrains.kotlin.course.alias.team

import org.springframework.stereotype.Service

@Service
class TeamService(private val identifierFactory: IdentifierFactory) {

    companion object {
        private val teamsStorage: MutableMap<Identifier, Team> = mutableMapOf()
    }

    fun generateTeamsForOneRound(teamsNumber: Int): List<Team> {
        val teams = mutableListOf<Team>()
        
        repeat(teamsNumber) {
            val id = identifierFactory.createIdentifier()
            val team = Team(id)
            teamsStorage[id] = team
            teams.add(team)
        }
        
        return teams
    }
}

data class TeamData(val id: Identifier, val points: Int = 0, val name: String = "Team#${id + 1}")
