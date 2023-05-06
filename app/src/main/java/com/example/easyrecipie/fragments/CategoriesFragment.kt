package com.example.easyrecipie.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.easyrecipie.R
import com.example.easyrecipie.activities.CategoryMealsActivity
import com.example.easyrecipie.activities.MainActivity
import com.example.easyrecipie.adapters.CategoriesAdapter
import com.example.easyrecipie.databinding.FragmentCategoriesBinding
import com.example.easyrecipie.viewModel.HomeViewModel


class CategoriesFragment : Fragment(R.layout.fragment_categories) {

    private lateinit var binding : FragmentCategoriesBinding
    private lateinit var categoriesAdapter : CategoriesAdapter
    private lateinit var viewModel : HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeCategories()
        OnCategoryClick()
    }

    private fun OnCategoryClick() {
        categoriesAdapter.onItemClick = { category->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(HomeFragment.CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }
    }

    private fun observeCategories() {
        viewModel.observeCategoryListLiveData().observe(viewLifecycleOwner, Observer { categories->
            categoriesAdapter.setCategoryList(categories)
        })
    }

    private fun prepareRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.rvCategoriesFragment.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter = categoriesAdapter
        }
    }
}