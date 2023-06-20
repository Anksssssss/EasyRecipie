package com.example.easyrecipie.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.easyrecipie.R
import com.example.easyrecipie.database.MealDatabase
import com.example.easyrecipie.databinding.ActivityMainBinding
import com.example.easyrecipie.viewModel.HomeViewModel
import com.example.easyrecipie.viewModel.HomeViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val viewModel : HomeViewModel by lazy{
        val mealDatabase = MealDatabase.getInstance(this)
        val homeViewModelFactory = HomeViewModelFactory(mealDatabase)
        ViewModelProvider(this,homeViewModelFactory)[HomeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = Navigation.findNavController(this, R.id.host_fragment)
        NavigationUI.setupWithNavController(binding.btmNav,navController)

    }

    override fun onBackPressed() {
        val navController = Navigation.findNavController(this, R.id.host_fragment)
        if (navController.currentDestination?.id == R.id.homeFragment) {
            super.onBackPressed() // If the current destination is the homeFragment, let the system handle the back press
        } else {
            navController.popBackStack() // Otherwise, navigate back to the previous fragment
        }
    }
}