package com.example.easyrecipie.api

import com.example.easyrecipie.models.CategoryList
import com.example.easyrecipie.models.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal():Call<MealList>

    @GET("lookup.php")
    fun getMealDetailsById(@Query("i") id:String):Call<MealList>

    @GET("filter.php?")
    fun getPopularItems(@Query("c") categoryName : String) : Call<CategoryList>
}