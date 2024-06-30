package com.uade.slamdunk.data

import com.uade.slamdunk.model.Player
import com.uade.slamdunk.model.PlayerDAO
import com.uade.slamdunk.model.toPlayer
import com.uade.slamdunk.model.toPlayerLocal

class PlayersLocalRepository(private val playerDao: PlayerDAO) {

    suspend fun insertPlayers(players: List<Player>, teamId: Int) {
        val playerLocals = players.map { it.toPlayerLocal(teamId) }
        playerDao.insertPlayers(playerLocals)
    }

    suspend fun getPlayers(teamId: Int): List<Player> {
        val playerLocals = playerDao.getPlayers(teamId)
        return playerLocals.map { it.toPlayer() }
    }

}