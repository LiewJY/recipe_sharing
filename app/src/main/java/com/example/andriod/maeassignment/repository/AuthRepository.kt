package com.example.andriod.maeassignment.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.andriod.maeassignment.models.Recipe
import com.example.andriod.maeassignment.models.User
import com.example.andriod.maeassignment.utils.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.toObject


class AuthRepository {
    private val mFireStore = FirebaseFirestore.getInstance()

    private lateinit var  auth: FirebaseAuth
    private var currentFirebaseUser = FirebaseAuth.getInstance().currentUser

    //private  var itemMutableList: MutableLiveData<User> = MutableLiveData()
    //private var authenticatedUserMutableLiveData = MutableLiveData<Boolean>()
//
//    //passdata
//    private var userLiveData: MutableLiveData<FirebaseUser>? = null

//    private
//    val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
//         value = firebaseAuth.currentUser
//    }

    fun updateUserDetails(name: String, mobile: Long): MutableLiveData<Boolean> {
        val updateUserDetailsMutableLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
        Log.e("frag", "SUCCESS get")
        var listRecipe = ArrayList<Recipe>()
        listRecipe.clear()
        updateUserDetailsMutableLiveData.value = false
        auth = FirebaseAuth.getInstance()
        val user = User(
            name = name,
            mobile = mobile,
        )
        mFireStore.collection(Firebase.USERS).document(currentFirebaseUser!!.uid)
            .update("user", user.name,
            "mobile", user.mobile)
            .addOnSuccessListener {
//                mFireStore.collection(Firebase.RECIPES).whereEqualTo("userid", currentFirebaseUser!!.uid).get()
//                    .addOnSuccessListener { recipes ->
//                        val  userRecipe = recipes.toObjects<Recipe>()
//                        for (item in userRecipe) {
//                            listRecipe.add(item)
//                            Log.e("frag", "test get ${item}")
//                        }
//                        mFireStore.collection(Firebase.RECIPES).whereIn("id", listRecipe).get()
//                            .addOnSuccessListener { favourite ->
//                                if(favourite != null) {
//                                    val data = favourite.toObjects<Recipe>()
//                                    Log.e("frag", "SUCCESS get ${data.toList()}")
//                                    Log.e("frag", "SUCCESS get $data")
//                                }
//                            }
//                    }
//
//
//                    .update("name", user.name)
                updateUserDetailsMutableLiveData.value = true
            }
        return updateUserDetailsMutableLiveData
    }

    fun getUserDetails(): MutableLiveData<User> {
        val getUserDetailsMutableLiveData: MutableLiveData<User> = MutableLiveData<User>()
        Log.e("frag", "SUCCESS get")
        auth = FirebaseAuth.getInstance()
        mFireStore.collection(Firebase.USERS).document(currentFirebaseUser!!.uid).get()
            .addOnSuccessListener { user ->
                if(user != null) {
                    getUserDetailsMutableLiveData.value = user.toObject<User>()
                }
            }
        return getUserDetailsMutableLiveData
}


    fun changePassword(newPassword: String): MutableLiveData<Boolean> {
    val changePasswordMutableLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    changePasswordMutableLiveData.value = false
    auth = FirebaseAuth.getInstance()
    if (auth.currentUser != null) {
        Log.e("frag", " email change have user")
    }
    auth.currentUser!!.updatePassword(newPassword)?.addOnCompleteListener { changePassword ->
        if (changePassword.isSuccessful) {
            Log.e("frag", " email change SUCCESS")
            changePasswordMutableLiveData.value = true
        } else {
            Log.e("frag", "FAIL")
            changePasswordMutableLiveData.value = false

        }
    }.addOnFailureListener {
        Log.e("frag", "FAIL $it")

    }
    return changePasswordMutableLiveData
}


    fun changeEmail(newEmail: String): MutableLiveData<Boolean> {
        val changeEmailMutableLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
        changeEmailMutableLiveData.value = false
        auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            Log.e("frag", " email change have user")
        }
        auth.currentUser!!.updateEmail(newEmail)?.addOnCompleteListener { changeEmail ->
            if (changeEmail.isSuccessful) {
                Log.e("frag", " email change SUCCESS")
                mFireStore.collection(Firebase.USERS)
                    .document(auth.currentUser!!.uid)
                    .update("email", newEmail)
                    .addOnSuccessListener {
                        changeEmailMutableLiveData.value = true
                    }
            } else {
                Log.e("frag", "FAIL")
                changeEmailMutableLiveData.value = false

            }
        }.addOnFailureListener {
            Log.e("frag", "FAIL $it")

        }
        return changeEmailMutableLiveData


    }

    fun forgotPassword(email: String): MutableLiveData<String> {
        val forgotPasswordMutableLiveData: MutableLiveData<String> = MutableLiveData<String>()
        forgotPasswordMutableLiveData.value = ""
        auth = FirebaseAuth.getInstance()
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener{ sentEmail ->
                if (sentEmail.isSuccessful) {
                    forgotPasswordMutableLiveData.value = "success"
                    Log.e("frag", "sent email SUCCESS")
                } else {
                    Log.e("frag", "FAIL")
                    forgotPasswordMutableLiveData.value = "failed"
                }
            }
        return forgotPasswordMutableLiveData
    }

    fun isLogin(): MutableLiveData<Boolean> {
        val isLoginMutableLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
        auth = FirebaseAuth.getInstance()
        isLoginMutableLiveData.value = false
        if (currentFirebaseUser != null) {isLoginMutableLiveData.value = true}
        return isLoginMutableLiveData
    }

    fun logout(): MutableLiveData<Boolean> {
        val logoutMutableLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
        auth = FirebaseAuth.getInstance()
        auth.signOut()
        if (currentFirebaseUser == null) {logoutMutableLiveData.value = true}

        return logoutMutableLiveData
    }

    fun login(email: String, password: String): MutableLiveData<Boolean> {
        val loginMutableLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
        auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { authLogin ->
                if (authLogin.isSuccessful) {
                    loginMutableLiveData.value = true
                    Log.e("frag", "login SUCCESS")
                } else {
                    Log.e("frag", "FAIL")
                    loginMutableLiveData.value = false
                }
            }
        return loginMutableLiveData
    }


    fun register(name: String ,email: String, password: String): MutableLiveData<Boolean> {
        auth = FirebaseAuth.getInstance()
          //pass data
          val newUserMutableLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

        //Log.e("frag", "in repo $name   $email  $password")

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { authRegister ->
                if (authRegister.isSuccessful) {
                    // Firebase registered user
                    val firebaseUser: FirebaseUser = authRegister.result!!.user!!
                    val user = User(
                        firebaseUser.uid,
                        name,
                        email
                    )
                    //add additional information into database
                    mFireStore.collection(Firebase.USERS)
                        .document(user.id)
                        .set(user, SetOptions.merge())
                        .addOnCompleteListener { addDetails ->
                            if (addDetails.isSuccessful) {
                                //pass data
                                newUserMutableLiveData.value = true
                                Log.e("frag", "SUCCESS")
                            }
                        }

                } else {
                    Log.e("frag", "FAIL")
                    //pass data
                    newUserMutableLiveData.value = false
                    //Toast.makeText(applicationContext, "Fail to register", Toast.LENGTH_SHORT).show()
                }
            }
        return newUserMutableLiveData
    }




}