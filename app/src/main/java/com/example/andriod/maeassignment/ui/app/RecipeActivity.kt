package com.example.andriod.maeassignment.ui.app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.andriod.maeassignment.databinding.ActivityRecipeBinding
import com.example.andriod.maeassignment.viewmodel.app.RecipeViewModel

class RecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeBinding

    private val viewModel: RecipeViewModel by lazy {
        ViewModelProvider(this).get(RecipeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //hide actiton bar
        supportActionBar?.hide()
        //setContentView(R.layout.activity_recipe)

        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //binding.test.text = "ssssssssssssssssssssss"



        val recipeId = intent.getStringExtra("recipeId")
        Log.e("frag", "$recipeId")
        viewModel.getRecipe(recipeId!!)
        viewModel.recipesData.observe(this) { recipe ->
            if (recipe != null) {
                Log.e("frag", "obserce $recipe")

            }
        }

    }
}