package com.ismin.projectapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ismin.projectapp.databinding.RowCardBinding

class CardAdapter(
    private val onStartDescritionPushed: (Card) -> Unit,
    private val onFavorite: (Card) -> Unit,
) : ListAdapter<Card, CardAdapter.CardViewHolder>(COMPARATOR) {
    object COMPARATOR : DiffUtil.ItemCallback<Card>() {
        override fun areItemsTheSame(oldItem: Card, newItem: Card) = oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Card, newItem: Card) = oldItem == newItem
    }

    class CardViewHolder(
        private val binding: RowCardBinding,
        private val onStartDescritionPushed: (Card) -> Unit,
        private val onFavorite: (Card) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(card: Card) {
            binding.rCardTxvName.text = card.name
            binding.rCardTxvJob.text = card.job
            binding.buttonFavorite.setImageDrawable(
                if (card.favori) ContextCompat.getDrawable(
                    binding.buttonFavorite.context,
                    R.drawable.ic_baseline_star_24
                ) else ContextCompat.getDrawable(
                    binding.buttonFavorite.context,
                    R.drawable.ic_baseline_star_border_24
                )
            )
            binding.rCardTxvDescription.text = card.description
            itemView.setOnClickListener { onStartDescritionPushed(card) }
            binding.buttonFavorite.setOnClickListener { onFavorite(card) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = RowCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding, onStartDescritionPushed, onFavorite)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) =
        holder.bind(getItem(position))
}