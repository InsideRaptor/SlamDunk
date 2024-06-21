package com.uade.slamdunk.data

import com.uade.slamdunk.model.JSONResponse
import com.uade.slamdunk.model.JSONResponsePlayer
import retrofit2.http.GET
import retrofit2.http.Query

interface NbaAPI {

    @GET("/teams")
    suspend fun getTeams(
    ) : JSONResponse

    @GET("/players")
    suspend fun getPlayers(
        @Query("team") teamId: Int,
        @Query("season") page: Int = 2023
    ) : JSONResponsePlayer

}