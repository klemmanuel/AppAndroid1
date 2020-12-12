package com.ismin.projectapp

import com.ismin.projectapp.Personne
import java.io.Serializable
data class Element(var name: String, var photoURL: String, var personnes: ArrayList<Personne>): Serializable