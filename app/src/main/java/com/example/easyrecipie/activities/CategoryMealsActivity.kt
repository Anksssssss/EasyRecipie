package com.example.easyrecipie.activities

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.easyrecipie.R
import com.example.easyrecipie.adapters.CategoryMealsAdapter
import com.example.easyrecipie.databinding.ActivityCategoryMealsBinding
import com.example.easyrecipie.fragments.HomeFragment
import com.example.easyrecipie.viewModel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {

    lateinit var binding: ActivityCategoryMealsBinding
    lateinit var categoryMvvm:CategoryMealsViewModel
    lateinit var categoryMealsAdapter:CategoryMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryMvvm = ViewModelProvider(this).get(CategoryMealsViewModel::class.java)
        categoryMealsAdapter  = CategoryMealsAdapter()

        prepareRecyclerView()

        categoryMvvm.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)
        categoryMvvm.observeMealsLiveData().observe(this, Observer { mealsList->
            binding.tvCategoryCount.text = "Number of Meals :${ mealsList.size.toString() }"
            categoryMealsAdapter.setMealsList(mealsList)
        })
    }

    private fun prepareRecyclerView() {
        binding.rvMeals.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = categoryMealsAdapter
        }
    }
}

