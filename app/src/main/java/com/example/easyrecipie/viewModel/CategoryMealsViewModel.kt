package com.example.easyrecipie.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyrecipie.api.RetrofitInstance
import com.example.easyrecipie.models.MealsByCategory
import com.example.easyrecipie.models.MealsByCategoryList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel:ViewModel() {

    val categoryMealsLiveData = MutableLiveData<List<MealsByCategory>>()

    fun getMealsByCategory(categoryName:String){
        RetrofitInstance.api.getMealsByCategory(categoryName).enqueue(object :Callback<MealsByCategoryList>{
            override fun onResponse(
                call: Call<MealsByCategoryList>,
                response: Response<MealsByCategoryList>
            ) {
                response?.let {
                    categoryMealsLiveData.postValue(it.body()!!.meals)
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.d("CategoryMeals Activity" , t.message.toString())
            }
        })
    }

    fun observeMealsLiveData(): MutableLiveData<List<MealsByCategory>> {
        return categoryMealsLiveData
    }


}