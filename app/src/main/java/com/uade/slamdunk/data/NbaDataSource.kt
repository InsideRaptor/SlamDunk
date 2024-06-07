package com.uade.slamdunk.data

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.uade.slamdunk.model.Player
import com.uade.slamdunk.model.Team
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.uade.slamdunk.BuildConfig
import kotlinx.coroutines.tasks.await

class NbaDataSource {

    companion object {

        //Constantes
        @SuppressLint("StaticFieldLeak")
        private val db = FirebaseFirestore.getInstance()
        private val firebaseAuth = FirebaseAuth.getInstance()
        private val uid = firebaseAuth.currentUser?.uid

        private const val BASE_URL = "https://v2.nba.api-sports.io/"
        private const val TAG = "NBA_API"
        private const val API_KEY = BuildConfig.API_KEY

        //Interceptor
        private val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val modifiedRequest = originalRequest.newBuilder()
                    .header("x-rapidapi-key", API_KEY)
                    .build()
                chain.proceed(modifiedRequest)
            }
            .build()

        //Objeto Retrofit
        private val api : NbaAPI = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build().create(NbaAPI::class.java)

        //Recuperar Equipos
        suspend fun getTeams(): ArrayList<Team> {
            Log.d(TAG, "Nba DataSource GET")

            return try {
                val response = api.getTeams()
                Log.d(TAG, "Nba DataSource SUCCESS")
                Log.d(TAG, response.toString())

                //Filtrar equipos franquicia de nba
                ArrayList(response.response
                    .filter { it.nbaFranchise && it.logo?.isNotEmpty() == true })

            } catch (e: Exception) {
                Log.d(TAG, "Nba DataSource ERROR: ${e.message}")
                ArrayList<Team>()
            }
        }

        //Recuperar Jugadores
        suspend fun getPlayers(teamId: Int): ArrayList<Player> {
            Log.d(TAG, "Nba DataSource GET")

            val result = api.getPlayers(teamId).execute()

            return if (result.isSuccessful) {
                Log.d(TAG, "Nba DataSource SUCCESS")
                result.body() ?: ArrayList<Player>()
            } else {
                Log.d(TAG, "Nba DataSource ERROR: ${result.message()}")
                ArrayList<Player>()
            }
        }

        suspend fun setFav(team: Team) {
            if (uid != null) {
                db.collection("favUsuarios")
                    .document(uid).collection("favoritos")
                    .document(team.id.toString())
                    .set(team)
                    .addOnSuccessListener {
                        Log.d(TAG, "Team successfully bookmarked!")
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error bookmarking team", e)
                    }
                    .await()
            }
        }

        suspend fun getFavs(): List<Team> {
            return if (uid != null) {
                val snapshot = db.collection("favUsuarios")
                    .document(uid).collection("favoritos")
                    .get()
                    .await()
                snapshot.documents.mapNotNull { doc ->
                    doc.toObject(Team::class.java)
                }
            } else {
                emptyList()
            }
        }

        suspend fun removeFav(team: Team) {
            if (uid != null) {
                db.collection("favUsuarios")
                    .document(uid).collection("favoritos")
                    .document(team.id.toString())
                    .delete()
                    .addOnSuccessListener {
                        Log.d(TAG, "Team successfully removed from bookmarks!")
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error removing team from bookmarks", e)
                    }
                    .await()
            }
        }

    }
}