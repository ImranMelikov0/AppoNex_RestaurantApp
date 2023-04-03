package com.imranmelikov.easyfood.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imranmelikov.easyfood.pojo.MealsByCategory
import com.imranmelikov.easyfood.pojo.MealsByCategorylist
import com.imranmelikov.easyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel:ViewModel() {
    val mealsLiveData=MutableLiveData<List<MealsByCategory>>()
    fun getMealsByCategory(categoryName:String){
        RetrofitInstance.api.getMealsByCategory(categoryName).enqueue(object :Callback<MealsByCategorylist>{
            override fun onResponse(
                call: Call<MealsByCategorylist>,
                response: Response<MealsByCategorylist>
            ) {
                response.body()?.let {
                    mealsLiveData.postValue(it.meals)
                }
            }

            override fun onFailure(call: Call<MealsByCategorylist>, t: Throwable) {
                Log.e("CategoryMealsViewModel",t.message.toString())
            }

        })
    }
    fun observeMealsLiveData():LiveData<List<MealsByCategory>>{
        return mealsLiveData
    }
}