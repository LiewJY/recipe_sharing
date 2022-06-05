package com.example.andriod.maeassignment.ui.app.account

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FavouriteFragment : Fragment(), FavouriteAdapter.OnItemClickListener {
    private lateinit var recyclerView : RecyclerView
    private lateinit var binding: FragmentFavouriteBinding

    private val viewModel: FavouriteViewModel by lazy {
        ViewModelProvider(this).get(FavouriteViewModel::class.java)
    }


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
            Log.e("frag", "SUCCESS frag get $recipes")

            recyclerView = view.findViewById(R.id.favouriteRecyclerView)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = context?.let { FavouriteAdapter(it, recipes, this) }
        }

    }
    private val mFireStore = FirebaseFirestore.getInstance()
    private var currentFirebaseUser = FirebaseAuth.getInstance().currentUser

    override fun onRemoveClick(recipeId: String) {
        Log.e("frag", "test fav remove ")
        viewModel.removeFavouriteRecipes(recipeId)
        viewModel.removeFavourite.observe(this) { status ->
            if (status == true) {
                Toast.makeText(context, "Removed recipe form favourite.", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context, "Failed to remove recipe from favourite.", Toast.LENGTH_SHORT).show()
            }
        }
        refresh()

    }

    fun refresh() {
        viewModel.getFavouriteRecipes()
        //load data into recycler view
        viewModel.favouriteRecipesData.observe(viewLifecycleOwner) { recipes ->
            Log.e("frag", "SUCCESS frag get $recipes")

            recyclerView = requireView().findViewById(R.id.favouriteRecyclerView)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = context?.let { FavouriteAdapter(it, recipes, this) }
        }
    }



    override fun onItemClick(recipeId: String) {
        Toast.makeText(context, "Item $recipeId clicked", Toast.LENGTH_SHORT).show()
        //open recipe page
        val intent = Intent(activity, RecipeActivity::class.java)
        intent.putExtra("recipeId", recipeId)
        startActivity(intent)

    }


}