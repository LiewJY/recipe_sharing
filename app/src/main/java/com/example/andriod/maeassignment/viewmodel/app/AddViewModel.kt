package com.example.andriod.maeassignment.viewmodel.app

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.andriod.maeassignment.repository.RecipeRepository


class AddViewModel : ViewModel() {

    //data passing
    private var _addRecipeLiveData = MutableLiveData<Boolean>()
    val addRecipeStatus: MutableLiveData<Boolean>
        get() = _addRecipeLiveData


    fun addRecipe(recipeTitle: String, recipeDesc: String, imageUrl: Uri?, ingredientsList: ArrayList<String>, methodList: ArrayList<String>,
    ) {
        _addRecipeLiveData = RecipeRepository().addRecipe(recipeTitle, recipeDesc, imageUrl, ingredientsList, methodList)
    }


}