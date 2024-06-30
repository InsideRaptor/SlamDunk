package com.uade.slamdunk.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uade.slamdunk.R
import com.uade.slamdunk.ui.adapter.PlayerAdapter
import com.uade.slamdunk.ui.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var rvPlayers: RecyclerView
    private lateinit var adapter: PlayerAdapter
    private lateinit var progressBarDetail: ProgressBar
    private lateinit var teamLogoImageView: ImageView
    private lateinit var teamNameTextView: TextView
    private lateinit var backButton: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        teamLogoImageView = findViewById(R.id.detailLogo)
        teamNameTextView = findViewById(R.id.detailTeamName)
        progressBarDetail = findViewById(R.id.progressBarDetail)
        backButton = findViewById(R.id.btnBack)

        bindViewModel()

        rvPlayers = findViewById(R.id.rvPlayers)
        rvPlayers.layoutManager = GridLayoutManager(this, 2)

        adapter = PlayerAdapter()
        rvPlayers.adapter = adapter

        // For player search and info
        getTeam()

        viewModel.players.observe(this) { players ->
            adapter.updateItems(players)
        }

        viewModel.selectedTeamName.observe(this) { teamName ->
            teamNameTextView.text = teamName
        }

        viewModel.selectedTeamLogo.observe(this) { teamLogo ->
            Glide.with(this)
                .load(teamLogo)
                .into(teamLogoImageView)
        }

        backButton.setOnClickListener {
            navigateToMainActivity()
        }

    }

    private fun getTeam() {
        val teamId = intent.getIntExtra("TEAM_ID", -1)
        val teamName = intent.getStringExtra("TEAM_NAME")
        val teamLogo = intent.getStringExtra("TEAM_LOGO")
        if (teamId != -1 && teamName != null && teamLogo != null) {
            viewModel.setTeam(teamId, teamName, teamLogo)
        }
        Log.d("NBA_API", "Received Team ID: $teamId")
        Log.d("NBA_API", "Received Team Name: $teamName")
        Log.d("NBA_API", "Received Team Logo: $teamLogo")
    }

    private fun bindViewModel() {
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        viewModel.isLoading.observe(this) { isLoading ->
            // Show or hide the progress bar based on the isLoading flag
            progressBarDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

}
