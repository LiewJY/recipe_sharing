package com.example.andriod.maeassignment.ui.app.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.andriod.maeassignment.R
import com.example.andriod.maeassignment.databinding.FragmentEditProfileBinding
import com.example.andriod.maeassignment.utils.Validation
import com.example.andriod.maeassignment.viewmodel.app.account.EditProfileViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_edit_profile.*

class EditProfileFragment : Fragment(), View.OnClickListener {
    private val viewModel: EditProfileViewModel by lazy {
        ViewModelProvider(this).get(EditProfileViewModel::class.java)
    }
    private lateinit var binding: FragmentEditProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentEditProfileBinding>(
            inflater,
            R.layout.fragment_edit_profile, container, false
        )
        binding.EditProfileToolbar.setOnClickListener{
            activity?.onBackPressed()
        }

        viewModel.getUserDetails()

        viewModel.userDetailsData.observe(viewLifecycleOwner) { userData ->
            binding.txtEditName.setText(userData.name)
            binding.txtEditPhoneNumber.setText(userData.mobile.toString())
        }

        binding.btnSave.setOnClickListener(this)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        validationListener()
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btnSave -> {
                    if (validationError() == true) {
                        viewModel.changeUserDetails(
                            txtEditName.text.toString(),
                            txtEditPhoneNumber.text.toString()
                        )
                        viewModel.changeUserDetailsStatus.observe(this) { result ->
                            if (result == 1) {
                                Toast.makeText(context, "User information updated", Toast.LENGTH_SHORT).show()
                            }else if (result == 2) {
                                Toast.makeText(context, "Failed to update user information", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun validationListener() {
        binding.txtEditName.setOnFocusChangeListener{_, focused ->
            if (!focused)
            {
                binding.containerEditName.helperText = Validation.nameValidation(binding.txtEditName.text.toString())
            }
        }
        binding.txtEditPhoneNumber.setOnFocusChangeListener{_, focused ->
            if (!focused)
            {
                binding.containerEditPhoneNumber.helperText = Validation.mobileNumberValidation(binding.txtEditPhoneNumber.text.toString())
            }
        }

    }
    private fun validationError(): Boolean {
        binding.containerEditName.helperText = Validation.nameValidation(binding.txtEditName.text.toString())
        binding.containerEditPhoneNumber.helperText = Validation.mobileNumberValidation(binding.txtEditPhoneNumber.text.toString())

        if(binding.containerEditName.helperText != null)
        {
            Snackbar.make(requireActivity().findViewById(android.R.id.content), "${binding.containerEditName.helperText}", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if(binding.containerEditPhoneNumber.helperText != null)
        {
            Snackbar.make(requireActivity().findViewById(android.R.id.content), "${binding.containerEditPhoneNumber.helperText}", Snackbar.LENGTH_SHORT).show()
            return false
        }

        return true
    }



}