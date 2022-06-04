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
import com.example.andriod.maeassignment.ui.app.HomeAdapter
import com.example.andriod.maeassignment.ui.app.recipe.RecipeActivity
import com.example.andriod.maeassignment.viewmodel.app.account.FavouriteViewModel

class FavouriteFragment : Fragment(), HomeAdapter.OnItemClickListener {
    private lateinit var recyclerView : RecyclerView

    private val viewModel: FavouriteViewModel by lazy {
        ViewModelProvider(this).get(FavouriteViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentFavouriteBinding>(
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
            recyclerView.adapter = context?.let { HomeAdapter(it, recipes, this) }
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