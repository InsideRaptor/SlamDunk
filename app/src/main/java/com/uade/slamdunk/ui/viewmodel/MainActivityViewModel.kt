package com.uade.slamdunk.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uade.slamdunk.data.NbaRepository
import com.uade.slamdunk.model.Player
import com.uade.slamdunk.model.Team
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    val TAG = "NBA_API"

    private val nbaRepo: NbaRepository = NbaRepository()
    var teams: MutableLiveData<ArrayList<Team>> = MutableLiveData<ArrayList<Team>>()
    var filteredTeams: MutableLiveData<ArrayList<Team>> = MutableLiveData<ArrayList<Team>>()
    var bookmarkedTeams: MutableLiveData<ArrayList<Team>> = MutableLiveData<ArrayList<Team>>()
    var players: MutableLiveData<ArrayList<Player>> = MutableLiveData<ArrayList<Player>>()

    var isLoading = MutableLiveData<Boolean>()

    init {
        fetchTeams()
    }

    // Recuperar a los equipos desde la API
    private fun fetchTeams() {
        isLoading.value = true
        scope.launch {
            kotlin.runCatching {
                nbaRepo.getTeams()
            }.onSuccess {
                Log.d(TAG, "onSuccess")
                isLoading.postValue(false)
                teams.postValue(it)
                filteredTeams.postValue(it)
                updateBookmarkedTeams()
                Log.d(TAG, it.toString())
            }.onFailure {
                Log.d(TAG, "Error: $it")
                isLoading.postValue(false)
                teams.postValue(ArrayList())
                filteredTeams.postValue(ArrayList())
                bookmarkedTeams.postValue(ArrayList())
            }
        }
    }
    fun filterTeams(query: String) {
        val filteredList = teams.value?.filter {
            it.name.contains(query, ignoreCase = true)
        } ?: ArrayList()
        filteredTeams.postValue(filteredList as ArrayList<Team>?)
    }

    fun updateBookmarkedTeams() {
        val bookmarkedList = teams.value?.filter {
            it.isBookmarked
        } ?: ArrayList()
        bookmarkedTeams.postValue(bookmarkedList as ArrayList<Team>?)
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}