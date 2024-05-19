package com.uade.slamdunk.model

data class Team (
    val id: Int,
    val name: String,
    val logo: String?,
    val nbaFranchise: Boolean
)

data class JSONResponse (
    val response: List<Team>
)