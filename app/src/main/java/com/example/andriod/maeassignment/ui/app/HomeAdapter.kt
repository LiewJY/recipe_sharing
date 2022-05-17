package com.example.andriod.maeassignment.ui.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.andriod.maeassignment.R
import com.example.andriod.maeassignment.models.Recipe

class HomeAdapter(private val recipeList: List<Recipe>) : RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recipes_item,
            parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = recipeList[position]

        holder.title.text = currentitem.title
        holder.user.text = currentitem.userid
        holder.desc.text = currentitem.desc

    }

    override fun getItemCount(): Int {

        return recipeList.size
    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val title : TextView = itemView.findViewById(R.id.tvHomeRecipeTitle)
        val user : TextView = itemView.findViewById(R.id.tvHomeName)
        val desc : TextView = itemView.findViewById(R.id.tvHomeDesc)

    }
}