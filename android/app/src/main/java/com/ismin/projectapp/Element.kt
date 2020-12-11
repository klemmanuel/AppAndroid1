package com.ismin.opendataapp

import java.io.Serializable
data class Element(var name: String, var photoURL: String, var personnes: ArrayList<Personne>): Serializable