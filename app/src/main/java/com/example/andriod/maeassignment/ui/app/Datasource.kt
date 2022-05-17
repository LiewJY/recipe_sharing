package com.example.andriod.maeassignment.ui.app

import com.example.andriod.maeassignment.models.Recipe

class Datasource {
    fun loadAffirmations(): List<Recipe> {
        return listOf<Recipe>(
            Recipe("id", "title", "desc", "img"),
            Recipe("id", "title 2", "desc", "img"),
            Recipe("id", "title 3", "desc", "img"),

            )

    }
}