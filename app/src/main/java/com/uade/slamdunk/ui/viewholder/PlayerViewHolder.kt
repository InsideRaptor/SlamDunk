package com.uade.slamdunk.ui.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uade.slamdunk.R

class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val playerName: TextView = itemView.findViewById(R.id.playerName)
}