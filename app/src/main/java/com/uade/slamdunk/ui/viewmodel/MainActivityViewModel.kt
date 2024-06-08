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

    private val tag = "NBA_API"

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
                Log.d(tag, "onSuccess")
                isLoading.postValue(false)
                teams.postValue(it)
                filteredTeams.postValue(it)
                fetchBookmarkedTeams()
                Log.d(tag, it.toString())
            }.onFailure {
                Log.d(tag, "Error: $it")
                isLoading.postValue(false)
                teams.postValue(ArrayList())
                filteredTeams.postValue(ArrayList())
            }
        }
    }
    fun filterTeams(query: String) {
        val filteredList = teams.value?.filter {
            it.name.contains(query, ignoreCase = true)
        } ?: ArrayList()
        filteredTeams.postValue(filteredList as ArrayList<Team>?)
    }

    private fun fetchBookmarkedTeams() {
        scope.launch {
            val bookmarkedList = nbaRepo.getFavs()
            val bookmarkedIds = bookmarkedList.map { it.id }.toSet()

            // Get the current list of teams
            val currentTeams = teams.value ?: ArrayList()

            // Update the bookmark status of the current teams
            currentTeams.forEach { team ->
                team.isBookmarked = bookmarkedIds.contains(team.id)
            }

            // Post the updated list to the LiveData
            teams.postValue(currentTeams)
            filteredTeams.postValue(currentTeams)
            bookmarkedTeams.postValue(ArrayList(bookmarkedList))
        }
    }

    fun toggleBookmark(team: Team) {
        scope.launch {
            if (team.isBookmarked) {
                nbaRepo.setFav(team)
            } else {
                nbaRepo.removeFav(team)
            }
            val currentTeams = teams.value ?: ArrayList()
            currentTeams.find { it.id == team.id }?.isBookmarked = team.isBookmarked
            teams.postValue(currentTeams)
            filteredTeams.postValue(currentTeams)

            // Refresh bookmarks
            fetchBookmarkedTeams()
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}