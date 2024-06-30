package com.uade.slamdunk.model

import androidx.room.Entity

@Entity(tableName = "players", primaryKeys = ["id"])
data class PlayerLocal(
    val teamId : Int,
    val id: Int,
    val firstname: String,
    val lastname: String,
)