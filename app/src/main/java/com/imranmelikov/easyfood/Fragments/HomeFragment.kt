package com.imranmelikov.easyfood.Fragments

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.imranmelikov.easyfood.Activities.CategoryMealActivity
import com.imranmelikov.easyfood.Activities.MainActivity
import com.imranmelikov.easyfood.Activities.MealActivity
import com.imranmelikov.easyfood.Adapters.CategoriesAdapter
import com.imranmelikov.easyfood.Adapters.MostPopularAdapter
import com.imranmelikov.easyfood.Fragments.BottomSheet.BottomSheetFragment
import com.imranmelikov.easyfood.databinding.FragmentHomeBinding
import com.imranmelikov.easyfood.pojo.MealsByCategory
import com.imranmelikov.easyfood.pojo.Meal
import com.imranmelikov.easyfood.viewModel.HomeViewModel
class HomeFragment :androidx.fragment.app.Fragment()  {

    private lateinit var binding:FragmentHomeBinding
    private lateinit var viewModel:HomeViewModel
    private lateinit var randomMeal:Meal
    private lateinit var popularitemsadapter:MostPopularAdapter
    private lateinit var categoriesadapter:CategoriesAdapter
    companion object{
        const val HEAD_ID="com.imranmelikov.easyfood.Fragments.idMeal"
        const val HEAD_NAME="com.imranmelikov.easyfood.Fragments.nameMeal"
        const val HEAD_THUMB="com.imranmelikov.easyfood.Fragments.thumbMeal"
        const val CATEGORY_NAME="com.imranmelikov.easyfood.Fragments.categoryName"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=(activity as MainActivity).viewModel
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

        viewModel.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()

        viewModel.getPopularItems()
        observePopularItemsLiveData()

        onPopularItemClick()

        prepareCategoriesRecyclerview()

        viewModel.getCategories()
        observerCategoryLiveData()

        onCategoryClick()

        onPopulerItemLongClick()

        onSearchIconClick()
    }

    private fun onSearchIconClick() {
        binding.imgSearch.setOnClickListener {
            findNavController().navigate(com.imranmelikov.easyfood.R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun onPopulerItemLongClick() {
        popularitemsadapter.onLongItemClick = {
            val mealBottomSheetFragment=BottomSheetFragment.newInstance(it.idMeal)
            mealBottomSheetFragment.show(childFragmentManager,"Meal Info")
        }
    }

    private fun onCategoryClick() {
        categoriesadapter.onItemClick={
            val intent=Intent(activity,CategoryMealActivity::class.java)
            intent.putExtra(CATEGORY_NAME,it.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRecyclerview() {
        categoriesadapter=CategoriesAdapter()
        binding.recyclerView.apply {
            layoutManager=GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter=categoriesadapter
        }
    }

    private fun observerCategoryLiveData() {
        viewModel.observerCategoryLiveData().observe(viewLifecycleOwner, Observer {
              categoriesadapter.setCategorylist(it)
        })
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
        viewModel.observePopularItemsLiveData().observe(viewLifecycleOwner,object :Observer<List<MealsByCategory>>{
            override fun onChanged(value: List<MealsByCategory>) {
                popularitemsadapter.setMeal(meallist = value as ArrayList<MealsByCategory>)
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
        viewModel.observerandomMealLiveData().observe(viewLifecycleOwner,object :Observer<Meal>{
            override fun onChanged(value: Meal) {
                Glide.with(this@HomeFragment)
                    .load(value!!.strMealThumb)
                    .into(binding.imgRandomMeal)
                this@HomeFragment.randomMeal=value
            }

        })
    }


}
