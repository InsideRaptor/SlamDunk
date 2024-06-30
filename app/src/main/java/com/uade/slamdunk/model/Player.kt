package com.uade.slamdunk.model

data class Player(
    val id: Int,
    val firstname: String,
    val lastname: String,
//    val birth: Birth,
//    val nba: Nba,
//    val height: Height,
//    val weight: Weight,
//    val college: String,
//    val affiliation: String,
//    val leagues: Leagues
)

//data class Birth(
//    val date: String,
//    val country: String
//)
//
//data class Nba(
//    val start: Int,
//    val pro: Int
//)
//
//data class Height(
//    val feets: String,
//    val inches: String,
//    val meters: String
//)
//
//data class Weight(
//    val pounds: String,
//    val kilograms: String
//)

data class JSONResponsePlayer (
    val response: List<Player>
)

