package com.imranmelikov.easyfood.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.imranmelikov.easyfood.databinding.PopularItemsBinding
import com.imranmelikov.easyfood.pojo.MealsByCategory

class MostPopularAdapter():RecyclerView.Adapter<MostPopularAdapter.MostPopularViewHolder>() {
    lateinit var onItemClick:((MealsByCategory)->Unit)
    var onLongItemClick:((MealsByCategory)->Unit)?=null
    var mealarraylist=ArrayList<MealsByCategory>()
    fun setMeal(meallist:ArrayList<MealsByCategory>){
        this.mealarraylist=meallist
        notifyDataSetChanged()
    }

    class MostPopularViewHolder(var binding:PopularItemsBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MostPopularViewHolder {
       return MostPopularViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
       return mealarraylist.size
    }

    override fun onBindViewHolder(holder: MostPopularViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealarraylist.get(position).strMealThumb)
            .into(holder.binding.imgPopularMeal)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealarraylist.get(position))

            holder.itemView.setOnLongClickListener {
                onLongItemClick?.invoke(mealarraylist.get(position))
                true
            }
        }
    }
}