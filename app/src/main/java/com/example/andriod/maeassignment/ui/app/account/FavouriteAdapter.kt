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

class FavouriteAdapter (private val context: Context,
                        private val recipeList: List<Recipe>,
                        private val listener: OnItemClickListener
)
    : RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.favourite_item,
            parent, false
        )
        return FavouriteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {

        val currentitem = recipeList[position]

        holder.title.text = currentitem.title
        holder.desc.text = currentitem.desc
        holder.author.text = currentitem.name

        Glide.with(context)
            .load(currentitem.image)
            .placeholder(R.mipmap.ic_launcher_round)
            .into(holder.image)

        holder.itemView.setOnClickListener(View.OnClickListener {
            Log.e("frag", "click on ${currentitem.title} ${currentitem.id}")
            listener.onItemClick(currentitem.id)
        })

        holder.remove.setOnClickListener {
            Log.e("frag", "remove ${currentitem.title} ${currentitem.id}")
            MaterialAlertDialogBuilder(
                context,
                com.google.android.material.R.style.Animation_AppCompat_Dialog
            )
                .setTitle("Remove recipe")
                .setMessage("Are you sure you want to remove recipe from favourite")
                .setNegativeButton("Cancel") { dialog, which ->
                    // do nothing
                    Log.e("frag", "remove negative ${currentitem.title} ${currentitem.id}")
                }
                .setPositiveButton("Remove") { dialog, which ->
                    //send file id to fragment
                    listener.onRemoveClick(currentitem.id)
                    Log.e("frag", "remove positive ${currentitem.title} ${currentitem.id}")
                }
                .show()

        }
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    class FavouriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvFavouriteTitle)
        val desc: TextView = itemView.findViewById(R.id.tvFavouriteDesc)
        val author: TextView = itemView.findViewById(R.id.tvFavouriteAuthor)
        val image: ImageView = itemView.findViewById(R.id.imageFavouriteRecipe)
        val remove: Button = itemView.findViewById(R.id.btnFavouriteRemove)
    }

    interface OnItemClickListener {
        fun onRemoveClick(recipeId: String)
        fun onItemClick(id: String)
    }
}
