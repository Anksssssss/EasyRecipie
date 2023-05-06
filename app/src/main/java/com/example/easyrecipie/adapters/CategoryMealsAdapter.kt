package com.example.easyrecipie.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyrecipie.databinding.MealItemBinding
import com.example.easyrecipie.models.MealsByCategory

class CategoryMealsAdapter:RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewHolder>() {

    inner class CategoryMealsViewHolder(var binding : MealItemBinding) :RecyclerView.ViewHolder(binding.root)

    private var mealList = ArrayList<MealsByCategory>()
    lateinit var onItemClick:((MealsByCategory)->Unit)

    fun setMealsList(mealsList : List<MealsByCategory>){
        this.mealList = mealsList as ArrayList<MealsByCategory>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewHolder {
        return CategoryMealsViewHolder(
            MealItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    override fun onBindViewHolder(holder: CategoryMealsViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.imgMeal)

        holder.binding.tvMealName.text = mealList[position].strMeal

        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealList[position])
        }
    }
}