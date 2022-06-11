package com.example.andriod.maeassignment.models


data class User (
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val mobile: String = "0",
    val favourite: ArrayList<String> = ArrayList(),
        )