package com.example.andriod.maeassignment.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.andriod.maeassignment.repository.AuthRepository

class RegisterViewModel: ViewModel() {

    var authRepository = AuthRepository()


    public fun register(email: String, password: String) {

        Log.e("frag", "in view model$email  $password")

        // TODO: add validation 

        authRepository.register(email, password)
    }

}