package com.example.andriod.maeassignment.ui.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.andriod.maeassignment.R
import com.example.andriod.maeassignment.databinding.FragmentHomeBinding
import com.example.andriod.maeassignment.models.Recipe
import com.example.andriod.maeassignment.viewmodel.app.HomeViewModel
import com.google.firebase.database.DatabaseReference


class HomeFragment : Fragment() {
    private lateinit var dbref : DatabaseReference
    private lateinit var recyclerView : RecyclerView
    private lateinit var userArrayList : ArrayList<Recipe>

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val myDataset = Datasource().loadAffirmations()


        //load data into recycler view

        recyclerView = view.findViewById(R.id.recipeRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = HomeAdapter(viewModel.recipesData)


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




        //userArrayList = arrayListOf<Recipe>()
        //viewModel.getRecipes()

        //register event handler (for the button)
//        binding.btnAddImage.setOnClickListener(this)




        return binding.root
    }

}