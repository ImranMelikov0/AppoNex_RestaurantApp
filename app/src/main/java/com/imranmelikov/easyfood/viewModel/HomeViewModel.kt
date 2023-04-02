package com.imranmelikov.easyfood.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imranmelikov.easyfood.pojo.CategoryList
import com.imranmelikov.easyfood.pojo.CategoryMeal
import com.imranmelikov.easyfood.pojo.Meal
import com.imranmelikov.easyfood.pojo.Meallist
import com.imranmelikov.easyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(): ViewModel() {
    private val randomMealLivedata=MutableLiveData<Meal>()
    private val popularItemsLiveData=MutableLiveData<CategoryMeal>()
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
        RetrofitInstance.api.getPopularItems("Seafood").enqueue(object :Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if(response.body()!=null){
                    popularItemsLiveData.value= response.body()!!.meals[1]
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("HomeFragment",t.message.toString())
            }

        })
    }
    fun observePopularItemsLiveData():LiveData<CategoryMeal>{
        return popularItemsLiveData
    }

    fun observerandomMealLiveData():LiveData<Meal>{
        return randomMealLivedata
    }
}