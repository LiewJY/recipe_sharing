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
import com.example.andriod.maeassignment.viewmodel.app.account.EditProfileViewModel
import kotlinx.android.synthetic.main.fragment_edit_profile.*

class EditProfileFragment : Fragment(), View.OnClickListener {
    private val viewModel: EditProfileViewModel by lazy {
        ViewModelProvider(this).get(EditProfileViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentEditProfileBinding>(
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

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btnSave -> {
                    viewModel.changeUserDetails(
                        txtEditName.text.toString(),
                        txtEditPhoneNumber.text.toString().toLong()
                    )

                    viewModel.changeUserDetailsStatus.observe(this) { result ->
                        if (result == true) {
                            Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()
                        }else {
                            //Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

}