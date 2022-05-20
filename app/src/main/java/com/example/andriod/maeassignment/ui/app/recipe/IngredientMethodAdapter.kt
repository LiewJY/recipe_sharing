package com.example.andriod.maeassignment.ui.app.recipe

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.andriod.maeassignment.R

class IngredientMethodAdapter(
                    private val recipeList: ArrayList<String>,
                    private val listener: OnItemClickListener)
    : RecyclerView.Adapter<IngredientMethodAdapter.IngredientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.ingredient_method_item,
            parent,false)
        return IngredientViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val currentitem = recipeList[position]

        holder.field.text = currentitem

        holder.itemView.setOnClickListener(View.OnClickListener {
            Log.e("frag", "test $currentitem")
            //listener.onItemClick(currentitem)
        })
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    inner class IngredientViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val field : TextView = itemView.findViewById(R.id.tvIngMetItem)
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }
    interface OnItemClickListener {
        fun onItemClick(position: Int) {
            Log.e("frag", "clicked dddd")

        }
    }
}