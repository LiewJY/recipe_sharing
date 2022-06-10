package com.example.andriod.maeassignment.viewmodel.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.andriod.maeassignment.repository.AuthRepository

class LoginViewModel: ViewModel() {
    //data passing
    private var _loginUserLiveData = MutableLiveData<Boolean>()
    val loginStatus: MutableLiveData<Boolean>
        get() = _loginUserLiveData

    private var _isLoginLiveData = MutableLiveData<Boolean>()
    val isLogin: MutableLiveData<Boolean>
        get() = _isLoginLiveData

    fun login(email: String, password: String) {
        _loginUserLiveData = AuthRepository().login(email, password)
    }

    fun isLogin() {
        _isLoginLiveData= AuthRepository().isLogin()
    }
}