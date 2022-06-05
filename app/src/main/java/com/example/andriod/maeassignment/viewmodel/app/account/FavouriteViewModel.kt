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

    private var _removeFavouriteLiveData = MutableLiveData<Boolean>()
    val removeFavourite: MutableLiveData<Boolean>
        get() = _removeFavouriteLiveData

    fun getFavouriteRecipes() {
        _favouriteRecipesLiveData = RecipeRepository().getFavourite()
        //RecipeRepository().getRecipes()
    }

    fun removeFavouriteRecipes(recipeId: String) {
        _removeFavouriteLiveData = RecipeRepository().removeFavourite(recipeId)
    }

}