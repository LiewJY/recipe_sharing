package com.example.andriod.maeassignment.repository

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.andriod.maeassignment.models.Recipe
import com.example.andriod.maeassignment.utils.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*


class AddRepository {

    private val mFireStore = FirebaseFirestore.getInstance()
    //private lateinit var  auth: FirebaseAuth

    //for firestore image
    private var storageReference = FirebaseStorage.getInstance().reference
    private var currentFirebaseUser = FirebaseAuth.getInstance().currentUser
    private lateinit var imageLink: String

    //private var user : FirebaseAuth? = null

    fun uploadImage(imageUrl: Uri?) {
        //add image to firestore first
        if (imageUrl != null) {
            var uri = Uri.parse(imageUrl.toString())
            val  imageRef = storageReference.child("images/" + currentFirebaseUser!!.uid + "/" + UUID.randomUUID().toString())
            //val uploadTask = imageRef.putFile(imageUrl.)
            val uploadTask = imageRef?.putFile(uri)
            uploadTask.addOnSuccessListener {
                val downloadUrl = imageRef.downloadUrl
                downloadUrl.addOnSuccessListener {
                    imageLink = it.toString()
                    Log.e("frag", "SUCCESS url   $imageLink")
                }
            }

        }
    }
    fun addRecipe(recipeTitle: String,recipeDesc: String,imageUrl: Uri?,ingredientsList: ArrayList<String>, methodList: ArrayList<String>): MutableLiveData<Boolean> {
        val addRecipeMutableLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
        uploadImage(imageUrl)

        val recipe = Recipe(
            userid = currentFirebaseUser!!.uid,
            image = imageLink,
            title = recipeTitle,
            desc = recipeDesc,
            ingredients = ingredientsList,
            methods = methodList,
        )
        mFireStore.collection(Firebase.RECIPES)
            .add(recipe)
            .addOnCompleteListener{ addRecipe ->
                if(addRecipe.isSuccessful) {

                    Log.e("frag", "SUCCESS added recipe")
                }
            }



        //if (auth.currentUser == null) {addRecipeMutableLiveData.value = true}

        return addRecipeMutableLiveData
    }

}