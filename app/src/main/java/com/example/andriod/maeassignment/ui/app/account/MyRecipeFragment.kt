package com.example.andriod.maeassignment.ui.app.account

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.andriod.maeassignment.R
import com.example.andriod.maeassignment.databinding.FragmentMyRecipeBinding
import com.example.andriod.maeassignment.viewmodel.app.account.MyRecipeViewModel


class MyRecipeFragment : Fragment(), MyRecipeAdapter.OnItemClickListener {

    private lateinit var recyclerView : RecyclerView

    private val viewModel: MyRecipeViewModel by lazy {
        ViewModelProvider(this).get(MyRecipeViewModel::class.java)
    }

//    private val editViewModel: EditRecipeViewModel by lazy {
//        ViewModelProvider(this).get(EditRecipeViewModel::class.java)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //load data into recycler view
        viewModel.myrecipesData.observe(viewLifecycleOwner) { recipes ->
            Log.e("frag", "SUCCESS frag get $recipes")

            recyclerView = view.findViewById(R.id.myRecipeRecyclerView)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = context?.let { MyRecipeAdapter(it, recipes, this) }

            if(recipes.isEmpty()) {
                Toast.makeText(context, "You have no published recipe", Toast.LENGTH_SHORT).show()
            }
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentMyRecipeBinding>(
            inflater,
            R.layout.fragment_my_recipe, container, false
        )
        viewModel.getRecipeByAuthor()

        binding.myRecipeToolbar.setOnClickListener{
            activity?.onBackPressed()
        }
        return binding.root

    }

    override fun onDeleteClick(recipeId: String) {
        //delete the recipe
        viewModel.deleteRecipe(recipeId)
        viewModel.deleteRecipeStatus.observe(this) {result ->
            if (result == 1) {
                refresh()
                Toast.makeText(context, "Recipe deleted", Toast.LENGTH_SHORT).show()
            }else if (result == 2) {
                Toast.makeText(context, "Recipe failed to delete", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun refresh() {

        viewModel.getRecipeByAuthor()
        //load data into recycler view
        viewModel.myrecipesData.observe(viewLifecycleOwner) { recipes ->
            recyclerView = requireView().findViewById(R.id.myRecipeRecyclerView)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = context?.let { MyRecipeAdapter(it, recipes, this) }
            if(recipes.isEmpty()) {
                Toast.makeText(context, "You have no published recipe", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onEditClick(recipeId: String) {
        //edit the recipe send data then go to edit page
        val bundle = bundleOf("id" to recipeId)
        findNavController().navigate(R.id.action_navigation_myRecipe_to_navigation_editRecipe, bundle)

    }

}