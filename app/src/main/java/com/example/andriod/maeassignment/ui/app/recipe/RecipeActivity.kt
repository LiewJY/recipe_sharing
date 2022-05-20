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

class RecipeActivity : AppCompatActivity(), IngredientMethodAdapter.OnItemClickListener {

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

        //action bar

        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recipeId = intent.getStringExtra("recipeId")
        //Log.e("frag", "$recipeId")
        viewModel.getRecipe(recipeId!!)
        viewModel.recipesData.observe(this) { recipe ->
            if (recipe != null) {
                Log.e("frag", "obserce $recipe")
                binding.tvRecipeTitle.text = recipe.title
                binding.tvRecipeAuthor.text = recipe.userid
                Glide.with(this)
                    .load(recipe.image)
                    .placeholder(R.mipmap.ic_launcher_round)
                    .into(binding.imageRecipes)

                binding.tvRecipeDesc.text = recipe.desc

        //val myDataset = Datasource().loadAffirmations()
                //Log.e("frag", "SUCCESS frag get ${recipe.ingredients}")


                ingredientRecyclerView = binding.ingredientRecyclerView
                ingredientRecyclerView.adapter = IngredientMethodAdapter( recipe.ingredients,this)
                ingredientRecyclerView.isNestedScrollingEnabled = false
                ingredientRecyclerView.layoutManager = LinearLayoutManager(this)

                methodRecyclerView = binding.methodRecyclerView
                methodRecyclerView.adapter = IngredientMethodAdapter( recipe.methods,this)
                methodRecyclerView.isNestedScrollingEnabled = false
                methodRecyclerView.layoutManager = LinearLayoutManager(this)
                //binding.tvRecipeIngredient.text = recipe.ingredients.toString()

                //binding.txtRecipeDesc.setText(recipe.title)


            }
        }

    }
    override fun onItemClick(position: Int) {
        Log.e("frag", "clicked dddd")

        Toast.makeText(this, "Item $position clicked", Toast.LENGTH_SHORT).show()

    }
}