package com.ismin.android

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var txvName = itemView.findViewById<TextView>(R.id.r_card_txv_name)
    var txvJob: TextView = itemView.findViewById(R.id.r_card_txv_job)
    var txvDescription: TextView = itemView.findViewById(R.id.r_card_txv_description)
}