package com.example.andriod.maeassignment.ui.app.account

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.andriod.maeassignment.R
import com.example.andriod.maeassignment.databinding.FragmentProfileBinding
import com.example.andriod.maeassignment.viewmodel.app.account.EditProfileViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog


class ProfileFragment : Fragment(), View.OnClickListener {
    private val recipeViewModel: EditProfileViewModel by lazy {
        ViewModelProvider(this).get(EditProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentProfileBinding>(
            inflater,
            R.layout.fragment_profile, container, false
        )

        binding.profileToolbar.setOnClickListener{
            activity?.onBackPressed()
        }

        binding.btnChangeEmail.setOnClickListener(this)
        binding.btnChangePassword.setOnClickListener(this)
        binding.btnEditProfile.setOnClickListener(this)


        return binding.root
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btnChangeEmail -> {
                    val dialog = context?.let { BottomSheetDialog(it) }
                    val dialogLayout=layoutInflater.inflate(R.layout.dialog_change_email,null)
                    dialog?.setContentView(dialogLayout)
                    dialog?.show()

                    dialogLayout.findViewById<Button>(R.id.btnEditEmailClose).setOnClickListener {
                        dialog?.dismiss()
                    }
                    dialogLayout.findViewById<Button>(R.id.btnEditEmail).setOnClickListener {
                        Log.e("frag" , "change")
                        val newEmail = dialogLayout.findViewById<EditText>(R.id.txtEditEmail).text.toString()
                        recipeViewModel.changeEmail(newEmail)
                        Log.e("frag" , "change to $newEmail")
                    }


                }
                R.id.btnChangePassword -> {
                    val dialog = context?.let { BottomSheetDialog(it) }
                    val dialogLayout=layoutInflater.inflate(R.layout.dialog_change_password,null)
                    dialog?.setContentView(dialogLayout)
                    dialog?.show()

                    dialogLayout.findViewById<Button>(R.id.btnEditPasswordClose).setOnClickListener {
                        dialog?.dismiss()
                    }
                    dialogLayout.findViewById<Button>(R.id.btnEditPassword).setOnClickListener {
                        Log.e("frag" , "change")
                        val newPassword = dialogLayout.findViewById<EditText>(R.id.txtEditPassword).text.toString()
                        recipeViewModel.changePassword(newPassword)
                        Log.e("frag" , "change to $newPassword")
                    }

                }
                R.id.btnEditProfile -> {
                    findNavController().navigate(R.id.action_navigation_profile_to_navigaton_edit)

                }
            }
        }
    }
}