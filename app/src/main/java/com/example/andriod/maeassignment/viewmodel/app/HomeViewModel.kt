package com.example.andriod.maeassignment.viewmodel.app

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.andriod.maeassignment.models.Recipe
import com.example.andriod.maeassignment.repository.RecipeRepository

class HomeViewModel: ViewModel() {
    //data passing
    private var _recipesLiveData = ArrayList<Recipe>()
    val recipesData: ArrayList<Recipe>
        get() = _recipesLiveData

    fun getRecipes() {
        _recipesLiveData = RecipeRepository().getRecipes()
        //RecipeRepository().getRecipes()
        Log.e("frag", " VM SUCCESS get $_recipesLiveData")

    }

}