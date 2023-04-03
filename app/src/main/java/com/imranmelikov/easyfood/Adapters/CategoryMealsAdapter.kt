package com.imranmelikov.easyfood.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.imranmelikov.easyfood.databinding.MealItemBinding
import com.imranmelikov.easyfood.pojo.Category
import com.imranmelikov.easyfood.pojo.MealsByCategory

class CategoryMealsAdapter :RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewHolder>(){
   inner class CategoryMealsViewHolder(var binding:MealItemBinding):RecyclerView.ViewHolder(binding.root)
    var mealarraylistlist=ArrayList<MealsByCategory>()

    fun setmeallist(meallist:List<MealsByCategory>){
        this.mealarraylistlist=meallist as ArrayList<MealsByCategory>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewHolder {
        var binding=MealItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoryMealsViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return mealarraylistlist.size
    }

    override fun onBindViewHolder(holder: CategoryMealsViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealarraylistlist.get(position).strMealThumb)
            .into(holder.binding.imgMeal)
        holder.binding.tvMealName.text=mealarraylistlist.get(position).strMeal
    }
}