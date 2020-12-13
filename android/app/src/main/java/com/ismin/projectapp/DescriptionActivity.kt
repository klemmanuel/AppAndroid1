package com.ismin.projectapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class DescriptionActivity : AppCompatActivity() {
    companion object {
        const val CARD_MESSAGE = "com.ismin.projectapp.CARD_MESSAGE"
    }
    private val card: Card by lazy { intent.getSerializableExtra(CARD_MESSAGE)!! as Card }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        Log.i("DESCRIPTPION", card.toString())
    }
}