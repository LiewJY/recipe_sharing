package com.example.andriod.maeassignment.repository

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.andriod.maeassignment.models.Recipe
import com.example.andriod.maeassignment.models.User
import com.example.andriod.maeassignment.utils.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
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
    private lateinit var  userInfo: User


    //get favourite recipe from this logged in user
    fun getFavourite(): MutableLiveData<ArrayList<Recipe>> {
        val getFavouriteRecipesMutableLiveData: MutableLiveData<ArrayList<Recipe>> = MutableLiveData<ArrayList<Recipe>>()
        Log.e("frag", "SUCCESS get")
        var listFavorites = ArrayList<String>()
        listFavorites.add("")
        mFireStore.collection(Firebase.USERS).document(currentFirebaseUser!!.uid).get()
            .addOnSuccessListener { fav ->
                val  userFavourite = fav.toObject<User>()
                for (item in userFavourite!!.favourite) {
                    listFavorites.add(item)
                    Log.e("frag", "test get ${item}")
                }
                mFireStore.collection(Firebase.RECIPES).whereIn("id", listFavorites).get()
                    .addOnSuccessListener { favourite ->
                        if(favourite != null) {
                            val data = favourite.toObjects<Recipe>()
                            data.toList()
                            getFavouriteRecipesMutableLiveData.value = ArrayList(data)
                            Log.e("frag", "SUCCESS get ${data.toList()}")
                            Log.e("frag", "SUCCESS get $data")
                        }
                    }
            }
        return getFavouriteRecipesMutableLiveData
    }

    fun addFavourite(recipeId: String): MutableLiveData<Int> {
        val addFavouriteRecipesMutableLiveData: MutableLiveData<Int> = MutableLiveData<Int>()
        addFavouriteRecipesMutableLiveData.value = 0
        mFireStore.collection(Firebase.USERS).document(currentFirebaseUser!!.uid)
            .update("favourite", FieldValue.arrayUnion(recipeId))
            .addOnSuccessListener {
                addFavouriteRecipesMutableLiveData.value = 1
            }
            .addOnFailureListener {
                addFavouriteRecipesMutableLiveData.value = 2
            }
        return addFavouriteRecipesMutableLiveData
    }

    fun removeFavourite(recipeId: String): MutableLiveData<Int> {
        val removeFavouriteRecipesMutableLiveData: MutableLiveData<Int> = MutableLiveData<Int>()
        removeFavouriteRecipesMutableLiveData.value = 0
        mFireStore.collection(Firebase.USERS).document(currentFirebaseUser!!.uid)
            .update("favourite", FieldValue.arrayRemove(recipeId))
            .addOnSuccessListener {
                removeFavouriteRecipesMutableLiveData.value = 1
            }
            .addOnFailureListener {
                removeFavouriteRecipesMutableLiveData.value = 2
            }
        return removeFavouriteRecipesMutableLiveData
    }


    fun updateRecipe(recipeId: String ,recipeTitle: String,recipeDesc: String,imageUrl: Uri?,ingredientsList: ArrayList<String>, methodList: ArrayList<String>): MutableLiveData<Int> {
        val updateRecipeMutableLiveData: MutableLiveData<Int> = MutableLiveData<Int>()
        updateRecipeMutableLiveData.value = 0
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
//                        id = recipeId,
//                        userid = currentFirebaseUser!!.uid,
                        title = recipeTitle,
                        desc = recipeDesc,
                        ingredients = ingredientsList,
                        methods = methodList,
                        image = imageLink,
                    )
                    mFireStore.collection(Firebase.RECIPES)
                        .document(recipeId)
                        .update("title", recipe.title,
                            "desc", recipe.desc,
                            "ingredients", recipe.ingredients,
                            "methods", recipe.methods,
                            "image", recipe.image)
                        .addOnCompleteListener{ updateRecipe ->
                            if(updateRecipe.isSuccessful){
                                updateRecipeMutableLiveData.value = 1
                            }
                        }
                }
                downloadUrl.addOnFailureListener {
                    updateRecipeMutableLiveData.value = 2
                }
            }
        }else {
            //no image update
            val recipe = Recipe(
//                id = recipeId,
//                userid = currentFirebaseUser!!.uid,
                title = recipeTitle,
                desc = recipeDesc,
                ingredients = ingredientsList,
                methods = methodList,
            )
            mFireStore.collection(Firebase.RECIPES)
                .document(recipeId)
                .update("title", recipe.title,
            "desc", recipe.desc,
                    "ingredients", recipe.ingredients,
                    "methods", recipe.methods)
                .addOnSuccessListener{
                    updateRecipeMutableLiveData.value = 1
                }
                .addOnFailureListener {
                    updateRecipeMutableLiveData.value = 2
                }
        }

        return updateRecipeMutableLiveData
    }


    //delete a recipe
    fun deleteRecipe(recipeId: String): MutableLiveData<Int> {
        val deleteRecipeMutableLiveData: MutableLiveData<Int> = MutableLiveData<Int>()
        deleteRecipeMutableLiveData.value = 0
        mFireStore.collection(Firebase.RECIPES).document(recipeId).delete()
            .addOnSuccessListener {
                deleteRecipeMutableLiveData.value = 1
            }
            .addOnFailureListener {
                deleteRecipeMutableLiveData.value = 2
            }
        return deleteRecipeMutableLiveData
    }




    //get recipe from this logged in user
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


    //get a single recipe
    fun getRecipe(recipeId: String): MutableLiveData<Recipe> {
        val getRecipeMutableLiveData: MutableLiveData<Recipe> = MutableLiveData<Recipe>()

        //var recipeArrayList : ArrayList<Recipe> = ArrayList<Recipe>()
        Log.e("frag", "SUCCESS get")
        mFireStore.collection(Firebase.RECIPES).document(recipeId).get()
            .addOnSuccessListener { recipe ->
                if(recipe != null) {
                    getRecipeMutableLiveData.value = recipe.toObject<Recipe>()


                }
            }
        return getRecipeMutableLiveData
    }

    //get all recipe in database
    fun getRecipes(): MutableLiveData<ArrayList<Recipe>> {
        val getRecipesMutableLiveData: MutableLiveData<ArrayList<Recipe>> = MutableLiveData<ArrayList<Recipe>>()
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


    fun addRecipe(recipeTitle: String,recipeDesc: String,imageUrl: Uri?,ingredientsList: ArrayList<String>, methodList: ArrayList<String>): MutableLiveData<Int> {
        val addRecipeMutableLiveData: MutableLiveData<Int> = MutableLiveData<Int>()
        addRecipeMutableLiveData.value = 0
        mFireStore.collection(Firebase.USERS).document(currentFirebaseUser!!.uid).get()
            .addOnSuccessListener {
                userInfo = it.toObject<User>()!!
                //get the uid first
                val uid = mFireStore.collection(Firebase.RECIPES).document()
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
                                name = userInfo.name,
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
                                        addRecipeMutableLiveData.value = 1
                                    }
                                }
                        }
                        downloadUrl.addOnFailureListener {
                            addRecipeMutableLiveData.value = 2
                        }
                    }
                }else {
                    //no image
                    val recipe = Recipe(
                        id = uid.id,
                        userid = currentFirebaseUser!!.uid,
                        name = userInfo.name,
                        title = recipeTitle,
                        desc = recipeDesc,
                        ingredients = ingredientsList,
                        methods = methodList,
                    )
                    mFireStore.collection(Firebase.RECIPES)
                        .document(uid.id)
                        .set(recipe)
                        .addOnSuccessListener{
                            addRecipeMutableLiveData.value = 1
                        }
                        .addOnFailureListener {
                            addRecipeMutableLiveData.value = 2
                        }
                }

            }
            .addOnFailureListener {
                addRecipeMutableLiveData.value = 2
           }



        return addRecipeMutableLiveData
    }

}