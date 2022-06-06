package com.example.andriod.maeassignment.ui.app.recipe

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.andriod.maeassignment.R
import com.example.andriod.maeassignment.databinding.ActivityRecipeBinding
import com.example.andriod.maeassignment.viewmodel.app.RecipeViewModel

class RecipeActivity : AppCompatActivity(){

    private lateinit var binding: ActivityRecipeBinding
    private lateinit var ingredientRecyclerView : RecyclerView
    private lateinit var methodRecyclerView : RecyclerView


    private val viewModel: RecipeViewModel by lazy {
        ViewModelProvider(this).get(RecipeViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //hide actiton bar
        supportActionBar?.hide()
        //recipeToolbar.setNavigationIcon(R.drawable.ic_notifications_black_24dp)
//        supportActionBar?.title = "test"
//        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_home_black_24dp)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        //action bar

        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.recipeToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24)
        binding.recipeToolbar.setOnClickListener{
            finish()
        }
        val recipeId = intent.getStringExtra("recipeId")
        //Log.e("frag", "$recipeId")
        viewModel.getRecipe(recipeId!!)
        viewModel.recipesData.observe(this) { recipe ->
            if (recipe != null) {
                Log.e("frag", "obserce $recipe")
                binding.tvRecipeTitle.text = recipe.title
                binding.tvRecipeAuthor.text = recipe.name
                Glide.with(this)
                    .load(recipe.image)
                    .placeholder(R.mipmap.ic_launcher_round)
                    .into(binding.imageRecipes)

                binding.tvRecipeDesc.text = recipe.desc

                ingredientRecyclerView = binding.ingredientRecyclerView
                ingredientRecyclerView.adapter = IngredientMethodAdapter( recipe.ingredients)
                ingredientRecyclerView.isNestedScrollingEnabled = false
                ingredientRecyclerView.layoutManager = LinearLayoutManager(this)

                methodRecyclerView = binding.methodRecyclerView
                methodRecyclerView.adapter = IngredientMethodAdapter( recipe.methods)
                methodRecyclerView.isNestedScrollingEnabled = false
                methodRecyclerView.layoutManager = LinearLayoutManager(this)
                //binding.tvRecipeIngredient.text = recipe.ingredients.toString()

                //binding.txtRecipeDesc.setText(recipe.title)


            }
        }

        binding.btnFavourite.setOnClickListener{
            viewModel.addFavourite(recipeId)
            viewModel.addFavourite.observe(this) { result ->
                if (result == 1) {
                    Toast.makeText(this, "${binding.tvRecipeTitle.text} added to favourite", Toast.LENGTH_SHORT).show()
                }else if (result == 2) {
                    Toast.makeText(this, "${binding.tvRecipeTitle.text} already added to favourite", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

}