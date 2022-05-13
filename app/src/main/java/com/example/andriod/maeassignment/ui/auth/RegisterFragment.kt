package com.example.andriod.maeassignment.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.andriod.maeassignment.R
import com.example.andriod.maeassignment.databinding.FragmentRegisterBinding
import com.example.andriod.maeassignment.viewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.fragment_register.*



class RegisterFragment : Fragment(), View.OnClickListener {

    private val viewModel: RegisterViewModel by lazy {
        ViewModelProvider(this).get(RegisterViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Log.e("frag", "open")
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentRegisterBinding>(inflater,
            R.layout.fragment_register,container,false)

        //register event handler (for the button)
        binding.tvRegisterLogin.setOnClickListener(this)
        binding.btnRegister.setOnClickListener(this)

        // Giving the binding access to the OverviewViewModel
        //binding.viewModel = viewModel
        //binding.containerRegisterName.setOnClickListener(this)


        return binding.root
    }
    override fun onClick(v: View?) {
        Log.e("frag", "clicked")

        if (v != null) {
            when(v.id){
                R.id.tvRegisterLogin -> {
                    Log.e("frag", "reg")
                    v!!.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }

                R.id.btnRegister -> {
                    Log.e("frag", "register")
                    viewModel.register(
                        txtRegisterName.text.toString(),
                        txtRegisterEmail.text.toString(),
                        txtRegisterPassword.text.toString()
                    )
                    //validation code
                    //containerRegisterName.error = "dddd"
                    viewModel.registerStatus.observe(this) { result ->
                        if(result == true) {
                            Log.e("frag", "in frag return $result")
                            Toast.makeText(context, "Register Success",Toast.LENGTH_SHORT)
                            // todo show success message
                            v!!.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                        } else {
                            Log.e("frag", "in frag return $result")
                            Toast.makeText(context, "Register Failed",Toast.LENGTH_SHORT).show()
                        }


                    }
                }
            }
        }
    }

}




