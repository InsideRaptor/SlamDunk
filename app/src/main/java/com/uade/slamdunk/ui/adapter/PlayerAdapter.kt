package com.uade.slamdunk.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uade.slamdunk.R
import com.uade.slamdunk.model.Player

class PlayerAdapter : RecyclerView.Adapter<PlayerViewHolder>() {

    // List of players
    private var players: MutableList<Player> = ArrayList<Player>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_player, parent, false)
        return PlayerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return players.size
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = players[position]
        val context = holder.itemView.context
        holder.playerName.text = context.getString(R.string.player_full_name, player.firstname, player.lastname)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(lista: MutableList<Player>) {
        players = lista
        this.notifyDataSetChanged()
    }
}

class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val playerName: TextView = itemView.findViewById(R.id.playerName)
}