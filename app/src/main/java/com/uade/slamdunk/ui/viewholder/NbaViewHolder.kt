package com.uade.slamdunk.ui.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uade.slamdunk.R

class NbaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val teamName: TextView = itemView.findViewById(R.id.btnTeamName)
    val teamLogo: ImageView = itemView.findViewById(R.id.teamLogo)
}