package com.ismin.projectapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CardAdapter(
    private val cards: ArrayList<Card>,
    private val onStartDescritionPushed: (Card) -> Unit
) : RecyclerView.Adapter<CardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val row = LayoutInflater.from(parent.context).inflate(R.layout.row_card, parent, false)
        return CardViewHolder(row)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = this.cards[position]

        holder.txvName.text = card.name
        holder.txvJob.text = card.job
        holder.txvDescription.text = card.description
        holder.itemView.setOnClickListener { onStartDescritionPushed(card) }
    }

    override fun getItemCount(): Int {
        return this.cards.size
    }

    fun updateItem(cardsToDisplay: List<Card>) {
        cards.clear();
        cards.addAll(cardsToDisplay)
        notifyDataSetChanged();
    }
}