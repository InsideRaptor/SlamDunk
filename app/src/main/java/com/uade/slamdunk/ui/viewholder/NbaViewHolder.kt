package com.uade.slamdunk.ui.viewholder

import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uade.slamdunk.R

class NbaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val teamName: TextView = itemView.findViewById(R.id.btnTeamName)
    val teamLogo: ImageView = itemView.findViewById(R.id.teamLogo)
    val bookmarkButton: ImageButton = itemView.findViewById(R.id.btnBookmark)
    val teamNameButton: Button = itemView.findViewById(R.id.btnTeamName)
}