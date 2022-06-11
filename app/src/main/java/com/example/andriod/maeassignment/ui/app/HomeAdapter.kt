package com.example.andriod.maeassignment.ui.app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.andriod.maeassignment.R
import com.example.andriod.maeassignment.models.Recipe
import java.util.*

class HomeAdapter(private val context: Context,
                  private val recipeList: ArrayList<Recipe>,
                  private val listener: OnItemClickListener?
)
    : RecyclerView.Adapter<HomeAdapter.RecipesViewHolder>(), Filterable{
    var recipeFilterList = ArrayList<Recipe>()
    init {
        recipeFilterList = recipeList as ArrayList<Recipe>
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recipes_item,
            parent,false)
        return RecipesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {

        val currentitem = recipeFilterList[position]

        holder.title.text = currentitem.title
        holder.user.text = currentitem.name
        holder.desc.text = currentitem.desc
        Glide.with(context)
            .load(currentitem.image)
            .placeholder(R.drawable.logo)
            .into(holder.image)

        holder.itemView.setOnClickListener(View.OnClickListener {
            listener?.onItemClick(currentitem.id)
        })
    }

    override fun getItemCount(): Int {
        return recipeFilterList.size
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

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val charSearch = p0.toString()
                if(charSearch.isEmpty())
                {
                    recipeFilterList.addAll(recipeList)
                }else
                {
                    val resultList = ArrayList<Recipe>()
                    val filterPattern: String = p0.toString().lowercase(Locale.getDefault())
                    for (item in recipeList) {
                        if (item.title.lowercase(Locale.getDefault()).contains(filterPattern)) {
                            resultList.add(item)
                        }
                    }
                    recipeFilterList = resultList
                }
                val results = FilterResults()
                results.values = recipeFilterList
                return  results
            }

            override fun publishResults(p0: CharSequence?, p1: Filter.FilterResults?) {
                recipeFilterList = p1?.values as ArrayList<Recipe>
                notifyDataSetChanged()
            }
        }


    }
}