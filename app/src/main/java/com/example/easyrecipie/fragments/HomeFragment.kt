package com.example.easyrecipie.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.easyrecipie.R
import com.example.easyrecipie.activities.MealActivity
import com.example.easyrecipie.databinding.FragmentHomeBinding
import com.example.easyrecipie.models.Meal
import com.example.easyrecipie.viewModel.HomeViewModel


class HomeFragment : Fragment(R.layout.fragment_home) {

    private var binding: FragmentHomeBinding? = null
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var randomMeal : Meal

    companion object{
        const val MEAL_ID = "com.example.easyrecipie.fragments.idMeal"
        const val MEAL_NAME = "com.example.easyrecipie.fragments.nameMeal"
        const val MEAL_THUMB = "com.example.easyrecipie.fragments.thumbMeal"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeMvvm.getRandomMeal()

        observeRandomMeal()

        onRandomMealClick()
    }

    private fun onRandomMealClick() {
        binding!!.randomMealCard.setOnClickListener {
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeRandomMeal() {
        homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner, object : Observer<Meal>{
            override fun onChanged(value: Meal) {
                Glide.with(this@HomeFragment)
                    .load(value.strMealThumb)
                    .into(binding!!.imgRandomMeal)

                randomMeal = value
            }

        })

    }

}