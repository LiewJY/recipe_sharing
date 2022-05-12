package com.example.andriod.maeassignment.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.andriod.maeassignment.R
import com.example.andriod.maeassignment.databinding.FragmentLoginBinding
import com.example.andriod.maeassignment.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(), View.OnClickListener{

    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

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
                    viewModel.login(
                        txtLoginEmail.text.toString(),
                        txtLoginPassword.text.toString()
                    )

                    viewModel.loginStatus.observe(this) { result ->
                        if (result == true) {
                            //todo login suceess
                            Log.e("frag", "login end")

                            //open home page
                            v!!.findNavController().navigate(R.id.action_loginFragment_to_homeActivity)
                        } else {
                            //todo login false

                        }
                    }






                }

            }
        }




    }

}



