package com.example.andriod.maeassignment.viewmodel.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.andriod.maeassignment.repository.AuthRepository

class ForgetPasswordViewModel: ViewModel(){
    //data passing
    private var _forgotPasswordLiveData = MutableLiveData<Boolean>()
    val forgotPasswordStatus: MutableLiveData<Boolean>
        get() = _forgotPasswordLiveData

    fun forgotPassword(email: String) {
        //todo validation
        validation(email)
        _forgotPasswordLiveData = AuthRepository().forgotPassword(email)

    }

    private fun validation(email: String) {
        //todo validation
    }


}