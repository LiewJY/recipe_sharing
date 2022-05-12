package com.example.andriod.maeassignment.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.andriod.maeassignment.R
import com.example.andriod.maeassignment.databinding.FragmentLoginBinding

class LoginFragment : Fragment(), View.OnClickListener{

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(inflater,
            R.layout.fragment_login,container,false)

        //register event handler (for the button)
        binding.btnSignIn.setOnClickListener(this)
        binding.tvRegister.setOnClickListener(this)


        return binding.root
    }

    override fun onClick(v: View?) {
        Log.e("frag", "clicked")

        if (v != null) {
            when(v.id){
                R.id.tvRegister -> {
                    Log.e("frag", "reg")
                    v!!.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
                }

                R.id.btnSignIn -> {
                    Log.e("frag", "login")

                }

            }
        }




    }

}