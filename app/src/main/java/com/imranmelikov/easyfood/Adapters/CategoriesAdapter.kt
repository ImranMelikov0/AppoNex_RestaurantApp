package com.imranmelikov.easyfood.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.bumptech.glide.Glide
import com.imranmelikov.easyfood.databinding.CategoryItemBinding
import com.imranmelikov.easyfood.pojo.Category
import com.imranmelikov.easyfood.pojo.CategoryList
import com.imranmelikov.easyfood.pojo.MealsByCategorylist

class CategoriesAdapter():RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>(){
    private var categoryarraylist=ArrayList<Category>()
    var onItemClick:((Category) -> Unit)?=null
    fun setCategorylist(categorylist: List<Category>){
        this.categoryarraylist=categorylist as ArrayList<Category>
        notifyDataSetChanged()
    }
    class CategoriesViewHolder(val binding:CategoryItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        var binding=CategoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoriesViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return categoryarraylist.size
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
       Glide.with(holder.itemView)
           .load(categoryarraylist.get(position).strCategoryThumb)
           .into(holder.binding.imgCategory)
        holder.binding.tvCategoryName.text=categoryarraylist.get(position).strCategory
        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(categoryarraylist.get(position))
        }
    }
}