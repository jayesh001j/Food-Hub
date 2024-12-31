package com.example.food_app.Adapter

import Restaurants_Model
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodhub.FoodDetails_Activity
import com.example.foodhub.R

class Restaurants_Adapter(
    private val context: Context,
    private val restaurantList: ArrayList<Restaurants_Model>
) : RecyclerView.Adapter<Restaurants_Adapter.RestaurantsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.restaurants_tale, parent, false)
        return RestaurantsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantsViewHolder, position: Int) {
        val restaurant = restaurantList[position]
        holder.restoImage.setImageResource(restaurant.image)
        holder.brandName.text = restaurant.brandName
        holder.ratingImage.setImageResource(restaurant.rating)

        holder.restoImage.setOnClickListener {
            val intent = Intent(context, FoodDetails_Activity::class.java).apply {
                putExtra("Restaurant", restaurant.image)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = restaurantList.size

    class RestaurantsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val restoImage: ImageView = itemView.findViewById(R.id.restoimag)
        val brandName: TextView = itemView.findViewById(R.id.brandname)
        val ratingImage: ImageView = itemView.findViewById(R.id.ratingb)
    }
}
