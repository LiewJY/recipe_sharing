package com.example.andriod.maeassignment.viewmodel.app

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.andriod.maeassignment.models.Recipe
import com.example.andriod.maeassignment.repository.RecipeRepository

class HomeViewModel: ViewModel() {
    //data passing
    private var _recipesLiveData = MutableLiveData<ArrayList<Recipe>>()
    val recipesData: MutableLiveData<ArrayList<Recipe>>
        get() = _recipesLiveData

    fun getRecipes() {
        _recipesLiveData = RecipeRepository().getRecipes()
        //RecipeRepository().getRecipes()
    }

}