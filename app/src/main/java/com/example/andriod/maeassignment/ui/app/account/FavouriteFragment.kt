package com.example.andriod.maeassignment.ui.app.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.andriod.maeassignment.R
import com.example.andriod.maeassignment.databinding.FragmentFavouriteBinding

class FavouriteFragment : Fragment() {

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
        return binding.root
    }


}