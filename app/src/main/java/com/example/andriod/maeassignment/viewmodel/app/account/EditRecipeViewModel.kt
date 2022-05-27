package com.example.andriod.maeassignment.viewmodel.app.account

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.andriod.maeassignment.models.Recipe
import com.example.andriod.maeassignment.repository.RecipeRepository

class EditRecipeViewModel : ViewModel() {

    // passing data between fragment
//    private var _recipeId = String()
//    val recipeId: String
//        get() = _recipeId

    //data passing
    private var _recipeLiveData = MutableLiveData<Recipe>()
    val recipeData: MutableLiveData<Recipe>
        get() = _recipeLiveData

    private var _updateRecipeLiveData = MutableLiveData<Boolean>()
    val updateRecipeStatus: MutableLiveData<Boolean>
        get() = _updateRecipeLiveData

    fun getRecipe(recipeId: String) {
        _recipeLiveData = RecipeRepository().getRecipe(recipeId)

    }

    fun  updateRecipe( recipeId: String,recipeTitle: String, recipeDesc: String, imageUrl: Uri?, ingredientsList: ArrayList<String>, methodList: ArrayList<String>,
    ) {
        _updateRecipeLiveData = RecipeRepository().updateRecipe(recipeId, recipeTitle, recipeDesc, imageUrl, ingredientsList, methodList)
    }

}