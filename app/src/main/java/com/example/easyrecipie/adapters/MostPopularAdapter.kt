package com.example.easyrecipie.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyrecipie.databinding.PopularItemBinding
import com.example.easyrecipie.models.MealsByCategory

class MostPopularAdapter :RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {

    class PopularMealViewHolder(val binding:PopularItemBinding):RecyclerView.ViewHolder(binding.root)

    private var oldMealsList = emptyList<MealsByCategory>()

    lateinit var OnItemClick : ((MealsByCategory)->Unit)

    var onLongItemClick : ((MealsByCategory)->Unit)?=null

    fun setMeals(newMealsList : List<MealsByCategory>){
        val diffUtil = MyDiffUtil(oldMealsList,newMealsList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        oldMealsList=newMealsList
        diffResults.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(PopularItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
      return  oldMealsList.size
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(oldMealsList[position].strMealThumb)
            .into(holder.binding.imgPopularMealItem)

        holder.itemView.setOnClickListener {
            OnItemClick.invoke(oldMealsList[position])
        }

        holder.itemView.setOnLongClickListener {
            onLongItemClick?.invoke(oldMealsList[position])
            true
        }
    }
}