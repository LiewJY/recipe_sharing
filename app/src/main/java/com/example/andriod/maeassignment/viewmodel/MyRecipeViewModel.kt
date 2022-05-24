package com.example.andriod.maeassignment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.andriod.maeassignment.models.Recipe
import com.example.andriod.maeassignment.repository.RecipeRepository

class MyRecipeViewModel: ViewModel() {
    //data passing
    private var _myRecipesLiveData = MutableLiveData<ArrayList<Recipe>>()
    val myrecipesData: MutableLiveData<ArrayList<Recipe>>
        get() = _myRecipesLiveData

    //data passing
    private var _deleteRecipeLiveData = MutableLiveData<Boolean>()
    val deleteRecipeStatus: MutableLiveData<Boolean>
        get() = _deleteRecipeLiveData

    fun getRecipeByAuthor() {
        _myRecipesLiveData = RecipeRepository().getRecipesByAuthor()
        //RecipeRepository().getRecipes()
    }

    fun deleteRecipe(recipeId: String) {
        _deleteRecipeLiveData = RecipeRepository().deleteRecipe(recipeId)
    }

}