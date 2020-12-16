package com.ismin.projectapp

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ismin.projectapp.databinding.ActivityDescriptionBinding
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class DescriptionActivity : AppCompatActivity() {
    companion object {
        val TAG = DescriptionActivity::class.simpleName
        const val CARD_MESSAGE = "com.ismin.projectapp.CARD_MESSAGE"
    }

    private val initialCard: Card by lazy { intent.getSerializableExtra(CARD_MESSAGE)!! as Card }
    private val cardService: CardService by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://app-a29f50ab-fa15-4fa0-9560-67f1f17c43cf.cleverapps.io")
            .build()
            .create(CardService::class.java)
    }
    private lateinit var binding: ActivityDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cardService.getCard(initialCard.name).enqueue(object : Callback<Card> {
            override fun onFailure(call: Call<Card>, t: Throwable) {
                displayErrorToast(t)
                Log.e(TAG, t.toString(), t)
            }

            override fun onResponse(call: Call<Card>, response: Response<Card>) {
                if (response.code() != 200) {
                    val error = HttpException(response)
                    displayErrorToast(error)
                    Log.e(TAG, error.toString(), error)
                }
               response.body()?.let {
                   binding.cardName.text = it.name
                   binding.cardDescription.text = it.description
                   binding.cardWikipedia.text = it.lienWikipedia
                   binding.cardWikipedia.setOnClickListener { _ ->
                       val intent = Intent(Intent.ACTION_VIEW).apply {
                           data = Uri.parse(it.lienWikipedia)
                       }
                       startActivity(intent)
                   }
                   binding.cardWikidata.text = it.lienWikidata
                   binding.cardWikidata.setOnClickListener { _ ->
                       val intent = Intent(Intent.ACTION_VIEW).apply {
                           data = Uri.parse(it.lienWikidata)
                       }
                       startActivity(intent)
                   }
                   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                       binding.cardResume.text = Html.fromHtml(it.resume, Html.FROM_HTML_MODE_LEGACY)
                   } else {
                       binding.cardResume.text = Html.fromHtml(it.resume)
                   }
               }
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
}