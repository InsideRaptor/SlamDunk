package com.uade.slamdunk.model

fun Player.toPlayerLocal(teamId: Int): PlayerLocal {
    return PlayerLocal(
        teamId = teamId,
        id = this.id,
        firstname = this.firstname,
        lastname = this.lastname
    )
}

fun PlayerLocal.toPlayer(): Player {
    // Assuming you have default values or other sources for these fields in your domain model
    return Player(
        id = this.id,
        firstname = this.firstname,
        lastname = this.lastname,
    )
}