package com.uade.slamdunk.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uade.slamdunk.data.NbaRepository
import com.uade.slamdunk.model.Player
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    private val tag = "NBA_API"

    private val nbaRepo: NbaRepository = NbaRepository()
    var players: MutableLiveData<ArrayList<Player>> = MutableLiveData<ArrayList<Player>>()
    private val selectedTeamId: MutableLiveData<Int> = MutableLiveData()

    var isLoading = MutableLiveData<Boolean>()

    fun setTeamId(teamId: Int) {
        selectedTeamId.value = teamId
        fetchPlayers(teamId)
    }

    // Recuperar a los jugadores desde la API
    private fun fetchPlayers(teamId: Int) {
        isLoading.value = true
        scope.launch {
            kotlin.runCatching {
                nbaRepo.getPlayers(teamId)
            }.onSuccess {
                Log.d(tag, "onSuccessPlayers")
                isLoading.postValue(false)
                players.postValue(it)
                Log.d(tag, it.toString())
            }.onFailure {
                Log.d(tag, "Error: $it")
                isLoading.postValue(false)
                players.postValue(ArrayList())

            }
        }
    }

}