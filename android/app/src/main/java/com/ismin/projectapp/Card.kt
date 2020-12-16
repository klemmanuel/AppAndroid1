package com.ismin.projectapp

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Card(
    @SerializedName("name")
    val name: String,
    val job: String,
    val description: String,
    var favori: Boolean,
    @SerializedName("lien_wikipedia")
    val lienWikipedia: String? = null,
    @SerializedName("lien_wikidata")
    val lienWikidata : String? = null,
    @SerializedName("resume")
    val resume : String? = null
) : Serializable
