package com.ismin.projectapp

import java.io.Serializable

class Cardshelf : Serializable {
    private val storage = HashMap<String, Card>()

    fun addCard(card: Card) {
        this.storage[card.name] = card
    }

    fun getCard(name: String): Card? {
        return this.storage[name]
    }

    fun getAllCards(): ArrayList<Card> {
        return ArrayList(this.storage.values.sortedBy { card -> card.name })
    }

    fun getCardsOf(author: String): List<Card> {
        val filteredStorage = this.storage.filter { it.value.job == author }
        return ArrayList(filteredStorage.values).sortedBy { card -> card.name }
    }

    fun getTotalNumberOfCards(): Int {
        return this.storage.size
    }

    fun clear(): Unit {
        this.storage.clear()
    }
}
