package com.imranmelikov.easyfood.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.imranmelikov.easyfood.Activities.MealActivity
import com.imranmelikov.easyfood.Adapters.MostPopularAdapter
import com.imranmelikov.easyfood.databinding.FragmentHomeBinding
import com.imranmelikov.easyfood.pojo.CategoryMeal
import com.imranmelikov.easyfood.pojo.Meal
import com.imranmelikov.easyfood.viewModel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding
    private lateinit var homeMvvm:HomeViewModel
    private lateinit var randomMeal:Meal
    private lateinit var popularitemsadapter:MostPopularAdapter
    companion object{
        const val HEAD_ID="com.imranmelikov.easyfood.Fragments.idMeal"
        const val HEAD_NAME="com.imranmelikov.easyfood.Fragments.nameMeal"
        const val HEAD_THUMB="com.imranmelikov.easyfood.Fragments.thumbMeal"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       homeMvvm=ViewModelProviders.of(this)[HomeViewModel::class.java]
        popularitemsadapter= MostPopularAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding=FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepearePopularItemsRecyclerview()

        homeMvvm.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()

        homeMvvm.getPopularItems()
        observePopularItemsLiveData()

        onPopularItemClick()
    }

    private fun onPopularItemClick() {
        popularitemsadapter.onItemClick={
            var intent=Intent(activity,MealActivity::class.java)
            intent.putExtra(HEAD_ID,it.idMeal)
            intent.putExtra(HEAD_NAME,it.strMeal)
            intent.putExtra(HEAD_THUMB,it.strMealThumb)
            startActivity(intent)
        }
    }

    private fun prepearePopularItemsRecyclerview() {
        binding.recViewMealsPopular.apply {
            layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
           adapter= popularitemsadapter
        }
    }

    private fun observePopularItemsLiveData() {
        homeMvvm.observePopularItemsLiveData().observe(viewLifecycleOwner,object :Observer<List<CategoryMeal>>{
            override fun onChanged(value: List<CategoryMeal>) {
                popularitemsadapter.setMeal(meallist = value as ArrayList<CategoryMeal>)
            }

        })
    }

    private fun onRandomMealClick(){
        binding.randomMeal.setOnClickListener {
            val intent=Intent(activity,MealActivity::class.java)
            intent.putExtra(HEAD_ID,randomMeal.idMeal)
            intent.putExtra(HEAD_NAME,randomMeal.strMeal)
            intent.putExtra(HEAD_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {
        homeMvvm.observerandomMealLiveData().observe(viewLifecycleOwner,object :Observer<Meal>{
            override fun onChanged(value: Meal) {
                Glide.with(this@HomeFragment)
                    .load(value!!.strMealThumb)
                    .into(binding.imgRandomMeal)
                this@HomeFragment.randomMeal=value
            }

        })
    }


}
