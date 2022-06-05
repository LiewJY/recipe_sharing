package com.example.andriod.maeassignment.ui.app.account

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.andriod.maeassignment.R
import com.example.andriod.maeassignment.models.Recipe
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MyRecipeAdapter (private val context: Context,
                                private val recipeList: List<Recipe>,
                                private val listener: OnItemClickListener
)
    : RecyclerView.Adapter<MyRecipeAdapter.MyRecipeViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRecipeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.my_recipe_item,
            parent,false)
        return MyRecipeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyRecipeViewHolder, position: Int) {

        val currentitem = recipeList[position]

        holder.title.text = currentitem.title
        holder.desc.text = currentitem.desc
        Glide.with(context)
            .load(currentitem.image)
            .placeholder(R.mipmap.ic_launcher_round)
            .into(holder.image)

        holder.edit.setOnClickListener {
            Log.e("frag", "edit ${currentitem.title} ${currentitem.id}")
            listener.onEditClick(currentitem.id)

        }
        holder.delete.setOnClickListener {
            Log.e("frag", "delete ${currentitem.title} ${currentitem.id}")
            MaterialAlertDialogBuilder(context,
                com.google.android.material.R.style.Animation_AppCompat_Dialog)
                .setTitle("Delete recipe")
                .setMessage("Once deleted recipe cannot be recovered")
                .setNegativeButton("Cancel") { dialog, which ->
                    // do nothing
                    Log.e("frag", "delete negative ${currentitem.title} ${currentitem.id}")
                }
                .setPositiveButton("Delete") { dialog, which ->
                    //send file id to fragment
                    listener.onDeleteClick(currentitem.id)
                    Log.e("frag", "delete positive ${currentitem.title} ${currentitem.id}")
                }
                .show()

        }
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    class MyRecipeViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val title : TextView = itemView.findViewById(R.id.tvMyRecipeTitle)
        val desc : TextView = itemView.findViewById(R.id.tvMyRecipeDesc)
        val image : ImageView = itemView.findViewById(R.id.imageMyRecipe)
        val edit : Button = itemView.findViewById(R.id.btnMyRecipeEdit)
        val delete : Button = itemView.findViewById(R.id.btnMyRecipeDelete)
    }
    interface OnItemClickListener {
        fun onDeleteClick(recipeId: String)
        fun onEditClick(recipeId: String)
    }

}