package com.ismin.opendataapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ElementAdapter(private val icons: ArrayList<Element> , private val onItemClick: (Int) -> Unit) :
    RecyclerView.Adapter<ElementViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementViewHolder {
        val row = LayoutInflater.from(parent.context).inflate(R.layout.element_liste, parent,
            false)
        return ElementViewHolder(row, onItemClick)
    }

    override fun onBindViewHolder(viewholder: ElementViewHolder, position: Int) {
        val (name, imageURL) = this.icons[position]

        viewholder.txvName.text = name
        viewholder.updateWithUrl(imageURL)
    }

    override fun getItemCount(): Int {
        return this.icons.size
    }
}