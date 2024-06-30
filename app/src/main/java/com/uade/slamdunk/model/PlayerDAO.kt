package com.uade.slamdunk.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlayerDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayers(players: List<PlayerLocal>)

    @Query("SELECT * FROM players WHERE teamId = :teamId")
    suspend fun getPlayers(teamId: Int): List<PlayerLocal>

}