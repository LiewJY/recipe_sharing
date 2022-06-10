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
import com.example.andriod.maeassignment.utils.Validation
import com.example.andriod.maeassignment.viewmodel.app.account.EditRecipeViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
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
    private lateinit var binding: FragmentEditRecipeBinding

    private var ingredientsList = ArrayList<String>()
    private var methodList = ArrayList<String>()
    //    for image picker
    private  var imageUrl: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentEditRecipeBinding>(
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        validationListener()
        ///populate data
        viewModel.recipeData.observe(viewLifecycleOwner) { recipeData ->
            //title and desc
            this.txtEditRecipeTitle.setText(recipeData.title)
            this.txtEditRecipeDesc.setText(recipeData.desc)
            //image
            Glide.with(this)
                .load(recipeData.image)
                .placeholder(R.drawable.logo)
                .into(image_preview)

            //ingredients & methods
            recipeData.ingredients.forEach{ ingredients ->
                addIngredientFromFirebase(ingredients)
            }
            recipeData.methods.forEach{ methods ->
                addMethodFromFirebase(methods)
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
                    //add ingredients and methods into array list
                    loadIngredients()
                    loadMethods()
                    if (validationError() == true) {
                        viewModel.updateRecipe(
                            recipeId.toString(),
                            txtEditRecipeTitle.text.toString(),
                            txtEditRecipeDesc.text.toString(),
                            imageUrl,
                            ingredientsList,
                            methodList,
                        )
                        viewModel.updateRecipeStatus.observe(this) { result ->
                            if (result == 1) {
                                Toast.makeText(context, "Recipe updated", Toast.LENGTH_SHORT).show()
                            }else if (result == 2) {
                                Toast.makeText(context, "Recipe failed to update", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }

    }


    private fun validationListener() {
        binding.txtEditRecipeTitle.setOnFocusChangeListener{_, focused ->
            if (!focused)
            {
                binding.containerEditRecipeTitle.helperText = Validation.titleValidation(binding.txtEditRecipeTitle.text.toString())
            }
        }
        binding.txtEditRecipeDesc.setOnFocusChangeListener{_, focused ->
            if (!focused)
            {
                binding.containerEditRecipeDesc.helperText = Validation.descriptionValidation(binding.txtEditRecipeDesc.text.toString())
            }
        }
    }
    private fun validationError(): Boolean {
        binding.containerEditRecipeTitle.helperText = Validation.titleValidation(binding.txtEditRecipeTitle.text.toString())
        binding.containerEditRecipeDesc.helperText = Validation.descriptionValidation(binding.txtEditRecipeDesc.text.toString())

        if(binding.containerEditRecipeTitle.helperText != null)
        {
            Snackbar.make(requireActivity().findViewById(android.R.id.content), "${binding.containerEditRecipeTitle.helperText}", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if(binding.containerEditRecipeDesc.helperText != null)
        {
            Snackbar.make(requireActivity().findViewById(android.R.id.content), "${binding.containerEditRecipeDesc.helperText}", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if(ingredientsList.count() < 1)
        {
            Snackbar.make(requireActivity().findViewById(android.R.id.content), Validation.noIngredient, Snackbar.LENGTH_SHORT).show()
            return false
        }
        if(ingredientsList.contains(""))
        {
            Snackbar.make(requireActivity().findViewById(android.R.id.content), Validation.emptyIngredient, Snackbar.LENGTH_SHORT).show()
            return false
        }
        if(methodList.count() < 1)
        {
            Snackbar.make(requireActivity().findViewById(android.R.id.content), Validation.noMethod, Snackbar.LENGTH_SHORT).show()
            return false
        }
        if(methodList.contains(""))
        {
            Snackbar.make(requireActivity().findViewById(android.R.id.content), Validation.emptyMethod, Snackbar.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private  fun loadMethods() {
        methodList.clear()
        val count = this.method_parent_linear_layout.childCount
        var view: View?
        for (i in 0 until count) {
            view = this.method_parent_linear_layout.getChildAt(i)
            val methods: EditText = view.findViewById(R.id.txtMethods)
            methodList.add(methods.text.toString())
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
            (inflater.parent as LinearLayout).removeView(inflater)
        }
        val editTextIngredient: EditText = inflater.findViewById(R.id.txtIngredients) as EditText
        val containerIngredient: TextInputLayout = inflater.findViewById(R.id.containerIngredients) as TextInputLayout
        editTextIngredient.setOnFocusChangeListener { _, focused ->
            if (!focused)
            {
                containerIngredient.helperText = Validation.ingredientValidation(editTextIngredient.text.toString())
            }
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
            (inflater.parent as LinearLayout).removeView(inflater)
        }
        val editTextMethod: EditText = inflater.findViewById(R.id.txtMethods) as EditText
        val containerMethod: TextInputLayout = inflater.findViewById(R.id.containerMethods) as TextInputLayout
        editTextMethod.setOnFocusChangeListener { _, focused ->
            if (!focused)
            {
                containerMethod.helperText = Validation.methodValidation(editTextMethod.text.toString())
            }
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
                    .placeholder(R.drawable.logo)
                    .into(this.image_preview)
                //Log.e("frag", "ing")
                imageUrl = selectedImage
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }

}