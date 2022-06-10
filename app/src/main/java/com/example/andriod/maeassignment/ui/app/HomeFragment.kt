package com.example.andriod.maeassignment.ui.app

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.andriod.maeassignment.R
import com.example.andriod.maeassignment.databinding.FragmentHomeBinding
import com.example.andriod.maeassignment.models.Recipe
import com.example.andriod.maeassignment.ui.app.recipe.RecipeActivity
import com.example.andriod.maeassignment.viewmodel.app.HomeViewModel


class HomeFragment : Fragment(), HomeAdapter.OnItemClickListener {
    private lateinit var recyclerView : RecyclerView
    private lateinit var aa : ArrayList<Recipe>
    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }
    private lateinit var binding: FragmentHomeBinding

    var adapter: HomeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         binding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater,
            R.layout.fragment_home, container, false
        )
        //load recipes
        viewModel.getRecipes()

        binding.txtSearch.setOnQueryTextListener( object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter?.filter?.filter(query)
                return true;
            }
            override fun onQueryTextChange(query: String?): Boolean {
                adapter?.filter?.filter(query)
                return true;
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //load data into recycler view
        viewModel.recipesData.observe(viewLifecycleOwner) { recipes ->
            adapter = context?.let { HomeAdapter(it, recipes, this) }
            recyclerView = view.findViewById(R.id.recipeRecyclerView)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter
        }
    }

    override fun onItemClick(recipeId: String) {
        //open recipe page
        val intent = Intent(activity, RecipeActivity::class.java)
        intent.putExtra("recipeId", recipeId)
        startActivity(intent)
        }

}

