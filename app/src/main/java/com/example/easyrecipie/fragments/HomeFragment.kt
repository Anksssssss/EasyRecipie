package com.example.easyrecipie.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.easyrecipie.R
import com.example.easyrecipie.activities.CategoryMealsActivity
import com.example.easyrecipie.activities.MainActivity
import com.example.easyrecipie.activities.MealActivity
import com.example.easyrecipie.adapters.CategoriesAdapter
import com.example.easyrecipie.adapters.MostPopularAdapter
import com.example.easyrecipie.databinding.FragmentHomeBinding
import com.example.easyrecipie.fragments.bottomsheet.MealBottomSheetFragment
import com.example.easyrecipie.models.Meal
import com.example.easyrecipie.viewModel.HomeViewModel


class HomeFragment : Fragment(R.layout.fragment_home) {

    private var binding: FragmentHomeBinding? = null
    private lateinit var viewModel: HomeViewModel
    private lateinit var randomMeal : Meal
    private lateinit var popularItemsAdapter : MostPopularAdapter
    private lateinit var categoriesAdapter : CategoriesAdapter

    companion object{
        const val MEAL_ID = "com.example.easyrecipie.fragments.idMeal"
        const val MEAL_NAME = "com.example.easyrecipie.fragments.nameMeal"
        const val MEAL_THUMB = "com.example.easyrecipie.fragments.thumbMeal"
        const val CATEGORY_NAME = "com.example.easyrecipie.fragments.categoryMeals"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        popularItemsAdapter = MostPopularAdapter()
        categoriesAdapter = CategoriesAdapter()
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

        preparePopularItemsRecyclerView()
        prepareCategoryListRecyclerView()

        viewModel.getRandomMeal()
        observeRandomMeal()

        onRandomMealClick()

        viewModel.getPopularItems("Seafood")
        observePopularMeal()

        onPopularItemClick()

        viewModel.getCategories()
        observeCategoryList()
        onCategoryClick()

        onPopularItemLongClick()

       // onSearchEditTextClick()

    }

    private fun onSearchEditTextClick() {
        binding!!.tvSearch.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
            FragmentManager.POP_BACK_STACK_INCLUSIVE
            findNavController().navigate(action)
        }
    }

    private fun onPopularItemLongClick() {
        popularItemsAdapter.onLongItemClick = { meal->
            val mealBottomSheetFragment = MealBottomSheetFragment.newInstance(meal.idMeal)
            mealBottomSheetFragment.show(childFragmentManager,"Meal Info")
        }
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = { category ->
            val intent = Intent(activity,CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoryListRecyclerView(){
        binding!!.recViewCard.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter = categoriesAdapter
        }
    }


    private fun observeCategoryList() {
        viewModel.observeCategoryListLiveData().observe(viewLifecycleOwner, Observer { categories->
            categoriesAdapter.setCategoryList(categories)
        })
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.OnItemClick = {meal->
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding!!.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter = popularItemsAdapter
        }
    }

    private fun observePopularMeal() {
        viewModel.observePopularItemsLiveData().observe(viewLifecycleOwner
        ) { mealList ->
                popularItemsAdapter.setMeals(mealList)

        }
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
        viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner, object : Observer<Meal>{
            override fun onChanged(value: Meal) {
                Glide.with(this@HomeFragment)
                    .load(value.strMealThumb)
                    .into(binding!!.imgRandomMeal)

                randomMeal = value
            }

        })

    }

}