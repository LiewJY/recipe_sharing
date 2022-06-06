package com.example.andriod.maeassignment.ui.auth

import android.content.Intent
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
import com.example.andriod.maeassignment.databinding.FragmentLoginBinding
import com.example.andriod.maeassignment.ui.app.AppActivity
import com.example.andriod.maeassignment.utils.Validation
import com.example.andriod.maeassignment.viewmodel.auth.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment(), View.OnClickListener {

    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentLoginBinding>(inflater,
            R.layout.fragment_login,container,false)

        //register event handler (for the button)
        binding.btnSignIn.setOnClickListener(this)
        binding.tvRegister.setOnClickListener(this)
        binding.tvForgetPassword.setOnClickListener(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        validationListener()
    }


    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id){
                R.id.tvForgetPassword -> {
                    v!!.findNavController().navigate(R.id.action_loginFragment_to_forgetPasswordFragment)
                }
                R.id.tvRegister -> {
                    v!!.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
                }
                R.id.btnSignIn -> {
                    if (validationError() == true) {
                        loginViewModel.login(
                            txtLoginEmail.text.toString(),
                            txtLoginPassword.text.toString()
                        )
                        loginViewModel.loginStatus.observe(this) { result ->
                            if (result == true) {
                                Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show()
                                //open home page
                                activity?.finish()
                                val intent = Intent(activity, AppActivity::class.java)
                                startActivity(intent)

                            } else {
                                Toast.makeText(context, "Login Failed. Incorrect email or password.", Toast.LENGTH_SHORT).show()
                            }
                        }

                    }
                }

            }
        }




    }

    private fun validationListener() {
        binding.txtLoginEmail.setOnFocusChangeListener{_, focused ->
            if (!focused)
            {
                binding.containerLoginEmail.helperText = Validation.emailValidation(binding.txtLoginEmail.text.toString())
            }
        }
        binding.txtLoginPassword.setOnFocusChangeListener{_, focused ->
            if (!focused)
            {
                binding.containerLoginPassword.helperText = Validation.passwordValidation(binding.txtLoginPassword.text.toString())
            }
        }

    }
    private fun validationError(): Boolean {
        binding.containerLoginEmail.helperText = Validation.emailValidation(binding.txtLoginEmail.text.toString())
        binding.containerLoginPassword.helperText = Validation.passwordValidation(binding.txtLoginPassword.text.toString())

        if(binding.containerLoginEmail.helperText != null || binding.txtLoginEmail.text.toString() == null)
        {
            Snackbar.make(requireActivity().findViewById(android.R.id.content), "${binding.containerLoginEmail.helperText}", Snackbar.LENGTH_SHORT).show()
            Log.e("frag", "error ddd")

            return false
        }
        if(binding.containerLoginPassword.helperText != null || binding.txtLoginPassword.text.toString() == null)
        {
            Snackbar.make(requireActivity().findViewById(android.R.id.content), "${binding.containerLoginPassword.helperText}", Snackbar.LENGTH_SHORT).show()
            Log.e("frag", "error pwd")

            return false
        }
        return true
    }


}



