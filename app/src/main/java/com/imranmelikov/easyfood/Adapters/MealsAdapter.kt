package com.imranmelikov.easyfood.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.imranmelikov.easyfood.databinding.MealItemBinding
import com.imranmelikov.easyfood.pojo.Meal

class MealsAdapter:RecyclerView.Adapter<MealsAdapter.FavoritesMealsViewHolder>() {
   inner class FavoritesMealsViewHolder(var binding: MealItemBinding) :
      RecyclerView.ViewHolder(binding.root)

   private val diffUtil = object : DiffUtil.ItemCallback<Meal>() {
      override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
         return oldItem.idMeal == newItem.idMeal
      }

      override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
         return oldItem == newItem
      }
   }
   val differ = AsyncListDiffer(this, diffUtil)
   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesMealsViewHolder {
      val binding=MealItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
      return FavoritesMealsViewHolder(binding)
   }

   override fun getItemCount(): Int {
      return differ.currentList.size
   }

   override fun onBindViewHolder(holder: FavoritesMealsViewHolder, position: Int) {
      val meal=differ.currentList.get(position)
      Glide.with(holder.itemView)
         .load(meal.strMealThumb)
         .into(holder.binding.imgMeal)
      holder.binding.tvMealName.text=meal.strMeal
   }
}