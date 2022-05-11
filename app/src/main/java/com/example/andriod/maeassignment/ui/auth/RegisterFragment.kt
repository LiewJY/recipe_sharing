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
import com.example.andriod.maeassignment.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment(), View.OnClickListener {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.e("frag", "open")
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentRegisterBinding>(inflater,
            R.layout.fragment_register,container,false)

        //register event handler (for the button)
        binding.tvRegisterLogin.setOnClickListener(this)


        return binding.root
    }

    override fun onClick(v: View?) {
        v!!.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
    }

}