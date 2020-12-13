package com.ismin.android

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CardAdapter(private val cards: ArrayList<Card>) : RecyclerView.Adapter<CardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val row = LayoutInflater.from(parent.context).inflate(R.layout.row_card, parent, false)
        return CardViewHolder(row)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val (name, job, description) = cards[position]

        holder.txvName.text = name
        holder.txvJob.text = job
        holder.txvDescription.text = description
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    fun refreshData(updatedCards: java.util.ArrayList<Card>) {
        cards.clear()
        cards.addAll(updatedCards)
    }
}