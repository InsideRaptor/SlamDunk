package com.uade.slamdunk.model

data class Team (
    val id: Int,
    val name: String,
    val logo: String?,
    val nbaFranchise: Boolean,
    var isBookmarked: Boolean = false
) {
    // Default constructor required for Firestore deserialization
    constructor() : this(0, "", "", false)
}

data class JSONResponse (
    val response: List<Team>
)