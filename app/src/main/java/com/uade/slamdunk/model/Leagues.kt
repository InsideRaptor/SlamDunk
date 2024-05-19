package com.uade.slamdunk.model

data class Leagues(
    val standard: League,
    val vegas: League,
    val utah: League,
    val sacramento: League,
    val africa: League,
    val orlando: League,
)

data class League(
    val conference: String,
    val division: String,
    val jersey: Int,
    val active: Boolean,
    val pos: String
)