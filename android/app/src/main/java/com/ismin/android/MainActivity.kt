package com.ismin.android

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), CardCreator {
    private val TAG = MainActivity::class.simpleName

    private val cardshelf = Cardshelf()
    private val theLordOfTheRings = Card(
        name = "The Lord of the Rings",
        job = "J. R. R. Tolkien",
        description = "1954-02-15"
    )

    private val theHobbit = Card(
        name = "The Hobbit",
        job = "J. R. R. Tolkien",
        description = "1937-09-21"
    )
    private val aLaRechercheDuTempsPerdu = Card(
        name = "À la recherche du temps perdu",
        job = "Marcel Proust",
        description = "1927"
    );


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.cardshelf.addCard(theLordOfTheRings)
        this.cardshelf.addCard(theHobbit)
        this.cardshelf.addCard(aLaRechercheDuTempsPerdu)

        displayList()
    }

    private fun displayList() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val cardListFragment = CardListFragment.newInstance(this.cardshelf.getAllCards())

        fragmentTransaction.replace(R.id.a_main_lyt_fragment_container, cardListFragment)
        fragmentTransaction.commit()

        a_main_btn_creation.visibility = View.VISIBLE
    }

    private fun displayCreation() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val createCardFragment = CreateCardFragment()

        fragmentTransaction.add(R.id.a_main_lyt_fragment_container, createCardFragment)
        fragmentTransaction.commit()

        a_main_btn_creation.visibility = View.GONE
    }

    fun goToCreation(view: View) {
        displayCreation()
    }

    override fun onCardCreated(card: Card) {
        cardshelf.addCard(card)
        displayList()
    }

    override fun closeCardCreation() {
        displayList()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_action_clear -> {
                cardshelf.clear()
                displayList()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}