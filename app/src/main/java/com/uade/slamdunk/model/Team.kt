package com.uade.slamdunk.model

data class Team (
    val id: Int,
    val name: String,
    val logo: String?,
    val nbaFranchise: Boolean,
    var isBookmarked: Boolean = false
)

data class JSONResponse (
    val response: List<Team>
)