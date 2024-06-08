package com.uade.slamdunk.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uade.slamdunk.R
import com.uade.slamdunk.model.Team
import com.uade.slamdunk.ui.viewholder.NbaViewHolder
import com.uade.slamdunk.ui.viewmodel.MainActivityViewModel

class NbaAdapter(private val viewModel: MainActivityViewModel) : RecyclerView.Adapter<NbaViewHolder>() {

    // List of teams
    private var teams: MutableList<Team> = ArrayList<Team>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NbaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return NbaViewHolder(view)
    }

    override fun getItemCount(): Int {
        return teams.size
    }

    override fun onBindViewHolder(holder: NbaViewHolder, position: Int) {
        // Get the team at the current position
        holder.teamName.text = teams[position].name

        // Load the team logo using Glide
        Glide.with(holder.itemView.context)
            .load(teams[position].logo)
            .into(holder.teamLogo)

        // Set bookmark icon based on bookmark status
        holder.bookmarkButton.setImageResource(
            if (teams[position].isBookmarked) R.drawable.bookmark_delete_icon else R.drawable.bookmark_add_icon
        )

        // Set click listener for the bookmark button
        holder.bookmarkButton.setOnClickListener {
            // Toggle bookmark status
            teams[position].isBookmarked = !teams[position].isBookmarked
            // Update icon
            holder.bookmarkButton.setImageResource(
                if (teams[position].isBookmarked) R.drawable.bookmark_delete_icon else R.drawable.bookmark_add_icon
            )
            // Update ViewModel
            viewModel.toggleBookmark(teams[position])
            notifyItemChanged(position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(lista: MutableList<Team>) {
        teams = lista
        this.notifyDataSetChanged()
    }

}