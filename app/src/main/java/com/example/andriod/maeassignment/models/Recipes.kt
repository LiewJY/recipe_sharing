package com.example.andriod.maeassignment.model

import com.google.firebase.firestore.FieldValue

data class Recipes(
    val id: String = "",
    val userid: String = "",
    val name: String? = "",
    val title: String = "",
    val desc: String = "",
    val image: String? = "",
    val ingredients: ArrayList<String> = ArrayList(),
    val methods: ArrayList<String> = ArrayList(),
    val timestamp: FieldValue? = FieldValue.serverTimestamp(),
    )



