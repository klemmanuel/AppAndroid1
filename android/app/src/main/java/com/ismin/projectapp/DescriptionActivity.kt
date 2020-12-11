package com.ismin.opendataapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.ismin.projectapp.R
import com.squareup.picasso.Picasso

class DescriptionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        //définition des variables
        val intent = this.intent
        val bundle = intent.extras
        val backButton: ImageButton

        //on récupère les données
        val data = bundle!!.getSerializable("Element") as Element
        val (name, imageURL, personnes) = data

        //on affiche les données
        this.findViewById<TextView>(R.id.titleDescription).text = name
        var textBiography: StringBuffer = StringBuffer()
        for(personne in personnes){
            textBiography.append("<Strong>Nom :</Strong> ${personne.name}\"")
            textBiography.append("<br><Strong>Activité</Strong> ${personne.activité}\"")
            textBiography.append("<br><Strong>date naissance</Strong> ${personne.date_naissance}\"")
            textBiography.append("<br><Strong>date décès :</Strong> ${personne.date_mort}")
            textBiography.append("<br><a href = ${personne.lien_wikipedia}> en savoir plus : ${personne.lien_wikipedia} </a> <br><br>")
        }
        this.findViewById<TextView>(R.id.biography).text = Html.fromHtml(textBiography.toString())

        Picasso.get()
            .load(imageURL)
            .into(this.findViewById<ImageView>(R.id.photoDescription))

        //Listener qui termine l'activity et retourne au MainActivity
        backButton = this.findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }
    }
}
