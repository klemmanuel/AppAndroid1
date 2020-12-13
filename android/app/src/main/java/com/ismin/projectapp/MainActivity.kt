package com.ismin.projectapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.ismin.projectapp.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity(), CardCreator {
    private val TAG = MainActivity::class.simpleName
    private val cardshelf = Cardshelf()
    private lateinit var cardService: CardService;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://Cardshelf-gme.cleverapps.io")
            .build()

        cardService = retrofit.create(CardService::class.java)

        cardService.getAllCards().enqueue(object : Callback<ArrayList<Card>> {
            override fun onResponse(
                call: Call<ArrayList<Card>>,
                response: Response<ArrayList<Card>>
            ) {
                val allCards = response.body()
                allCards?.forEach {
                    cardshelf.addCard(it)
                }

                displayList()
            }

            override fun onFailure(call: Call<ArrayList<Card>>, t: Throwable) {
                displayErrorToast(t)

            }
        })

    }

    private fun displayErrorToast(t: Throwable) {
        Toast.makeText(
            applicationContext,
            "Network error ${t.localizedMessage}",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun displayList() {
        val cardListFragment = CardListFragment.newInstance(cardshelf.getAllCards())

        supportFragmentManager.beginTransaction()
            .replace(R.id.a_main_lyt_container, cardListFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    fun goToCreation(view: View) {
        val createCardFragment = CreateCardFragment()

        supportFragmentManager.beginTransaction()
            .add(R.id.a_main_lyt_container, createCardFragment)
            .addToBackStack("createCardFragment")
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()

        a_main_btn_creation.visibility = View.GONE
    }

    override fun onCardCreated(card: Card) {
        cardService.createCard(card).enqueue {
            onResponse = {
                cardshelf.addCard(it.body()!!)
                closeCreateFragment()
            }
            onFailure = {
                if (it != null) {
                    displayErrorToast(it)
                }
            }
        }
    }

    override fun closeCreateFragment() {
        displayList();

        a_main_btn_creation.visibility = View.VISIBLE
    }
}