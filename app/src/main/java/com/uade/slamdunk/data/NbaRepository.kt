package com.uade.slamdunk.data

import com.uade.slamdunk.model.Player
import com.uade.slamdunk.model.Team

class NbaRepository {
    suspend fun getTeams() : ArrayList<Team> {
        return NbaDataSource.getTeams()
    }
/*    suspend fun getPlayers(teamId: Int) : ArrayList<Player> {
        return NbaDataSource.getPlayers(teamId)
    }*/
}