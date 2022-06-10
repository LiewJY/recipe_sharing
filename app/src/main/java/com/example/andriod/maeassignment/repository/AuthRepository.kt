package com.example.andriod.maeassignment.repository

import androidx.lifecycle.MutableLiveData
import com.example.andriod.maeassignment.models.Recipe
import com.example.andriod.maeassignment.models.User
import com.example.andriod.maeassignment.utils.FirebaseVal
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.toObject


class AuthRepository {
    private val mFireStore = FirebaseFirestore.getInstance()

    private lateinit var  auth: FirebaseAuth
    private var currentFirebaseUser = FirebaseAuth.getInstance().currentUser

    fun updateUserDetails(name: String, mobile: String): MutableLiveData<Int> {
        val updateUserDetailsMutableLiveData: MutableLiveData<Int> = MutableLiveData<Int>()
        var listRecipe = ArrayList<Recipe>()
        listRecipe.clear()
        updateUserDetailsMutableLiveData.value = 0
        auth = FirebaseAuth.getInstance()
        val user = User(
            name = name,
            mobile = mobile,
        )
        mFireStore.collection(FirebaseVal.USERS).document(currentFirebaseUser!!.uid)
            .update("user", user.name,
            "mobile", user.mobile)
            .addOnSuccessListener {
                updateUserDetailsMutableLiveData.value = 1
            }
            .addOnFailureListener {
                updateUserDetailsMutableLiveData.value = 2
            }
        return updateUserDetailsMutableLiveData
    }

    fun getUserDetails(): MutableLiveData<User> {
        val getUserDetailsMutableLiveData: MutableLiveData<User> = MutableLiveData<User>()
        auth = FirebaseAuth.getInstance()
        mFireStore.collection(FirebaseVal.USERS).document(currentFirebaseUser!!.uid).get()
            .addOnSuccessListener { user ->
                if(user != null) {
                    getUserDetailsMutableLiveData.value = user.toObject<User>()
                }
            }
        return getUserDetailsMutableLiveData
    }

    fun reAuth(email: String, password: String): MutableLiveData<Int> {
        val reAuthMutableLiveData: MutableLiveData<Int> = MutableLiveData<Int>()
        reAuthMutableLiveData.value = 0
        auth = FirebaseAuth.getInstance()
        val credential = EmailAuthProvider.getCredential(email, password)
        auth.currentUser!!.reauthenticate(credential).addOnSuccessListener {
            reAuthMutableLiveData.value = 1
        }
        .addOnFailureListener {
            reAuthMutableLiveData.value = 2
        }
        return reAuthMutableLiveData
    }


    fun changePassword(newPassword: String): MutableLiveData<Int> {
        val changePasswordMutableLiveData: MutableLiveData<Int> = MutableLiveData<Int>()
        changePasswordMutableLiveData.value = 0
        auth = FirebaseAuth.getInstance()
        auth.currentUser!!.updatePassword(newPassword)
            ?.addOnSuccessListener {
                changePasswordMutableLiveData.value = 1
        }.addOnFailureListener {
            changePasswordMutableLiveData.value = 2
        }
        return changePasswordMutableLiveData
    }


    fun changeEmail(newEmail: String): MutableLiveData<Int> {
        val changeEmailMutableLiveData: MutableLiveData<Int> = MutableLiveData<Int>()
        changeEmailMutableLiveData.value = 0
        auth = FirebaseAuth.getInstance()
        auth.currentUser!!.updateEmail(newEmail)
            ?.addOnSuccessListener {
            mFireStore.collection(FirebaseVal.USERS)
                .document(auth.currentUser!!.uid)
                .update("email", newEmail)
                .addOnSuccessListener {
                    changeEmailMutableLiveData.value = 1
                }
        }.addOnFailureListener {
                changeEmailMutableLiveData.value = 2
        }
        return changeEmailMutableLiveData
    }

    fun forgotPassword(email: String): MutableLiveData<Int> {
        val forgotPasswordMutableLiveData: MutableLiveData<Int> = MutableLiveData<Int>()
        forgotPasswordMutableLiveData.value = 0
        auth = FirebaseAuth.getInstance()
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener{ sentEmail ->
                if (sentEmail.isSuccessful) {
                    forgotPasswordMutableLiveData.value = 1
                } else {
                    forgotPasswordMutableLiveData.value = 2
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
            .addOnSuccessListener {
                loginMutableLiveData.value = true
            }
            .addOnFailureListener {
                loginMutableLiveData.value = false
            }
        return loginMutableLiveData
    }


    fun register(name: String ,email: String, password: String): MutableLiveData<Boolean> {
        val newUserMutableLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
        auth = FirebaseAuth.getInstance()
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
                    mFireStore.collection(FirebaseVal.USERS)
                        .document(user.id)
                        .set(user, SetOptions.merge())
                        .addOnCompleteListener { addDetails ->
                            if (addDetails.isSuccessful) {
                                newUserMutableLiveData.value = true
                            }
                        }
                } else {
                    newUserMutableLiveData.value = false
                }
            }
        return newUserMutableLiveData
    }




}