package com.example.andriod.maeassignment.viewmodel.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.andriod.maeassignment.repository.AuthRepository


class RegisterViewModel: ViewModel() {
    //data passing
    private var _createdUserLiveData = MutableLiveData<Boolean>()
    val registerStatus: MutableLiveData<Boolean>
        get() = _createdUserLiveData

    fun register(name: String, email: String,password: String) {
       // Log.e("frag", "in view model$name  $email  $password")

        // TODO: add validation

//        if (name.isEmpty()) {
//            Log.e("frag", "no name")
//
//            return
//        }
        _createdUserLiveData = AuthRepository().register(name, email, password)
    }



}