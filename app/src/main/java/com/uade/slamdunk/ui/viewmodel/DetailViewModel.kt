package com.uade.slamdunk.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.uade.slamdunk.data.AppDatabase
import com.uade.slamdunk.data.NbaRepository
import com.uade.slamdunk.data.PlayersLocalRepository
import com.uade.slamdunk.model.Player
import com.uade.slamdunk.model.PlayerDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailViewModel (application: Application) : AndroidViewModel(application) {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    private val tag = "NBA_API"

    private val nbaRepo: NbaRepository = NbaRepository()
    var players: MutableLiveData<ArrayList<Player>> = MutableLiveData<ArrayList<Player>>()
    private val selectedTeamId: MutableLiveData<Int> = MutableLiveData()
    val selectedTeamName: MutableLiveData<String> = MutableLiveData()
    val selectedTeamLogo: MutableLiveData<String> = MutableLiveData()

    var isLoading = MutableLiveData<Boolean>()

    private val playerRepository: PlayersLocalRepository
    private val playerDao: PlayerDAO

    init {
        val db = AppDatabase.getInstance(application)
        playerDao = db.playerDao()
        playerRepository = PlayersLocalRepository(playerDao)
    }

    fun setTeam(teamId: Int, teamName: String, teamLogo: String) {
        selectedTeamId.value = teamId
        selectedTeamName.value = teamName
        selectedTeamLogo.value = teamLogo
        fetchPlayers(teamId)
    }

    // Recuperar a los jugadores desde la API
    private fun fetchPlayers(teamId: Int) {
        isLoading.value = true
        scope.launch {
            // Load players from local database first
            val playersFromDb = playerRepository.getPlayers(teamId)
            if (playersFromDb.isNotEmpty()) {
                players.postValue(ArrayList(playersFromDb))
                isLoading.postValue(false)
            } else {
                // If no players in local database, fetch from API
                kotlin.runCatching {
                    nbaRepo.getPlayers(teamId)
                }.onSuccess {
                    Log.d(tag, "onSuccessPlayers")
                    players.postValue(ArrayList(it))
                    savePlayersLocally(it, teamId)
                    isLoading.postValue(false)
                }.onFailure {
                    Log.d(tag, "Error: $it")
                    players.postValue(ArrayList())
                    isLoading.postValue(false)
                }
            }
        }
    }

    private suspend fun savePlayersLocally(players: List<Player>, teamId: Int) {
        playerRepository.insertPlayers(players, teamId)
    }

}