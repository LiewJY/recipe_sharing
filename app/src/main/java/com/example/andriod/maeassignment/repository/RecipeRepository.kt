package com.example.andriod.maeassignment.repository

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.andriod.maeassignment.models.Recipe
import com.example.andriod.maeassignment.utils.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
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
    //private val mFirebaseDatabase = FirebaseDatabase.getInstance()
    
    //delete
    fun deleteRecipe(recipeId: String): MutableLiveData<Boolean> {
        val deleteRecipeMutableLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
        deleteRecipeMutableLiveData.value = false
        mFireStore.collection(Firebase.RECIPES).document(recipeId).delete()
            .addOnSuccessListener {
                deleteRecipeMutableLiveData.value = true
                Log.e("frag", "SUCCESS delete")
            }
        deleteRecipeMutableLiveData

        return deleteRecipeMutableLiveData
    }



    fun getRecipesByAuthor(): MutableLiveData<ArrayList<Recipe>> {
        val getRecipesByAuthorMutableLiveData: MutableLiveData<ArrayList<Recipe>> = MutableLiveData<ArrayList<Recipe>>()

        //var recipeArrayList : ArrayList<Recipe> = ArrayList<Recipe>()
        Log.e("frag", "SUCCESS get")
        mFireStore.collection(Firebase.RECIPES).whereEqualTo("userid", currentFirebaseUser!!.uid).get()
            .addOnSuccessListener { recipes ->
                if(recipes != null) {
                    val data = recipes.toObjects<Recipe>()
                    data.toList()
                    getRecipesByAuthorMutableLiveData.value = ArrayList(data)
                    //recipeArrayList = ArrayList(data)
                    Log.e("frag", "SUCCESS get ${data.toList()}")
                    //Log.e("frag", "SUCCESS get $recipeArrayList")
                    Log.e("frag", "SUCCESS get ${getRecipesByAuthorMutableLiveData.value}")
                    Log.e("frag", "SUCCESS get $data")
                }
            }
        return getRecipesByAuthorMutableLiveData
    }

    fun getRecipe(recipeId: String): MutableLiveData<Recipe> {
        val getRecipeMutableLiveData: MutableLiveData<Recipe> = MutableLiveData<Recipe>()

        //var recipeArrayList : ArrayList<Recipe> = ArrayList<Recipe>()
        Log.e("frag", "SUCCESS get")
        mFireStore.collection(Firebase.RECIPES).document(recipeId).get()
            .addOnSuccessListener { recipe ->
                if(recipe != null) {
                    //val data = recipes.toObject<Recipe>()
                    getRecipeMutableLiveData.value = recipe.toObject<Recipe>()
                    //recipeArrayList = ArrayList(data)
                    //Log.e("frag", "SUCCESS get ${getRecipeMutableLiveData.value}")
                }
            }
        return getRecipeMutableLiveData
    }

    fun getRecipes(): MutableLiveData<ArrayList<Recipe>> {
        val getRecipesMutableLiveData: MutableLiveData<ArrayList<Recipe>> = MutableLiveData<ArrayList<Recipe>>()

        //var recipeArrayList : ArrayList<Recipe> = ArrayList<Recipe>()
        Log.e("frag", "SUCCESS get")
        mFireStore.collection(Firebase.RECIPES).get()
            .addOnSuccessListener { recipes ->
                if(recipes != null) {
                    val data = recipes.toObjects<Recipe>()
                    data.toList()
                    getRecipesMutableLiveData.value = ArrayList(data)
                    //recipeArrayList = ArrayList(data)
                    Log.e("frag", "SUCCESS get ${data.toList()}")
                    //Log.e("frag", "SUCCESS get $recipeArrayList")
                    Log.e("frag", "SUCCESS get ${getRecipesMutableLiveData.value}")
                    Log.e("frag", "SUCCESS get $data")
                }
            }
        return getRecipesMutableLiveData
    }


    fun addRecipe(recipeTitle: String,recipeDesc: String,imageUrl: Uri?,ingredientsList: ArrayList<String>, methodList: ArrayList<String>): MutableLiveData<Boolean> {
        val addRecipeMutableLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
        addRecipeMutableLiveData.value = false
        //get the uid first
        val uid = mFireStore.collection(Firebase.RECIPES).document()
        Log.e("frag", "SUCCESS get uid ${uid.id}")

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
                        id = uid.id,
                        userid = currentFirebaseUser!!.uid,
                        title = recipeTitle,
                        desc = recipeDesc,
                        ingredients = ingredientsList,
                        methods = methodList,
                        image = imageLink,
                    )
                    mFireStore.collection(Firebase.RECIPES)
                        .document(uid.id)
                        .set(recipe)
                        .addOnCompleteListener{ addRecipe ->
                            if(addRecipe.isSuccessful){
                                Log.e("frag", "SUCCESS added recipe with image   $imageLink")
                                addRecipeMutableLiveData.value = true

                            }
                        }
                }
                downloadUrl.addOnFailureListener {
                    addRecipeMutableLiveData.value = true
                    Log.e("frag", "failed to add recipe with image")
                }
            }
        }else {
            //no image
            val recipe = Recipe(
                id = uid.id,
                userid = currentFirebaseUser!!.uid,
                title = recipeTitle,
                desc = recipeDesc,
                ingredients = ingredientsList,
                methods = methodList,
            )
            mFireStore.collection(Firebase.RECIPES)
                .document(uid.id)
                .set(recipe)
                .addOnCompleteListener{ addRecipe ->
                    if(addRecipe.isSuccessful){
                        Log.e("frag", "SUCCESS added recipe with no image")
                    }
                }
        }


        return addRecipeMutableLiveData
    }

}