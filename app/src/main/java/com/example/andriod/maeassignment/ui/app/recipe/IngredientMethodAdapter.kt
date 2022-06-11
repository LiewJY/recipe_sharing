package com.example.andriod.maeassignment.ui.app.recipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.andriod.maeassignment.R

class IngredientMethodAdapter(
                    private val recipeList: ArrayList<String>)
    : RecyclerView.Adapter<IngredientMethodAdapter.IngredientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.ingredient_method_item,
            parent, false
        )
        return IngredientViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val currentitem = recipeList[position]

        holder.field.text = currentitem

        holder.itemView.setOnClickListener(View.OnClickListener {
        })
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    inner class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    val field: TextView = itemView.findViewById(R.id.tvIngMetItem)
}


}