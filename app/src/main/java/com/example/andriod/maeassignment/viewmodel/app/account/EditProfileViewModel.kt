package com.example.andriod.maeassignment.viewmodel.app.account

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.andriod.maeassignment.models.User
import com.example.andriod.maeassignment.repository.AuthRepository

class EditProfileViewModel: ViewModel() {
    //data passing
    private var _changeEmailLiveData = MutableLiveData<Int>()
    val changeEmailStatus: MutableLiveData<Int>
        get() = _changeEmailLiveData

    private var _changePasswordLiveData = MutableLiveData<Int>()
    val changePassowrdStatus: MutableLiveData<Int>
        get() = _changePasswordLiveData

    private var _userDetailsLiveData = MutableLiveData<User>()
    val userDetailsData: MutableLiveData<User>
        get() = _userDetailsLiveData

    private var _changeUserDetailsLiveData = MutableLiveData<Int>()
    val changeUserDetailsStatus: MutableLiveData<Int>
        get() = _changeUserDetailsLiveData

    private var _reAuthUserLiveData = MutableLiveData<Int>()
    val reAuthUserStatus: MutableLiveData<Int>
        get() = _reAuthUserLiveData


    fun changeEmail(newEmail : String) {
        _changeEmailLiveData = AuthRepository().changeEmail(newEmail)
    }

    fun changePassword(newPasword : String) {
        _changePasswordLiveData = AuthRepository().changePassword(newPasword)
    }

    fun getUserDetails() {
        _userDetailsLiveData = AuthRepository().getUserDetails()
    }
    fun changeUserDetails(name: String, mobile: String) {
        _changeUserDetailsLiveData = AuthRepository().updateUserDetails(name, mobile)
    }

    fun reAuthUser(email: String, password: String) {
        _reAuthUserLiveData = AuthRepository().reAuth(email, password)
    }


}