package com.example.andriod.maeassignment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.andriod.maeassignment.repository.AuthRepository

class LogoutViewModel: ViewModel() {

    //data passing
    private var _logoutUserLiveData = MutableLiveData<Boolean>()
    val logoutStatus: MutableLiveData<Boolean>
        get() = _logoutUserLiveData

    fun logout() {
        _logoutUserLiveData = AuthRepository().logout()
    }
}