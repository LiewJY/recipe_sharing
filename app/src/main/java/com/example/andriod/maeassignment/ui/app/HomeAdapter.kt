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

class HomeAdapter(private val context: Context,
                  private val recipeList: List<Recipe>,
                  private val listener: OnItemClickListener)
    : RecyclerView.Adapter<HomeAdapter.RecipesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recipes_item,
            parent,false)
        return RecipesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {

        val currentitem = recipeList[position]

        holder.title.text = currentitem.title
        holder.user.text = currentitem.userid
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

     class RecipesViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val title : TextView = itemView.findViewById(R.id.tvHomeRecipeTitle)
        val user : TextView = itemView.findViewById(R.id.tvHomeName)
        val desc : TextView = itemView.findViewById(R.id.tvHomeDesc)
        val image : ImageView = itemView.findViewById(R.id.imageRecipes)
    }
    interface OnItemClickListener {
        fun onItemClick(recipeId: String)
    }
}