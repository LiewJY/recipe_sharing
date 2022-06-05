package com.example.andriod.maeassignment.viewmodel.app.account

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.andriod.maeassignment.models.User
import com.example.andriod.maeassignment.repository.AuthRepository

class EditProfileViewModel: ViewModel() {
    //data passing
    private var _changeEmailLiveData = MutableLiveData<Boolean>()
    val changeEmailStatus: MutableLiveData<Boolean>
        get() = _changeEmailLiveData

    private var _changePasswordLiveData = MutableLiveData<Boolean>()
    val changePassowrdStatus: MutableLiveData<Boolean>
        get() = _changePasswordLiveData

    private var _userDetailsLiveData = MutableLiveData<User>()
    val userDetailsData: MutableLiveData<User>
        get() = _userDetailsLiveData

    private var _changeUserDetailsLiveData = MutableLiveData<Boolean>()
    val changeUserDetailsStatus: MutableLiveData<Boolean>
        get() = _changeUserDetailsLiveData


    fun changeEmail(newEmail : String) {
        _changeEmailLiveData = AuthRepository().changeEmail(newEmail)
    }

    fun changePassword(newPasword : String) {
        _changePasswordLiveData = AuthRepository().changePassword(newPasword)
    }

    fun getUserDetails() {
        _userDetailsLiveData = AuthRepository().getUserDetails()
    }
    fun changeUserDetails(name: String, mobile: Long) {
        _changeUserDetailsLiveData = AuthRepository().updateUserDetails(name, mobile)
    }


}