package com.uade.slamdunk.data

import com.uade.slamdunk.model.JSONResponse
import com.uade.slamdunk.model.Player
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NbaAPI {

    @GET("/teams")
    suspend fun getTeams(
    ) : JSONResponse

    @GET("/players")
    suspend fun getPlayers(
        @Query("id") teamId: Int
    ) : Call<ArrayList<Player>>

}