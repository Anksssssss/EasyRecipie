package com.example.easyrecipie.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.easyrecipie.models.CategoryMeals

class MyDiffUtil (
    private var oldList : List<CategoryMeals>,
    private var newList : List<CategoryMeals>
        ):DiffUtil.Callback(){
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].idMeal == newList[newItemPosition].idMeal
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}