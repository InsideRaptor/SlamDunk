package com.uade.slamdunk.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uade.slamdunk.R
import com.uade.slamdunk.model.Team
import com.uade.slamdunk.ui.viewholder.NbaViewHolder

class NbaAdapter : RecyclerView.Adapter<NbaViewHolder>() {

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
        holder.teamName.text = teams[position].name
        Glide.with(holder.itemView.context)
            .load(teams[position].logo)
            .into(holder.teamLogo)
    }

    fun updateItems(lista: MutableList<Team>) {
        teams = lista
        this.notifyDataSetChanged()
    }

}