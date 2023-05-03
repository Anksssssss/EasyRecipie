package com.example.easyrecipie.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyrecipie.api.RetrofitInstance
import com.example.easyrecipie.models.Meal
import com.example.easyrecipie.models.MealList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel:ViewModel() {

    private var randomMealLiveData = MutableLiveData<Meal>()

    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeal().enqueue(object:Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body()!=null){
                    val randomMeal : Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("Home Fragment",t.message.toString())
            }
        })
    }

    fun observeRandomMealLiveData():LiveData<Meal>{
        return randomMealLiveData
    }

}