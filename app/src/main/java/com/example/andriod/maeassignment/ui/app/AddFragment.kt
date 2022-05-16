package com.example.andriod.maeassignment.ui.app

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.andriod.maeassignment.R
import com.example.andriod.maeassignment.databinding.FragmentAddBinding
import com.example.andriod.maeassignment.viewmodel.app.AddViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import java.io.FileNotFoundException
import java.util.*


class AddFragment : Fragment(), View.OnClickListener {
    private val viewModel: AddViewModel by lazy {
        ViewModelProvider(this).get(AddViewModel::class.java)
    }
    //    for image picker
    private  var imageUrl: Uri? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentAddBinding>(
            inflater,
            R.layout.fragment_add, container, false
        )
        //register event handler (for the button)
        binding.btnAddImage.setOnClickListener(this)
        binding.btnAddIngredient.setOnClickListener(this)
        binding.btnAddMethod.setOnClickListener(this)
        binding.btnPublish.setOnClickListener(this)

        Glide.with(this)
            .load("http://via.placeholder.com/300.png")
            .dontAnimate()
            .placeholder(R.mipmap.ic_launcher_round)
            .into(binding.imagePreview)

        return binding.root
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btnAddImage -> {
                    launchGallery()
                }
                R.id.btnAddIngredient -> {
                    addIngredient()
                }
                R.id.btnAddMethod -> {
                    addMethod()
                }
                R.id.btnPublish -> {
                    publishRecipe()
                }
//                R.id.button -> {
//                    //onDelete()
//                    //ingredient_parent_linear_layout.removeView((view))
//                }
            }
        }
    }
//    fun onDelete(v: View) {
//        this.ingredient_parent_linear_layout.removeView(v.context as View)
//    }
    private var ingredientsList = ArrayList<String>()
    private var methodList = ArrayList<String>()

    private fun publishRecipe() {
        Log.e("frag", "publish")
        //add ingredients and methods into array list
        loadIngredients()
        loadMethods()
        viewModel.addRecipe(
            txtRecipeTitle.text.toString(),
            txtRecipeDesc.text.toString(),
            imageUrl,
            ingredientsList,
            methodList,
            )
        //todo return success message
        viewModel.addRecipeStatus.observe(this) { result ->
            if (result == true) {
                Toast.makeText(context, "Recipe Added", Toast.LENGTH_SHORT).show()
            }else {
                //todo
            }
        }


    }

    private  fun loadMethods() {
        methodList.clear()
        val count = this.method_parent_linear_layout.childCount
        var view: View?
        for (i in 0 until count) {
            view = this.method_parent_linear_layout.getChildAt(i)
            val methods: EditText = view.findViewById(R.id.txtMethods)
            methodList.add(methods.text.toString())
            Toast.makeText(context, "Method at $i is ${methodList[i]}  ", Toast.LENGTH_SHORT).show()
        }
    }
    private  fun loadIngredients() {
        ingredientsList.clear()
        val count = this.ingredient_parent_linear_layout.childCount
        var view: View?
        for (i in 0 until count) {
            view = this.ingredient_parent_linear_layout.getChildAt(i)
            val ingredients: EditText = view.findViewById(R.id.txtIngredients)
            ingredientsList.add(ingredients.text.toString())
            Toast.makeText(context, "Ingredient at $i is ${ingredientsList[i]}  ", Toast.LENGTH_SHORT).show()
        }
    }
    private fun addIngredient() {
        // this method inflates the single item layout
        // inside the parent linear layout
        val inflater = LayoutInflater.from(context).inflate(R.layout.add_ingredient_row_layout, null)
        this.ingredient_parent_linear_layout.addView(inflater)
    }
    private fun addMethod() {
        // this method inflates the single item layout
        // inside the parent linear layout
        val inflater = LayoutInflater.from(context).inflate(R.layout.add_method_row_layout, null)
        this.method_parent_linear_layout.addView(inflater)
    }

//    for image picker
    private fun launchGallery() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        pickImageResultLauncher.launch(photoPickerIntent)
    }
    private var pickImageResultLauncher = registerForActivityResult(
        StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val selectedImage: Uri? = Objects.requireNonNull(data)?.data
            try {
                Glide.with(this)
                    .load(selectedImage)
                    .into(this.image_preview)
                 //Log.e("frag", "ing")
                imageUrl = selectedImage
                Log.e("frag", "img url = $imageUrl   img selecred = $selectedImage")

            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }
}
