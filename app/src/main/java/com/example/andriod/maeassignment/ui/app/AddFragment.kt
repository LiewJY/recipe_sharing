package com.example.andriod.maeassignment.ui.app

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.andriod.maeassignment.R
import com.example.andriod.maeassignment.databinding.FragmentAddBinding
import kotlinx.android.synthetic.main.fragment_add.*
import java.io.FileNotFoundException
import java.util.*


class AddFragment : Fragment(), View.OnClickListener {

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
            }
        }
    }

//    for image picker
    private fun launchGallery() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        pickImageResultLauncher.launch(photoPickerIntent)
    }
    var pickImageResultLauncher = registerForActivityResult(
        StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val selectedImage: Uri? = Objects.requireNonNull(data)?.data
            try {
                Glide.with(this)
                    .load(selectedImage)
                    .into(this.image_preview)
                 Log.e("frag", "ing")
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }
}
