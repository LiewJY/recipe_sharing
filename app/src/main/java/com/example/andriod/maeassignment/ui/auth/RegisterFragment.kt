package com.example.andriod.maeassignment.ui.auth

import android.os.Bundle
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
import com.example.andriod.maeassignment.utils.Validation
import com.example.andriod.maeassignment.viewmodel.auth.RegisterViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_register.*



class RegisterFragment : Fragment(), View.OnClickListener {

    private val viewModel: RegisterViewModel by lazy {
        ViewModelProvider(this).get(RegisterViewModel::class.java)
    }
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentRegisterBinding>(inflater,
            R.layout.fragment_register,container,false)

        //register event handler (for the button)
        binding.tvRegisterLogin.setOnClickListener(this)
        binding.btnRegister.setOnClickListener(this)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        validationListener()
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id){
                R.id.tvRegisterLogin -> {
                    v!!.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }

                R.id.btnRegister -> {
                    if (validationError() == true) {
                        viewModel.register(
                            txtRegisterName.text.toString(),
                            txtRegisterEmail.text.toString(),
                            txtRegisterPassword.text.toString()
                        )
                        viewModel.registerStatus.observe(this) { result ->
                            if(result == true) {
                                Toast.makeText(context, "Register Success",Toast.LENGTH_SHORT).show()
                                v!!.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                            } else {
                                Toast.makeText(context, "Register Failed",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun validationListener() {
        binding.txtRegisterName.setOnFocusChangeListener{_, focused ->
            if (!focused)
            {
                binding.containerRegisterName.helperText = Validation.nameValidation(binding.txtRegisterName.text.toString())
            }
        }
        binding.txtRegisterEmail.setOnFocusChangeListener{_, focused ->
            if (!focused)
            {
                binding.containerRegisterEmail.helperText = Validation.emailValidation(binding.txtRegisterEmail.text.toString())
            }
        }
        binding.txtRegisterPassword.setOnFocusChangeListener{_, focused ->
            if (!focused)
            {
                binding.containerRegisterPassword.helperText = Validation.passwordValidation(binding.txtRegisterPassword.text.toString())
            }
        }
        binding.txtRegisterConfirmPassword.setOnFocusChangeListener{_, focused ->
            if (!focused)
            {
                binding.containerRegisterConfirmPassword.helperText = Validation.passwordMatchValidation(binding.txtRegisterPassword.text.toString(),binding.txtRegisterConfirmPassword.text.toString())
            }
        }
    }
    private fun validationError(): Boolean {
        binding.containerRegisterName.helperText = Validation.nameValidation(binding.txtRegisterName.text.toString())
        binding.containerRegisterEmail.helperText = Validation.emailValidation(binding.txtRegisterEmail.text.toString())
        binding.containerRegisterPassword.helperText = Validation.passwordValidation(binding.txtRegisterPassword.text.toString())
        binding.containerRegisterConfirmPassword.helperText = Validation.passwordMatchValidation(binding.txtRegisterPassword.text.toString(),binding.txtRegisterConfirmPassword.text.toString())

        if(binding.containerRegisterName.helperText != null)
        {
            Snackbar.make(requireActivity().findViewById(android.R.id.content), "${binding.containerRegisterName.helperText}", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if(binding.containerRegisterEmail.helperText != null)
        {
            Snackbar.make(requireActivity().findViewById(android.R.id.content), "${binding.containerRegisterEmail.helperText}", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if(binding.containerRegisterPassword.helperText != null)
        {
            Snackbar.make(requireActivity().findViewById(android.R.id.content), "${binding.containerRegisterPassword.helperText}", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if(binding.containerRegisterConfirmPassword.helperText != null)
        {
            Snackbar.make(requireActivity().findViewById(android.R.id.content), "${binding.containerRegisterConfirmPassword.helperText}", Snackbar.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}




