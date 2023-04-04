package com.imranmelikov.easyfood.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.imranmelikov.easyfood.Adapters.CategoryMealsAdapter
import com.imranmelikov.easyfood.Fragments.HomeFragment
import com.imranmelikov.easyfood.R
import com.imranmelikov.easyfood.databinding.ActivityCategoryMealBinding
import com.imranmelikov.easyfood.viewModel.CategoryMealsViewModel

class CategoryMealActivity : AppCompatActivity() {
    private lateinit var binding:ActivityCategoryMealBinding
    lateinit var categoryMealsViewModel: CategoryMealsViewModel
    lateinit var categoryMealsAdapter: CategoryMealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCategoryMealBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        prepareRecyclerview()
        categoryMealsViewModel=ViewModelProviders.of(this).get(CategoryMealsViewModel::class.java)
        categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)
        categoryMealsViewModel.observeMealsLiveData().observe(this, Observer {
            binding.tvCategoryCount.text=it.size.toString()
            categoryMealsAdapter.setmeallist(it)
        })
    }

    private fun prepareRecyclerview() {
        categoryMealsAdapter=CategoryMealsAdapter()
        binding.mealRecyclerview.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=categoryMealsAdapter
        }
    }
}