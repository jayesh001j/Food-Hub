package com.example.foodhub

import AuthHelper.Companion.authHelper
import Restaurants_Model
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food_app.Adapter.Restaurants_Adapter
import com.example.food_app.Adapter.Salad_Adapter
import com.example.food_app.Adapter.Salad_model
import com.example.foodhub.databinding.ActivityHomeBinding
import com.example.my_app.helper.FireStoreHelper.Companion.fireStoreHelper

class Home_Activity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val image = arrayOf(R.drawable.sample_food_image, R.drawable.second_food)
    private val rating = arrayOf(R.drawable.rating, R.drawable.second_rating)
    private val brandName = arrayOf("McDonald's", "Starbuck")

    private val saladImage = arrayOf(R.drawable.your_image, R.drawable.your_image2)
    private val price = arrayOf(R.drawable.price_background, R.drawable.price_background2)
    private val like = arrayOf(R.drawable.hart, R.drawable.hart2)
    private val saladName = arrayOf("McDonald's", "Starbuck")

    private val quotesList = ArrayList<Restaurants_Model>()
    private val saladList = ArrayList<Salad_model>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Setting up the click listener for the menu button
        binding.menu.setOnClickListener {
            // Open the navigation drawer
            binding.main.openDrawer(GravityCompat.START)
        }


        // Adding data to quotesList
        for (i in image.indices) {
            val restaurant = Restaurants_Model(image[i], rating[i], brandName[i])
            quotesList.add(restaurant)
        }

        val quotesListAdapter = Restaurants_Adapter(this, quotesList)
        val layoutManagerFeatured = GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL, false)
        binding.rvData.adapter = quotesListAdapter
        binding.rvData.layoutManager = layoutManagerFeatured

        // Adding data to saladList
        for (i in saladImage.indices) {
            val salad = Salad_model(saladImage[i], price[i], like[i], saladName[i])
            saladList.add(salad)
        }

        val saladAdapter = Salad_Adapter(saladList)
        val layoutManagerPopular = GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL, false)
        binding.PopularRecyclerView.adapter = saladAdapter
        binding.PopularRecyclerView.layoutManager = layoutManagerPopular

        fireStoreHelper.fetchTodoData(binding.rvData)


        binding.imageView.setOnClickListener{
            authHelper.logOut()
            googleClient.signOut()
//            Firebase.auth.signOut()
            val intent = Intent(this,Login_Activity::class.java)
            startActivity(intent)
            finish()
        }

        binding.userNameId.text = authHelper.auth.currentUser?.displayName
        binding.emailId.text = authHelper.auth.currentUser?.email



    }
}


