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
import androidx.navigation.fragment.findNavController
import com.example.andriod.maeassignment.MainActivity
import com.example.andriod.maeassignment.R
import com.example.andriod.maeassignment.databinding.FragmentAccountBinding
import com.example.andriod.maeassignment.viewmodel.app.account.LogoutViewModel

class AccountFragment : Fragment(),  View.OnClickListener{

    private val viewModel: LogoutViewModel by lazy {
        ViewModelProvider(this).get(LogoutViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentAccountBinding>(inflater,
            R.layout.fragment_account,container,false)

        //register event handler (for the button)
        binding.btnLogout.setOnClickListener(this)
        binding.tvProfile.setOnClickListener(this)
        binding.tvMyPost.setOnClickListener(this)
        binding.tvFavourite.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btnLogout -> {
                    viewModel.logout()
                    viewModel.logoutStatus.observe(this) { result ->
                        if(result == true) {
                            Toast.makeText(context, "Logout Success", Toast.LENGTH_SHORT).show()
                        }
                    }
                    //go to main
                    activity?.finish()
                    val intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                }
                R.id.tvProfile -> {
//                    Toast.makeText(context, "Profile", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_navigation_account_to_navigation_profile)
                    //findNavController().navigate(R.id.action_accountFragment_to_profileFragment)

                }
                R.id.tvMyPost -> {
//                    Toast.makeText(context, "My Recipe", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_navigation_account_to_navigation_myRecipe)

                }
                R.id.tvFavourite -> {
//                    Toast.makeText(context, "Favourite", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_navigation_account_to_navigation_favourite)

                }


            }
        }
    }

}