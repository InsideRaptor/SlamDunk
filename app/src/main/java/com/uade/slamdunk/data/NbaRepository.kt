package com.uade.slamdunk.data

import com.uade.slamdunk.model.Player
import com.uade.slamdunk.model.Team

class NbaRepository {
    suspend fun getTeams() : ArrayList<Team> {
        return NbaDataSource.getTeams()
    }

    suspend fun setFav(team: Team) {
        return NbaDataSource.setFav(team)
    }

    suspend fun getFavs() : List<Team> {
        return NbaDataSource.getFavs()
    }

    suspend fun removeFav(team: Team) {
        return NbaDataSource.removeFav(team)
    }

/*    suspend fun getPlayers(teamId: Int) : ArrayList<Player> {
        return NbaDataSource.getPlayers(teamId)
    }*/
}