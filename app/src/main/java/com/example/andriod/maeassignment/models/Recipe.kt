package com.example.andriod.maeassignment.models

data class Recipe(
    val id: String = "",
    val userid: String = "",
    val name: String? = "",
    val title: String = "",
    val desc: String = "",
    val image: String? = "",
    val ingredients: ArrayList<String> = ArrayList(),
    val methods: ArrayList<String> = ArrayList(),
    )


