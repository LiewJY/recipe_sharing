package com.example.andriod.maeassignment.repository

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.andriod.maeassignment.models.Recipe
import com.example.andriod.maeassignment.utils.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.storage.FirebaseStorage
import java.util.*


class RecipeRepository {

    private val mFireStore = FirebaseFirestore.getInstance()

    //private lateinit var  auth: FirebaseAuth

    //for firestore image
    private var storageReference = FirebaseStorage.getInstance().reference
    private var currentFirebaseUser = FirebaseAuth.getInstance().currentUser
    private lateinit var imageLink: String

    //for read data
    private val mFirebaseDatabase = FirebaseDatabase.getInstance()


    fun getRecipes(): ArrayList<Recipe> {
        //val getRecipeMutableLiveData: MutableLiveData<ArrayList<Recipe>> = MutableLiveData<ArrayList<Recipe>>()

        var recipeArrayList : ArrayList<Recipe> = ArrayList<Recipe>()
        Log.e("frag", "SUCCESS get")
        mFireStore.collection(Firebase.RECIPES).get()
            .addOnSuccessListener { recipes ->
                if(recipes != null) {
                    val data = recipes.toObjects<Recipe>()
                    data.toList()
//                    getRecipeMutableLiveData.value = ArrayList(data)
                    recipeArrayList = ArrayList(data)
                    Log.e("frag", "SUCCESS get ${data.toList()}")
                    Log.e("frag", "SUCCESS get $recipeArrayList")
                    //Log.e("frag", "SUCCESS get ${getRecipeMutableLiveData.value}")
                    Log.e("frag", "SUCCESS get $data")
                }
            }
        return recipeArrayList


//        mFirebaseDatabase.getReference(Firebase.USERS)
//            .addValueEventListener(object:ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    if (snapshot.exists()){
//                        Log.e("frag", "SUCCESS get 1")
//
//                        for (recipesSnapshot in snapshot.children){
//                            val recipe = recipesSnapshot.getValue(Recipe::class.java)
//                            recipeArrayList.add(recipe!!)
//                            Log.e("frag", "SUCCESS COUNT ADD")
//
//                        }
//                        //userRecyclerview.adapter = MyAdapter(recipeArrayList)
//                    }
//            }
//                override fun onCancelled(error: DatabaseError) {
//                    TODO("Not yet implemented")
//                }
//            })

    }


    fun addRecipe(recipeTitle: String,recipeDesc: String,imageUrl: Uri?,ingredientsList: ArrayList<String>, methodList: ArrayList<String>): MutableLiveData<Boolean> {
        val addRecipeMutableLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
        if (imageUrl != null) {
            //image is present
            // upload the image to firebase
            var uri = Uri.parse(imageUrl.toString())
            val  imageRef = storageReference.child("images/" + currentFirebaseUser!!.uid + "/" + UUID.randomUUID().toString())
            val uploadTask = imageRef?.putFile(uri)
            uploadTask.addOnSuccessListener {
                //get the url for the image
                val downloadUrl = imageRef.downloadUrl
                downloadUrl.addOnSuccessListener {
                    //store data to firebase with info
                    imageLink = it.toString()
                    val recipe = Recipe(
                        userid = currentFirebaseUser!!.uid,
                        title = recipeTitle,
                        desc = recipeDesc,
                        ingredients = ingredientsList,
                        methods = methodList,
                        image = imageLink,
                    )
                    mFireStore.collection(Firebase.RECIPES)
                        .add(recipe)
                        .addOnCompleteListener{ addRecipe ->
                            if(addRecipe.isSuccessful){
                                Log.e("frag", "SUCCESS added recipe with image   $imageLink")
                            }
                        }
                }
                downloadUrl.addOnFailureListener {
                    Log.e("frag", "failed to add recipe with image")
                }
            }
        }else {
            //no image
            val recipe = Recipe(
                userid = currentFirebaseUser!!.uid,
                title = recipeTitle,
                desc = recipeDesc,
                ingredients = ingredientsList,
                methods = methodList,
            )
            mFireStore.collection(Firebase.RECIPES)
                .add(recipe)
                .addOnCompleteListener{ addRecipe ->
                    if(addRecipe.isSuccessful){
                        Log.e("frag", "SUCCESS added recipe with no image")
                    }
                }
        }
        return addRecipeMutableLiveData
    }

}