package com.example.andriod.maeassignment.ui.app

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
import com.example.andriod.maeassignment.databinding.FragmentHomeBinding
import com.example.andriod.maeassignment.ui.app.recipe.RecipeActivity
import com.example.andriod.maeassignment.viewmodel.app.HomeViewModel



class HomeFragment : Fragment(), HomeAdapter.OnItemClickListener {
//    private lateinit var dbref : DatabaseReference
    private lateinit var recyclerView : RecyclerView
//    private lateinit var userArrayList : ArrayList<Recipe>

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater,
            R.layout.fragment_home, container, false
        )
        viewModel.getRecipes()

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val myDataset = Datasource().loadAffirmations()

        //load data into recycler view
        viewModel.recipesData.observe(viewLifecycleOwner) { recipes ->
            Log.e("frag", "SUCCESS frag get $recipes")

            recyclerView = view.findViewById(R.id.recipeRecyclerView)
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