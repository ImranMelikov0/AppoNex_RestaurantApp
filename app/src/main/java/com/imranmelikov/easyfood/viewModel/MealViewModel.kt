package com.imranmelikov.easyfood.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imranmelikov.easyfood.Activities.MainActivity
import com.imranmelikov.easyfood.pojo.Meal
import com.imranmelikov.easyfood.pojo.Meallist
import com.imranmelikov.easyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MealViewModel:ViewModel() {
    private var mealdetailsLiveData=MutableLiveData<Meal>()

    fun getMealDetail(id:String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object:retrofit2.Callback<Meallist>{
            override fun onResponse(call: Call<Meallist>, response: Response<Meallist>) {
                if(response.body()!=null){
                    mealdetailsLiveData.value=response.body()!!.meals[0]
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<Meallist>, t: Throwable) {
                Log.d("MealActivity",t.message.toString())
            }

        })

    }
    fun observerMealDetailsLiveData():LiveData<Meal>{
        return mealdetailsLiveData
    }
}