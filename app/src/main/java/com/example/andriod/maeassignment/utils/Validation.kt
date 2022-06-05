package com.example.andriod.maeassignment.utils

import android.util.Patterns

class Validation {
    companion object{
        fun emailValidation(email : String): String? {
            if(Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                return null
            }
            return "Invalid Email Address"
        }
        fun passwordValidation(password: String): String? {
            if(password.length > 8) {
                return null
            }
            return "Password should be minimum of 8 character"
        }

    }
}