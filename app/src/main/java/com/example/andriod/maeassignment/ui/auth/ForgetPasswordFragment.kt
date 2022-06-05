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
import com.example.andriod.maeassignment.databinding.FragmentForgetPasswordBinding
import com.example.andriod.maeassignment.viewmodel.auth.ForgetPasswordViewModel
import kotlinx.android.synthetic.main.fragment_forget_password.*

class ForgetPasswordFragment : Fragment(), View.OnClickListener {

    private val viewModel: ForgetPasswordViewModel by lazy {
        ViewModelProvider(this).get(ForgetPasswordViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentForgetPasswordBinding>(
            inflater,
            R.layout.fragment_forget_password, container, false
        )
        //register event handler (for the button)
        binding.btForgotPassword.setOnClickListener(this)
        binding.tvForgetPasswordLogin.setOnClickListener(this)



        return binding.root
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.tvForgetPasswordLogin -> {
                    v!!.findNavController().navigate(R.id.action_forgetPasswordFragment_to_loginFragment)
                }
                R.id.btForgotPassword -> {
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