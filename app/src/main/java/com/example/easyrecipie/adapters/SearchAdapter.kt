package com.example.easyrecipie.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyrecipie.databinding.SearchMealItemBinding
import com.example.easyrecipie.models.Meal
import com.example.easyrecipie.models.MealsByCategory

class SearchAdapter:RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    inner class SearchViewHolder(var binding : SearchMealItemBinding) : RecyclerView.ViewHolder(binding.root)

    lateinit var onItemClick : ((Meal)->Unit)

    private var diffUtil = object : DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffUtil)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(SearchMealItemBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        ))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val meal = differ.currentList[position]
        Glide.with(holder.itemView)
            .load(meal.strMealThumb)
            .into(holder.binding.imgSearchMeal)

        holder.binding.tvSearchFragArea.text = meal.strArea
        holder.binding.tvSearchFragCategory.text = meal.strCategory
        holder.binding.searchFragMealName.text = meal.strMeal

        holder.itemView.setOnClickListener {
            onItemClick.invoke(meal)
        }
    }
}