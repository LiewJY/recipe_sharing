package com.example.andriod.maeassignment.viewmodel.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.andriod.maeassignment.repository.AuthRepository

class ForgetPasswordViewModel: ViewModel(){
    //data passing
    private var _forgotPasswordLiveData = MutableLiveData<Int>()
    val forgotPasswordStatus: MutableLiveData<Int>
        get() = _forgotPasswordLiveData

    fun forgotPassword(email: String) {
        _forgotPasswordLiveData = AuthRepository().forgotPassword(email)

    }
}