package com.example.easyrecipie.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.easyrecipie.models.MealsByCategory

class MyDiffUtil (
    private var oldList : List<MealsByCategory>,
    private var newList : List<MealsByCategory>
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