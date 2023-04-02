package com.imranmelikov.easyfood.retrofit

import com.imranmelikov.easyfood.pojo.CategoryList
import com.imranmelikov.easyfood.pojo.Meallist
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Mealapi {
    @GET("random.php")
    fun getRandomMeal():Call<Meallist>
    @GET("lookup.php?")
    fun getMealDetails(@Query("i")id:String):Call<Meallist>
    @GET("filter.php?")
    fun getPopularItems(@Query("c")categoryName:String):Call<CategoryList>
}