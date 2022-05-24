package com.example.andriod.maeassignment.ui.app

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.andriod.maeassignment.R
import com.example.andriod.maeassignment.databinding.FragmentMyRecipeBinding
import com.example.andriod.maeassignment.viewmodel.MyRecipeViewModel


class MyRecipeFragment : Fragment(), MyRecipeAdapter.OnItemClickListener {

    private lateinit var recyclerView : RecyclerView

    private val viewModel: MyRecipeViewModel by lazy {
        ViewModelProvider(this).get(MyRecipeViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val myDataset = Datasource().loadAffirmations()

        //load data into recycler view
        viewModel.myrecipesData.observe(viewLifecycleOwner) { recipes ->
            Log.e("frag", "SUCCESS frag get $recipes")

            recyclerView = view.findViewById(R.id.myRecipeRecyclerView)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = context?.let { MyRecipeAdapter(it, recipes, this) }
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


        return binding.root

    }

    override fun onDeleteClick(recipeId: String) {
        //delete the recipe
        viewModel.deleteRecipe(recipeId)
        viewModel.deleteRecipeStatus.observe(this) {result ->
            if(result == true){
                Log.e("frag", "delete success frag")
            }else
            {

            }
        }
    }

}