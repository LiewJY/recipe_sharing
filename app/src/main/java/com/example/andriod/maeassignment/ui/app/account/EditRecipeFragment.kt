package com.example.andriod.maeassignment.ui.app.account

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.andriod.maeassignment.R
import com.example.andriod.maeassignment.databinding.FragmentEditRecipeBinding
import com.example.andriod.maeassignment.viewmodel.app.account.EditRecipeViewModel
import kotlinx.android.synthetic.main.fragment_add.ingredient_parent_linear_layout
import kotlinx.android.synthetic.main.fragment_add.method_parent_linear_layout
import kotlinx.android.synthetic.main.fragment_edit_recipe.*
import java.io.FileNotFoundException
import java.util.*

class EditRecipeFragment : Fragment(), View.OnClickListener {

    private var recipeId :String? = null
    private val viewModel: EditRecipeViewModel by lazy {
        ViewModelProvider(this).get(EditRecipeViewModel::class.java)
    }

    //    for image picker
    private  var imageUrl: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentEditRecipeBinding>(
            inflater,
            R.layout.fragment_edit_recipe, container, false
        )

        binding.editRecipeToolbar.setOnClickListener {
            activity?.onBackPressed()
        }




        //register event handler (for the button)
        binding.btnEditImage.setOnClickListener(this)
        binding.btnAddIngredient.setOnClickListener(this)
        binding.btnAddMethod.setOnClickListener(this)
        binding.btnUpdate.setOnClickListener(this)

        recipeId = arguments?.getString("id")
        viewModel.getRecipe(recipeId.toString())
        Log.e("frag", "got in edit $recipeId")


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val testdata = arrayOf("no 1", "no 2", "no 3", "no 4")


        viewModel.recipeData.observe(viewLifecycleOwner) { recipeData ->
            //title and desc
            this.txtEditRecipeTitle.setText(recipeData.title)
            this.txtEditRecipeDesc.setText(recipeData.desc)

            //image
            Glide.with(this)
                .load(recipeData.image)
                .placeholder(R.mipmap.ic_launcher_round)
                .into(image_preview)

            //ingredients & methods
            recipeData.ingredients.forEach{ ingredients ->
                addIngredientFromFirebase(ingredients)
                Log.e("frag", "for each $ingredients")
            }
            recipeData.methods.forEach{ methods ->
                addMethodFromFirebase(methods)
                Log.e("frag", "for each $methods")
            }

        }

    }

    override fun onClick(v: View?) {

        if (v != null) {
            when (v.id) {
                R.id.btnEditImage -> {
                    launchGallery()
                }
                R.id.btnAddIngredient -> {
                    addIngredient()
                }
                R.id.btnAddMethod -> {
                    addMethod()
                }
                R.id.btnUpdate -> {
                    updateRecipe()
                }
            }
        }
    }
//    fun onDelete(v: View) {
//        Log.e("frag", "od pressed")
//        this.ingredient_parent_linear_layout.removeView(v.parent as View)
//    }

    private var ingredientsList = ArrayList<String>()
    private var methodList = ArrayList<String>()

    private fun updateRecipe() {
        Log.e("frag", "update")
        //add ingredients and methods into array list
        loadIngredients()
        loadMethods()


        viewModel.updateRecipe(
            recipeId.toString(),
            txtEditRecipeTitle.text.toString(),
            txtEditRecipeDesc.text.toString(),
            imageUrl,
            ingredientsList,
            methodList,
        )
        //todo return success message
        viewModel.updateRecipeStatus.observe(this) { result ->
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
            //Toast.makeText(context, "Method at $i is ${methodList[i]}  ", Toast.LENGTH_SHORT).show()
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
            //Toast.makeText(context, "Ingredient at $i is ${ingredientsList[i]}  ", Toast.LENGTH_SHORT).show()
        }
    }
    private fun addIngredientFromFirebase(ingredient : String) {
        // this method inflates the single item layout
        // inside the parent linear layout
        val inflater = LayoutInflater.from(context).inflate(R.layout.add_ingredient_row_layout, null)
        this.ingredient_parent_linear_layout.addView(inflater)
        inflater.findViewById<EditText>(R.id.txtIngredients).setText(ingredient)
//        add control for remove button
        val buttonRemove: Button = inflater.findViewById(R.id.btnRemove) as Button
        buttonRemove.setOnClickListener{
            Log.e("frag", "remove pressed")
            (inflater.parent as LinearLayout).removeView(inflater)
        }
    }
    private fun addMethodFromFirebase(method : String) {
        // this method inflates the single item layout
        // inside the parent linear layout
        val inflater = LayoutInflater.from(context).inflate(R.layout.add_method_row_layout, null)
        this.method_parent_linear_layout.addView(inflater)
        inflater.findViewById<EditText>(R.id.txtMethods).setText(method)
        //add control for remove button
        val buttonRemove: Button = inflater.findViewById(R.id.btnRemove) as Button
        buttonRemove.setOnClickListener{
            Log.e("frag", "remove pressed")
            (inflater.parent as LinearLayout).removeView(inflater)
        }
    }
    private fun addIngredient() {
        // this method inflates the single item layout
        // inside the parent linear layout
        val inflater = LayoutInflater.from(context).inflate(R.layout.add_ingredient_row_layout, null)
        this.ingredient_parent_linear_layout.addView(inflater, this.ingredient_parent_linear_layout.childCount)
        //add control for remove button
        val buttonRemove: Button = inflater.findViewById(R.id.btnRemove) as Button
        buttonRemove.setOnClickListener{
            Log.e("frag", "remove pressed")
            (inflater.parent as LinearLayout).removeView(inflater)
        }
    }
    private fun addMethod() {
        // this method inflates the single item layout
        // inside the parent linear layout
        val inflater = LayoutInflater.from(context).inflate(R.layout.add_method_row_layout, null)
        this.method_parent_linear_layout.addView(inflater, this.method_parent_linear_layout.childCount)
        //add control for remove button
        val buttonRemove: Button = inflater.findViewById(R.id.btnRemove) as Button
        buttonRemove.setOnClickListener{
            Log.e("frag", "remove pressed")
            (inflater.parent as LinearLayout).removeView(inflater)
        }
    }

//    //    for image picker
    private fun launchGallery() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        pickImageResultLauncher.launch(photoPickerIntent)
    }

    private var pickImageResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
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