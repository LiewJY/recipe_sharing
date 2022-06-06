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
import com.example.andriod.maeassignment.databinding.FragmentForgetPasswordBinding
import com.example.andriod.maeassignment.utils.Validation
import com.example.andriod.maeassignment.viewmodel.auth.ForgetPasswordViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_forget_password.*

class ForgetPasswordFragment : Fragment(), View.OnClickListener {

    private val viewModel: ForgetPasswordViewModel by lazy {
        ViewModelProvider(this).get(ForgetPasswordViewModel::class.java)
    }
    private lateinit var binding: FragmentForgetPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentForgetPasswordBinding>(
            inflater,
            R.layout.fragment_forget_password, container, false
        )
        //register event handler (for the button)
        binding.btnForgotPassword.setOnClickListener(this)
        binding.tvForgetPasswordLogin.setOnClickListener(this)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        validationListener()
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.tvForgetPasswordLogin -> {
                    v!!.findNavController().navigate(R.id.action_forgetPasswordFragment_to_loginFragment)
                }
                R.id.btnForgotPassword -> {
                    if (validationError() == true) {
                        viewModel.forgotPassword(
                            txtForgotPasswordEmail.text.toString()
                        )
                        viewModel.forgotPasswordStatus.observe(this) { result ->
                            if(result == "success") {
                                Toast.makeText(context, "Reset Success. Please check your email.", Toast.LENGTH_SHORT).show()

                            } else if (result == "failed") {
                                Toast.makeText(context, "Reset failed. Please try again.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun validationListener() {
        binding.txtForgotPasswordEmail.setOnFocusChangeListener{_, focused ->
            if (!focused)
            {
                binding.containerForgotPasswordEmail.helperText = Validation.emailValidation(binding.txtForgotPasswordEmail.text.toString())
            }
        }
    }
    private fun validationError(): Boolean {
        binding.containerForgotPasswordEmail.helperText = Validation.emailValidation(binding.txtForgotPasswordEmail.text.toString())
        if(binding.containerForgotPasswordEmail.helperText != null || binding.txtForgotPasswordEmail.text.toString() == null)
        {
            Snackbar.make(requireActivity().findViewById(android.R.id.content), "${binding.containerForgotPasswordEmail.helperText}", Snackbar.LENGTH_SHORT).show()
            Log.e("frag", "error ddd")
            return false
        }
        return true
    }



}