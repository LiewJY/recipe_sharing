package com.example.andriod.maeassignment.viewmodel.app.account

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.andriod.maeassignment.repository.AuthRepository

class EditProfileViewModel: ViewModel() {
    //data passing
    private var _changeEmailLiveData = MutableLiveData<Boolean>()
    val changeEmailStatus: MutableLiveData<Boolean>
        get() = _changeEmailLiveData

    fun changeEmail(newEmail : String) {
        _changeEmailLiveData = AuthRepository().changeEmail(newEmail)
    }

    fun changePassword(newPasword : String) {
        //_changeEmailLiveData = AuthRepository().changeEmail(newEmail)
    }


}