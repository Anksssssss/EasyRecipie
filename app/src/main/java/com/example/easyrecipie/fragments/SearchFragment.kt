package com.example.easyrecipie.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.easyrecipie.R
import com.example.easyrecipie.activities.MainActivity
import com.example.easyrecipie.activities.MealActivity
import com.example.easyrecipie.adapters.SearchAdapter
import com.example.easyrecipie.databinding.FragmentSearchBinding
import com.example.easyrecipie.models.Meal
import com.example.easyrecipie.models.MealList
import com.example.easyrecipie.viewModel.HomeViewModel


class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var binding : FragmentSearchBinding
    private lateinit var viewModel : HomeViewModel
    private lateinit var searchItemAdapter :SearchAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        searchItemAdapter = SearchAdapter()
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()

        if(binding.etSearchFragment.text.toString()!=null) {
            binding.searchButton.setOnClickListener {
                viewModel.getMealBySearch(binding.etSearchFragment.text.toString())
                observeSearchMeal()
            }
        }else{
            Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show()
        }

        onItemClick()

    }

    private fun onItemClick() {
        searchItemAdapter.onItemClick = { meal->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(HomeFragment.MEAL_ID,meal.idMeal)
            intent.putExtra(HomeFragment.MEAL_NAME,meal.strMeal)
            intent.putExtra(HomeFragment.MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeSearchMeal() {
        viewModel.observeMealsBySearchLiveData().observe(viewLifecycleOwner, Observer { meal->
            searchItemAdapter.differ.submitList(meal)
            if(meal==null){
                Toast.makeText(context,"NotFound",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun prepareRecyclerView() {
        binding.rvSearchFragment.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            adapter = searchItemAdapter
        }
    }


}