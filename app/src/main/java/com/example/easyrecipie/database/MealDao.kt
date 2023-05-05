package com.example.easyrecipie.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.easyrecipie.models.Meal

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: Meal)

    @Delete
    suspend fun delete(meal:Meal)

    @Query("SELECT * FROM mealInformation")
    fun getAllMeals() : LiveData<List<Meal>>
}