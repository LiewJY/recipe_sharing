package com.example.andriod.maeassignment.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth


class AuthRepository {

    //private lateinit var  auth: FirebaseAuth

    fun register(email: String, password: String) {
        //auth = FirebaseAuth.getInstance()

        Log.e("frag", "in repo $email  $password")


        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Log.e("frag", "success")
            }
            .addOnFailureListener {
                Log.e("frag", "failed")
            }


        //mFireStore.collection(Firebase.USERS)


    }

//    fun register(email: String, password: String) {
//        mFireStore.collection(Firebase.USERS)
//            .document(userInfo.id)
//            .set(userInfo, SetOptions.merge())
//            .addOnSuccessListener {
//
//                Log.e("frag", "registered")
//            }
//            .addOnFailureListener{
//                Log.e("frag", "failed")
//
//            }
//
//    }


}