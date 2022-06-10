package com.example.andriod.maeassignment.ui.app.account

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.andriod.maeassignment.R
import com.example.andriod.maeassignment.databinding.FragmentProfileBinding
import com.example.andriod.maeassignment.utils.Validation
import com.example.andriod.maeassignment.viewmodel.app.account.EditProfileViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout


class ProfileFragment : Fragment(), View.OnClickListener {
    private val viewModel: EditProfileViewModel by lazy {
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
                    val dialogLayout=layoutInflater.inflate(R.layout.dialog_reauthenticate,null)
                    val email = dialogLayout.findViewById<EditText>(R.id.txtReAuthEmail)
                    val emailContainer = dialogLayout.findViewById<TextInputLayout>(R.id.containerReAuthEmail)
                    val password = dialogLayout.findViewById<EditText>(R.id.txtReAuthPassword)
                    val passwordContainer = dialogLayout.findViewById<TextInputLayout>(R.id.containerReAuthPassword)
                    dialog?.setContentView(dialogLayout)
                    dialog?.show()
                    //validation
                    email.setOnFocusChangeListener{_, focused ->
                        if (!focused)
                        {
                            emailContainer.helperText = Validation.emailValidation(email.text.toString())
                        }
                    }
                    password.setOnFocusChangeListener{_, focused ->
                        if (!focused)
                        {
                            passwordContainer.helperText = Validation.passwordValidation(password.text.toString())
                        }
                    }
                    dialogLayout.findViewById<Button>(R.id.btnReAuth).setOnClickListener {
                        emailContainer.helperText = Validation.emailValidation(email.text.toString())
                        passwordContainer.helperText = Validation.passwordValidation(password.text.toString())

                        var passValidation = true
                        if(emailContainer.helperText != null)
                        {
                            passValidation = false
                            Snackbar.make(requireActivity().findViewById(android.R.id.content), "${emailContainer.helperText}", Snackbar.LENGTH_SHORT).show()
                        }
                        if(passwordContainer.helperText != null)
                        {
                            passValidation = false
                            Snackbar.make(requireActivity().findViewById(android.R.id.content), "${passwordContainer.helperText}", Snackbar.LENGTH_SHORT).show()
                        }

                        if(passValidation == true) {
                            val emailString = email.text.toString()
                            val passwordstring = password.text.toString()
                            viewModel.reAuthUser(emailString, passwordstring)
                            viewModel.reAuthUserStatus.observe(this) { result ->
                                if(result == 1) {
                                    Toast.makeText(context, "Authentication success", Toast.LENGTH_SHORT).show()
                                    resetEmail()
                                    dialog?.dismiss()
                                } else if (result == 2){
                                    Toast.makeText(context, "Failed to authenticate. Please try again later", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                    dialogLayout.findViewById<Button>(R.id.btnReAuthClose).setOnClickListener {
                        dialog?.dismiss()
                    }

                }
                R.id.btnChangePassword -> {
                    val dialog = context?.let { BottomSheetDialog(it) }
                    val dialogLayout=layoutInflater.inflate(R.layout.dialog_reauthenticate,null)
                    val email = dialogLayout.findViewById<EditText>(R.id.txtReAuthEmail)
                    val emailContainer = dialogLayout.findViewById<TextInputLayout>(R.id.containerReAuthEmail)
                    val password = dialogLayout.findViewById<EditText>(R.id.txtReAuthPassword)
                    val passwordContainer = dialogLayout.findViewById<TextInputLayout>(R.id.containerReAuthPassword)
                    dialog?.setContentView(dialogLayout)
                    dialog?.show()
                    //validation
                    email.setOnFocusChangeListener{_, focused ->
                        if (!focused)
                        {
                            emailContainer.helperText = Validation.emailValidation(email.text.toString())
                        }
                    }
                    password.setOnFocusChangeListener{_, focused ->
                        if (!focused)
                        {
                            passwordContainer.helperText = Validation.passwordValidation(password.text.toString())
                        }
                    }
                    dialogLayout.findViewById<Button>(R.id.btnReAuth).setOnClickListener {
                        emailContainer.helperText = Validation.emailValidation(email.text.toString())
                        passwordContainer.helperText = Validation.passwordValidation(password.text.toString())

                        var passValidation = true
                        if(emailContainer.helperText != null)
                        {
                            passValidation = false
                            Snackbar.make(requireActivity().findViewById(android.R.id.content), "${emailContainer.helperText}", Snackbar.LENGTH_SHORT).show()
                        }
                        if(passwordContainer.helperText != null)
                        {
                            passValidation = false
                            Snackbar.make(requireActivity().findViewById(android.R.id.content), "${passwordContainer.helperText}", Snackbar.LENGTH_SHORT).show()
                        }

                        if(passValidation == true) {
                            val emailString = email.text.toString()
                            val passwordstring = password.text.toString()
                            viewModel.reAuthUser(emailString, passwordstring)
                            viewModel.reAuthUserStatus.observe(this) { result ->
                                if(result == 1) {
                                    Toast.makeText(context, "Authentication success", Toast.LENGTH_SHORT).show()
                                    resetPassword()
                                    dialog?.dismiss()
                                } else if (result == 2){
                                    Toast.makeText(context, "Failed to authenticate. Please try again later", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                    dialogLayout.findViewById<Button>(R.id.btnReAuthClose).setOnClickListener {
                        dialog?.dismiss()
                    }
                }
                R.id.btnEditProfile -> {
                    findNavController().navigate(R.id.action_navigation_profile_to_navigaton_edit)

                }
            }
        }
    }

    fun resetEmail() {
        Log.e("frag", "show email")
        val dialog = context?.let { BottomSheetDialog(it) }
        val dialogLayout=layoutInflater.inflate(R.layout.dialog_change_email,null)
        val newEmail = dialogLayout.findViewById<EditText>(R.id.txtEditEmail)
        val newEmailContainer = dialogLayout.findViewById<TextInputLayout>(R.id.containerEditEmail)
        dialog?.setContentView(dialogLayout)
        dialog?.show()

        dialogLayout.findViewById<Button>(R.id.btnEditEmailClose).setOnClickListener {
            dialog?.dismiss()
        }
        //validation
        newEmail.setOnFocusChangeListener{_, focused ->
            if (!focused)
            {
                newEmailContainer.helperText = Validation.emailValidation(newEmail.text.toString())
            }
        }

        dialogLayout.findViewById<Button>(R.id.btnEditEmail).setOnClickListener {
            newEmailContainer.helperText = Validation.emailValidation(newEmail.text.toString())
            var passValidation = true
            if(newEmailContainer.helperText != null)
            {
                passValidation = false
                Snackbar.make(requireActivity().findViewById(android.R.id.content), "${newEmailContainer.helperText}", Snackbar.LENGTH_SHORT).show()
            }
            if(passValidation == true) {
                val newEmailString = newEmail.text.toString()
                viewModel.changeEmail(newEmailString)
                viewModel.changeEmailStatus.observe(this) { result ->
                    if(result == 1) {
                        Toast.makeText(context, "Changed email", Toast.LENGTH_SHORT).show()
                    } else if (result == 2){
                        Toast.makeText(context, "Failed to change email", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    fun resetPassword() {
        val dialog = context?.let { BottomSheetDialog(it) }
        val dialogLayout=layoutInflater.inflate(R.layout.dialog_change_password,null)
        val newPassword = dialogLayout.findViewById<EditText>(R.id.txtEditPassword)
        val newPasswordConfirm = dialogLayout.findViewById<EditText>(R.id.txtEditPasswordConfirm)
        val newPasswordContainer = dialogLayout.findViewById<TextInputLayout>(R.id.containerEditPassword)
        val newPasswordConfirmContainer = dialogLayout.findViewById<TextInputLayout>(R.id.containerEditPasswordConfirm)
        dialog?.setContentView(dialogLayout)
        dialog?.show()

        dialogLayout.findViewById<Button>(R.id.btnEditPasswordClose).setOnClickListener {
            dialog?.dismiss()
        }
        //validation
        newPassword.setOnFocusChangeListener{_, focused ->
            if (!focused)
            {
                newPasswordContainer.helperText = Validation.passwordValidation(newPassword.text.toString())
            }
        }
        newPasswordConfirm.setOnFocusChangeListener{_, focused ->
            if (!focused)
            {
                newPasswordConfirmContainer.helperText = Validation.passwordMatchValidation(newPassword.text.toString(),newPasswordConfirm.text.toString())
            }
        }

        dialogLayout.findViewById<Button>(R.id.btnEditPassword).setOnClickListener {
            newPasswordContainer.helperText = Validation.passwordValidation(newPassword.text.toString())
            newPasswordConfirmContainer.helperText = Validation.passwordMatchValidation(newPassword.text.toString(),newPasswordConfirm.text.toString())
            var passValidation = true
            if(newPasswordContainer.helperText != null)
            {
                passValidation = false
                Snackbar.make(requireActivity().findViewById(android.R.id.content), "${newPasswordContainer.helperText}", Snackbar.LENGTH_SHORT).show()
            }
            if(newPasswordConfirmContainer.helperText != null)
            {
                passValidation = false
                Snackbar.make(requireActivity().findViewById(android.R.id.content), "${newPasswordConfirmContainer.helperText}", Snackbar.LENGTH_SHORT).show()
            }
            if(passValidation == true) {
                val newPasswordString = newPassword.text.toString()
                viewModel.changePassword(newPasswordString)
                viewModel.changePassowrdStatus.observe(this) { result ->
                    if(result == 1) {
                        Toast.makeText(context, "Change password", Toast.LENGTH_SHORT).show()
                    } else if (result == 2){
                        Toast.makeText(context, "Failed to change password", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

//    fun reauth(): Boolean{
//        val dialog = context?.let { BottomSheetDialog(it) }
//        val dialogLayout=layoutInflater.inflate(R.layout.dialog_reauthenticate,null)
//        val email = dialogLayout.findViewById<EditText>(R.id.txtReAuthEmail)
//        val emailContainer = dialogLayout.findViewById<TextInputLayout>(R.id.containerReAuthEmail)
//        val password = dialogLayout.findViewById<EditText>(R.id.txtReAuthPassword)
//        val passwordContainer = dialogLayout.findViewById<TextInputLayout>(R.id.containerReAuthPassword)
//        dialog?.setContentView(dialogLayout)
//        dialog?.show()
//        //validation
//        email.setOnFocusChangeListener{_, focused ->
//            if (!focused)
//            {
//                emailContainer.helperText = Validation.emailValidation(email.text.toString())
//            }
//        }
//        password.setOnFocusChangeListener{_, focused ->
//            if (!focused)
//            {
//                passwordContainer.helperText = Validation.passwordValidation(password.text.toString())
//            }
//        }
//        dialogLayout.findViewById<Button>(R.id.btnReAuth).setOnClickListener {
//            emailContainer.helperText = Validation.emailValidation(email.text.toString())
//            passwordContainer.helperText = Validation.passwordValidation(password.text.toString())
//
//            var passValidation = true
//            if(emailContainer.helperText != null)
//            {
//                passValidation = false
//                Snackbar.make(requireActivity().findViewById(android.R.id.content), "${emailContainer.helperText}", Snackbar.LENGTH_SHORT).show()
//            }
//            if(passwordContainer.helperText != null)
//            {
//                passValidation = false
//                Snackbar.make(requireActivity().findViewById(android.R.id.content), "${passwordContainer.helperText}", Snackbar.LENGTH_SHORT).show()
//            }
//
//            if(passValidation == true) {
//                val emailString = email.text.toString()
//                val passwordstring = password.text.toString()
//                viewModel.reAuthUser(emailString, passwordstring)
//                viewModel.reAuthUserStatus.observe(this) { result ->
//                    if(result == 1) {
//                        Toast.makeText(context, "Authentication success", Toast.LENGTH_SHORT).show()
//                        passAuth = true
//
//                    } else if (result == 2){
//                        Toast.makeText(context, "Failed to authenticate. Please try again later", Toast.LENGTH_SHORT).show()
//                        passAuth = false
//                    }
//                }
//            }
//        }
//        dialogLayout.findViewById<Button>(R.id.btnReAuthClose).setOnClickListener {
//            dialog?.dismiss()
//        }
//    }
}