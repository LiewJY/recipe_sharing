package com.example.andriod.maeassignment.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.andriod.maeassignment.R
import com.example.andriod.maeassignment.databinding.FragmentHomeBinding
import com.example.andriod.maeassignment.viewmodel.LogoutViewModel

class HomeFragment : Fragment(), View.OnClickListener {

    private val viewModel: LogoutViewModel by lazy {
        ViewModelProvider(this).get(LogoutViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater,
            R.layout.fragment_home,container,false)

        //register event handler (for the button)
        binding.btnLogout.setOnClickListener(this)
        return binding.root

    }

    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id) {
                R.id.btnLogout -> {
                    viewModel.logout()
                    Log.e("frag", "logged out")

                }
            }

        }
    }


}