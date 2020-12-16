package com.ismin.projectapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.ismin.projectapp.DescriptionActivity.Companion.CARD_MESSAGE
import com.ismin.projectapp.databinding.ActivityMainBinding
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity(), DescriptionActivityStarter, CanBeFavorite, CardsGetter {
    private val TAG = MainActivity::class.simpleName
    private val cardService: CardService by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://app-a29f50ab-fa15-4fa0-9560-67f1f17c43cf.cleverapps.io")
            .build()
            .create(CardService::class.java)
    }
    private lateinit var binding: ActivityMainBinding
    private val aboutFragment: AboutFragment by lazy { AboutFragment.newInstance() }
    private val cardListFragment: CardListFragment by lazy { CardListFragment.newInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Add fragments to ViewPager2
        binding.pager.adapter = MainViewPagerAdapter(
            this,
            listOf(
                cardListFragment,
                aboutFragment
            )
        )

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = when (position) {
                0 -> "Cards"
                1 -> "A propos"
                else -> throw RuntimeException("No fragment here.")
            }
        }.attach()
    }

    override fun startDescriptionActivity(card: Card) {
        val intent = Intent(this, DescriptionActivity::class.java)
        intent.putExtra(CARD_MESSAGE, card)
        startActivity(intent)
    }

    private fun displayErrorToast(t: Throwable) {
        Toast.makeText(
            applicationContext,
            "Network error ${t.localizedMessage}",
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menu_action_refresh -> {
            cardListFragment.refresh()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun toggleFavorite(card: Card) {
        cardService.updateCard(card.name, card.copy(favori = !card.favori))
            .enqueue(object : Callback<Card> {
                override fun onResponse(
                    call: Call<Card>,
                    response: Response<Card>
                ) {
                    if (response.code() != 200) {
                        val error = HttpException(response)
                        displayErrorToast(error)
                        Log.e(DescriptionActivity.TAG, error.toString(), error)
                    }
                    cardListFragment.refresh()
                }

                override fun onFailure(call: Call<Card>, t: Throwable) {
                    displayErrorToast(t)
                }
            })
    }

    override fun getAllCards() = cardService.getAllCards()
}