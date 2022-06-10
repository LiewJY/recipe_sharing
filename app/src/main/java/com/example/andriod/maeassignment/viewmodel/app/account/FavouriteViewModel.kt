package com.example.andriod.maeassignment.viewmodel.app.account

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.andriod.maeassignment.models.Recipe
import com.example.andriod.maeassignment.repository.RecipeRepository

class FavouriteViewModel: ViewModel() {

    //data passing
    private var _favouriteRecipesLiveData = MutableLiveData<ArrayList<Recipe>>()
    val favouriteRecipesData: MutableLiveData<ArrayList<Recipe>>
        get() = _favouriteRecipesLiveData

    private var _removeFavouriteLiveData = MutableLiveData<Int>()
    val removeFavourite: MutableLiveData<Int>
        get() = _removeFavouriteLiveData

    fun getFavouriteRecipes() {
        _favouriteRecipesLiveData = RecipeRepository().getFavourite()
    }

    fun removeFavouriteRecipes(recipeId: String) {
        _removeFavouriteLiveData = RecipeRepository().removeFavourite(recipeId)
    }

}