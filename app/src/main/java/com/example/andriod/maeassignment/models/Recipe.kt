package com.example.andriod.maeassignment.models

data class Recipe(
    val userid: String = "",
    val title: String = "", //done
    val desc: String = "", //done
    val image: String? = "",
    val ingredients: ArrayList<String> = ArrayList(), //done
    val methods: ArrayList<String> = ArrayList(), //done
    )


