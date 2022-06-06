package com.example.andriod.maeassignment.ui.app

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.andriod.maeassignment.R
import com.example.andriod.maeassignment.databinding.FragmentAddBinding
import com.example.andriod.maeassignment.utils.Validation
import com.example.andriod.maeassignment.viewmodel.app.AddViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_add.*
import java.io.FileNotFoundException
import java.util.*


class AddFragment : Fragment(), View.OnClickListener {
    private val viewModel: AddViewModel by lazy {
        ViewModelProvider(this).get(AddViewModel::class.java)
    }
    private lateinit var binding: FragmentAddBinding

    private var ingredientsList = ArrayList<String>()
    private var methodList = ArrayList<String>()
    //    for image picker
    private  var imageUrl: Uri? = null




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentAddBinding>(
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
            .placeholder(android.R.drawable.ic_menu_report_image)
            .into(binding.imagePreview)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        validationListener()
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
                    //add ingredients and methods into array list
                    loadIngredients()
                    loadMethods()
                    if (validationError() == true) {
                        viewModel.addRecipe(
                            txtRecipeTitle.text.toString(),
                            txtRecipeDesc.text.toString(),
                            imageUrl,
                            ingredientsList,
                            methodList,
                        )
                        viewModel.addRecipeStatus.observe(this) { result ->
                            if (result == 1) {
                                Toast.makeText(context, "Recipe added", Toast.LENGTH_SHORT).show()
                            }else if (result == 2) {
                                Toast.makeText(context, "Recipe failed to add", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                }
            }
        }
    }


    private fun validationListener() {
        binding.txtRecipeTitle.setOnFocusChangeListener{_, focused ->
            if (!focused)
            {
                binding.containerRecipeTitle.helperText = Validation.titleValidation(binding.txtRecipeTitle.text.toString())
            }
        }
        binding.txtRecipeDesc.setOnFocusChangeListener{_, focused ->
            if (!focused)
            {
                binding.containerRecipeDesc.helperText = Validation.descriptionValidation(binding.txtRecipeDesc.text.toString())
            }
        }
    }
    private fun validationError(): Boolean {
        binding.containerRecipeTitle.helperText = Validation.titleValidation(binding.txtRecipeTitle.text.toString())
        binding.containerRecipeDesc.helperText = Validation.descriptionValidation(binding.txtRecipeDesc.text.toString())

        if(binding.containerRecipeTitle.helperText != null)
        {
            Snackbar.make(requireActivity().findViewById(android.R.id.content), "${binding.containerRecipeTitle.helperText}", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if(binding.containerRecipeDesc.helperText != null)
        {
            Snackbar.make(requireActivity().findViewById(android.R.id.content), "${binding.containerRecipeDesc.helperText}", Snackbar.LENGTH_SHORT).show()
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
    private fun addIngredient() {
        // this method inflates the single item layout
        // inside the parent linear layout
        val inflater = LayoutInflater.from(context).inflate(R.layout.add_ingredient_row_layout, null)
        this.ingredient_parent_linear_layout.addView(inflater)
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
        this.method_parent_linear_layout.addView(inflater)
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
                imageUrl = selectedImage
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }
}
