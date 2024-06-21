package com.uade.slamdunk.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uade.slamdunk.R
import com.uade.slamdunk.ui.adapter.PlayerAdapter
import com.uade.slamdunk.ui.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var rvPlayers: RecyclerView
    private lateinit var adapter: PlayerAdapter
    private lateinit var progressBarDetail: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        progressBarDetail = findViewById(R.id.progressBarDetail)


        rvPlayers = findViewById(R.id.rvPlayers)
        rvPlayers.layoutManager = LinearLayoutManager(this)

        adapter = PlayerAdapter()
        rvPlayers.adapter = adapter

        bindViewModel()

        // For player search
        getTeamId()

        viewModel.players.observe(this) { players ->
            adapter.updateItems(players)
        }

    }

    private fun getTeamId() {
        val teamId = intent.getIntExtra("TEAM_ID", -1)
        if (teamId != -1) {
            viewModel.setTeamId(teamId)
        }
        Log.d("NBA_API", "Received Team ID: $teamId")
    }

    private fun bindViewModel() {
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        viewModel.isLoading.observe(this) { isLoading ->
            // Show or hide the progress bar based on the isLoading flag
            progressBarDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

}
