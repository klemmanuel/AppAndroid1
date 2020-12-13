package com.ismin.projectapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path


class MainActivity : AppCompatActivity(), DescriptionActivityStarter {
    private val TAG = MainActivity::class.simpleName
    private val cardshelf = Cardshelf()
    private lateinit var cardService: CardService
    private val pager: ViewPager2 by lazy { findViewById<ViewPager2>(R.id.pager) }
    private val tabLayout: TabLayout by lazy { findViewById<TabLayout>(R.id.tab_layout) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://app-fe0b6e61-56d7-403b-8e1d-a5425534a2a7.cleverapps.io")
            .build()

        cardService = retrofit.create(CardService::class.java)

        cardService.getAllCards().enqueue(object : Callback<MutableList<Card>> {
            override fun onResponse(
                call: Call<MutableList<Card>>,
                response: Response<MutableList<Card>>
            ) {
                val allCards = response.body()
                allCards?.forEach {
                    cardshelf.addCard(it)
                    Log.d("hey hey je debuge", "name : "+ it.name + "job: " +it.job )
                }
                displayList()
            }

            override fun onFailure(call: Call<MutableList<Card>>, t: Throwable) {
                displayErrorToast(t)

            }
        })

        // Add fragments to ViewPager2
        pager.adapter = MainViewPagerAdapter(
            this,
            listOf(
                CardListFragment.newInstance(cardshelf.getAllCards()),
                CardListFragment.newInstance(cardshelf.getAllCards()),
            )
        )

        TabLayoutMediator(tabLayout, pager) { tab, position ->
            tab.text = when (position) {
                0 -> "Cards"
                1 -> "A propos"
                else -> throw RuntimeException("No fragment here.")
            }
        }.attach()

    }

    override fun startDescriptionActivity(card: Card) {
        val intent = Intent(this, DescriptionActivity::class.java)
        intent.putExtra("CARDNAME", card.name)
        intent.putExtra("CARDDESC", "card job: " + card.job  + "\ncard de description: " + card.description)
        startActivity(intent)
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
            .commitAllowingStateLoss()
    }

//    fun goToCreation(view: View) {
//        val createCardFragment = CreateCardFragment()
//
//        supportFragmentManager.beginTransaction()
//            .add(R.id.a_main_lyt_container, createCardFragment)
//            .addToBackStack("createCardFragment")
//            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//            .commit()
//
//        a_main_btn_creation.visibility = View.GONE
//    }

//    override fun onCardCreated(card: Card) {
//        cardService.createCard(card).enqueue {
//            onResponse = {
//                try {
//                    cardshelf.addCard(card)
//                } catch (e: Throwable) {
//                    Log.e(TAG, e.toString(), e)
//                } finally {
//                    closeCreateFragment()
//                }
//            }
//            onFailure = {
//                if (it != null) {
//                    displayErrorToast(it)
//                }
//            }
//        }
//    }

//    override fun closeCreateFragment() {
//        displayList()
//
//        a_main_btn_creation.visibility = View.VISIBLE
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem)= when(item.itemId) {
        R.id.menu_action_clear -> {
            TODO("Action clear")
            true
        }
        R.id.menu_action_refresh -> {
            TODO("Action refresh")
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}