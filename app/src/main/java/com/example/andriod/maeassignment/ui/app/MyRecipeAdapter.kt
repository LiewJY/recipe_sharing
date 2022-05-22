package com.example.andriod.maeassignment.ui.app

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.andriod.maeassignment.R
import com.example.andriod.maeassignment.models.Recipe

 class MyRecipeAdapter (private val context: Context,
                                private val recipeList: List<Recipe>,
                                private val listener: OnItemClickListener)
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

        holder.itemView.setOnClickListener(View.OnClickListener {
            Log.e("frag", "test ${currentitem.title} ${currentitem.id}")
            listener.onItemClick(currentitem.id)
        })
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    class MyRecipeViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val title : TextView = itemView.findViewById(R.id.tvMyRecipeTitle)
        val desc : TextView = itemView.findViewById(R.id.tvMyRecipeDesc)
        val image : ImageView = itemView.findViewById(R.id.imageMyRecipe)
    }
    interface OnItemClickListener {
        fun onItemClick(position: String)
    }

}