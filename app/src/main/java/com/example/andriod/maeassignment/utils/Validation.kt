package com.example.andriod.maeassignment.utils

import android.util.Patterns

class Validation {
    companion object{
        const val noIngredient: String = "At least one ingredient is needed"
        const val emptyIngredient: String = "Please remove unused ingredient box"
        const val noMethod: String = "At least one method is needed"
        const val emptyMethod: String = "Please remove unused method box"

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
        fun passwordMatchValidation(password: String, passwordConfirm: String): String? {
            if(password == passwordConfirm) {
                return null
            }
            return "Password does not match"
        }

        fun nameValidation(name: String): String? {
            if(name.length > 2) {
                return null
            }
            return "Enter a valid name"
        }
        fun titleValidation(name: String): String? {
            if(name.length > 2) {
                return null
            }
            return "Title cannot be empty"
        }
        fun descriptionValidation(name: String): String? {
            if(name.length > 2) {
                return null
            }
            return "Description cannot be empty"
        }
        fun ingredientValidation(name: String): String? {
            if(name.length > 2) {
                return null
            }
            return "Ingredient cannot be empty. Remove if unused"
        }
        fun methodValidation(name: String): String? {
            if(name.length > 2) {
                return null
            }
            return "Method cannot be empty. Remove if unused"
        }
        fun mobileNumberValidation(mobile: String): String? {
            if(mobile.count() > 10) {
                return null
            }
            return "Enter valid mobile number"
        }


    }
}