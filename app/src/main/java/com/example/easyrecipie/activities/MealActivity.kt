package com.example.easyrecipie.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.easyrecipie.R
import com.example.easyrecipie.database.MealDatabase
import com.example.easyrecipie.databinding.ActivityMainBinding
import com.example.easyrecipie.databinding.ActivityMealBinding
import com.example.easyrecipie.databinding.FragmentHomeBinding
import com.example.easyrecipie.fragments.HomeFragment
import com.example.easyrecipie.models.Meal
import com.example.easyrecipie.models.MealList
import com.example.easyrecipie.viewModel.HomeViewModel
import com.example.easyrecipie.viewModel.MealViewModel
import com.example.easyrecipie.viewModel.MealViewModelFactory

class MealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding

    private lateinit var mealName: String
    private lateinit var mealId: String
    private lateinit var mealThumb: String
    private lateinit var youtubeLink :String

    private lateinit var mealToSave : Meal

    private lateinit var mealMvvm :MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDatabase)
        mealMvvm = ViewModelProvider(this,viewModelFactory).get(MealViewModel::class.java)


        getMealInformationFromIntent()
        setInformationInViews()

        loadingCase()

        mealMvvm.getMealDetail(mealId)
        observeMealDetailsByIdLiveData()

        onYoutubeImageClick()

        onFavoriteClick()

    }

    private fun onFavoriteClick() {
        binding.btnAddToFav.setOnClickListener {
            mealToSave?.let {
                mealMvvm.insertMeal(it)
                Toast.makeText(this, "Meal Saved to Favorites", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onYoutubeImageClick() {
        binding.imgYouTube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private fun observeMealDetailsByIdLiveData() {
        mealMvvm.observeMealDetailsLiveData().observe(this@MealActivity, object : Observer<Meal>{
            override fun onChanged(value: Meal) {
                onResponseCase()
                val meal = value
                mealToSave = meal
                binding.tvCategory.text = "Category : ${meal.strCategory}"
                binding.tvArea.text = "Area : ${meal.strArea}"
                binding.tvViewInstructions.text = meal.strInstructions
                youtubeLink = meal.strYoutube!!
            }
        })
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.btnAddToFav.visibility = View.INVISIBLE
        binding.tvViewInstructions.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.imgYouTube.visibility = View.INVISIBLE
    }

    private fun onResponseCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnAddToFav.visibility = View.VISIBLE
        binding.tvViewInstructions.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.imgYouTube.visibility = View.VISIBLE
    }

}