package com.imranmelikov.easyfood.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imranmelikov.easyfood.Db.MealDatabase
import com.imranmelikov.easyfood.pojo.*
import com.imranmelikov.easyfood.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(
    var mealDatabase: MealDatabase
): ViewModel() {
    private val randomMealLivedata=MutableLiveData<Meal>()
    private val popularItemsLiveData=MutableLiveData<List<MealsByCategory>>()
    private val categoriesLiveData=MutableLiveData<List<Category>>()
    private var favoritesMealsLiveData=mealDatabase.mealDao().getAllMeals()
    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<Meallist> {
            override fun onResponse(call: Call<Meallist>, response: Response<Meallist>) {
                if(response.body()!=null){
                    val randomMeal: Meal =response.body()!!.meals[0]
                    randomMealLivedata.value=randomMeal
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<Meallist>, t: Throwable) {
                Log.d("HomeFragment",t.message.toString())
            }

        })
    }

    fun getPopularItems(){
        RetrofitInstance.api.getPopularItems("Seafood").enqueue(object :Callback<MealsByCategorylist>{
            override fun onResponse(call: Call<MealsByCategorylist>, response: Response<MealsByCategorylist>) {
                if(response.body()!=null){
                    popularItemsLiveData.value= response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<MealsByCategorylist>, t: Throwable) {
                Log.d("HomeFragment",t.message.toString())
            }

        })
    }
    fun getCategories(){
        RetrofitInstance.api.getCategories().enqueue(object :Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
               response.body()?.let {
                   categoriesLiveData.postValue(it.categories)
               }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.e("HomeViewModel",t.message.toString())
            }

        })
    }

    fun deleteMeal(meal:Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }
    fun innerMeal(meal:Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }
    fun observePopularItemsLiveData(): MutableLiveData<List<MealsByCategory>> {
        return popularItemsLiveData
    }

    fun observerandomMealLiveData():LiveData<Meal>{
        return randomMealLivedata
    }
    fun observerCategoryLiveData(): LiveData<List<Category>> {
        return categoriesLiveData
    }
    fun observeFavoritesMealsLiveData():LiveData<List<Meal>>{
        return favoritesMealsLiveData
    }
}