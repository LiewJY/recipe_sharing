package com.example.andriod.maeassignment.model

import com.google.firebase.firestore.FieldValue

data class Recipes(
    val id: String = "",
    val userid: String = "", //done
    val name: String? = "", //done
    val title: String = "", //done
    val desc: String = "", //done
    val image: String? = "", //done
    val ingredients: ArrayList<String> = ArrayList(), //done
    val methods: ArrayList<String> = ArrayList(),
    val timestamp: FieldValue? = FieldValue.serverTimestamp(), //done
    )



