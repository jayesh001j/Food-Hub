
package com.example.food_app.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodhub.R


class Salad_Adapter(
    private val saladList: List<Salad_model>
) : RecyclerView.Adapter<Salad_Adapter.SaladViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaladViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.popular_tile, parent, false)
        return SaladViewHolder(view)
    }

    override fun onBindViewHolder(holder: SaladViewHolder, position: Int) {
        val salad = saladList[position]
        holder.image.setImageResource(salad.saladImage)
        holder.price.setImageResource(salad.price)
        holder.favoriteIcon.setImageResource(salad.like)
        holder.title.text = salad.saladName
    }

    override fun getItemCount(): Int = saladList.size

    class SaladViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val favoriteIcon: ImageView = view.findViewById(R.id.favorite_icon)
        val price: ImageView = view.findViewById(R.id.price)
        val image: ImageView = view.findViewById(R.id.image)
        val title: TextView = view.findViewById(R.id.title)
    }
}
