package com.example.andriod.maeassignment.ui.app.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.andriod.maeassignment.R
import com.example.andriod.maeassignment.databinding.FragmentFavouriteBinding
import com.example.andriod.maeassignment.ui.app.recipe.RecipeActivity
import com.example.andriod.maeassignment.viewmodel.app.account.FavouriteViewModel

class FavouriteFragment : Fragment(), FavouriteAdapter.OnItemClickListener {
    private lateinit var recyclerView : RecyclerView

    private val viewModel: FavouriteViewModel by lazy {
        ViewModelProvider(this).get(FavouriteViewModel::class.java)
    }
    private lateinit var binding: FragmentFavouriteBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
         binding = DataBindingUtil.inflate<FragmentFavouriteBinding>(
            inflater,
            R.layout.fragment_favourite, container, false
        )

        binding.favouriteToolbar.setOnClickListener{
            activity?.onBackPressed()
        }
        viewModel.getFavouriteRecipes()

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //load data into recycler view
        viewModel.favouriteRecipesData.observe(viewLifecycleOwner) { recipes ->
            recyclerView = view.findViewById(R.id.favouriteRecyclerView)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = context?.let { FavouriteAdapter(it, recipes, this) }
            if(recipes.isEmpty()) {
                Toast.makeText(context, "You have no favourite recipe", Toast.LENGTH_SHORT).show()
            }
        }


    }
    override fun onRemoveClick(recipeId: String) {
        viewModel.removeFavouriteRecipes(recipeId)
        viewModel.removeFavourite.observe(this) { result ->
            if (result == 1) {
                refresh()
                Toast.makeText(context, "Recipe removed from favourite", Toast.LENGTH_SHORT).show()
            }else if (result == 2) {
                Toast.makeText(context, "Recipe failed to remove from favourite", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun refresh() {
        viewModel.getFavouriteRecipes()
        //load data into recycler view
        viewModel.favouriteRecipesData.observe(viewLifecycleOwner) { recipes ->
            recyclerView = requireView().findViewById(R.id.favouriteRecyclerView)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = context?.let { FavouriteAdapter(it, recipes, this) }
            Toast.makeText(context, "You have no favourite recipe", Toast.LENGTH_SHORT).show()
        }
    }



    override fun onItemClick(recipeId: String) {
        //open recipe page
        val intent = Intent(activity, RecipeActivity::class.java)
        intent.putExtra("recipeId", recipeId)
        startActivity(intent)
    }


}