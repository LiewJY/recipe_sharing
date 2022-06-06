package com.example.andriod.maeassignment.viewmodel.app

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.andriod.maeassignment.models.Recipe
import com.example.andriod.maeassignment.repository.RecipeRepository

class RecipeViewModel : ViewModel() {
    //data passing
    private var _recipesLiveData = MutableLiveData<Recipe>()
    val recipesData: MutableLiveData<Recipe>
        get() = _recipesLiveData

    private var _addFavouriteLiveData = MutableLiveData<Int>()
    val addFavourite: MutableLiveData<Int>
        get() = _addFavouriteLiveData


    fun getRecipe(recipeId: String) {
        _recipesLiveData = RecipeRepository().getRecipe(recipeId)

    }

    fun addFavourite(recipeId: String) {
        _addFavouriteLiveData = RecipeRepository().addFavourite(recipeId)

    }


}