package com.example.andriod.maeassignment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.andriod.maeassignment.repository.AuthRepository

class LoginViewModel: ViewModel() {
    //data passing
    private var _loginUserLiveData = MutableLiveData<Boolean>()
    val loginStatus: MutableLiveData<Boolean>
        get() = _loginUserLiveData


    fun login(email: String, password: String) {
        // todo add validation

        _loginUserLiveData = AuthRepository().login(email, password)

    }


//    val authenticationState = FirebaseUserLiveData().map { user ->
//        if (user != null) {
//            AuthenticationState.AUTHENTICATED
//        } else {
//            AuthenticationState.UNAUTHENTICATED
//        }
//    }



}