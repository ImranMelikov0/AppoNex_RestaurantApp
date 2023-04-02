package com.imranmelikov.easyfood.Activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.imranmelikov.easyfood.Fragments.HomeFragment
import com.imranmelikov.easyfood.R
import com.imranmelikov.easyfood.databinding.ActivityMealBinding
import com.imranmelikov.easyfood.pojo.Meal
import com.imranmelikov.easyfood.viewModel.MealViewModel

class MealActivity : AppCompatActivity() {
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var binding:ActivityMealBinding
    private lateinit var mealMvvm: MealViewModel
    private lateinit var youtubelink:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMealBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        mealMvvm=ViewModelProviders.of(this)[MealViewModel::class.java]
        getMealInformationfromintent()
        setInformationInView()
        loadimagecase()

        mealMvvm.getMealDetail(mealId)
        observerMealDetailsLiveData()

        onYoutubeimageclick()
    }

    private fun onYoutubeimageclick() {
        binding.imgYoutube.setOnClickListener {
            val intent=Intent(Intent.ACTION_VIEW,Uri.parse(youtubelink))
            startActivity(intent)
        }

    }

    private fun observerMealDetailsLiveData() {
        mealMvvm.observerMealDetailsLiveData().observe(this,object :Observer<Meal>{
            override fun onChanged(value: Meal) {
                onResponseCase()
                val meal=value
                binding.tvCategoryInfo.text=meal.strCategory
                binding.tvAreaInfo.text=meal.strArea
                binding.tvInstructions.text=meal.strInstructions

                youtubelink=meal.strYoutube
            }

        })
    }

    private fun setInformationInView() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)
        binding.collapsingToolbar.title=mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInformationfromintent() {
        val intent=intent
       mealId= intent.getStringExtra(HomeFragment.HEAD_ID)!!
        mealName=intent.getStringExtra(HomeFragment.HEAD_NAME)!!
        mealThumb=intent.getStringExtra(HomeFragment.HEAD_THUMB)!!
    }

    private fun loadimagecase(){
        binding.progressBar.visibility=View.VISIBLE
        binding.btnSave.visibility=View.INVISIBLE
        binding.tvInstructions.visibility=View.INVISIBLE
        binding.tvCategoryInfo.visibility=View.INVISIBLE
        binding.tvAreaInfo.visibility=View.INVISIBLE
        binding.imgYoutube.visibility=View.INVISIBLE
    }
    private fun onResponseCase(){
        binding.progressBar.visibility=View.INVISIBLE
        binding.btnSave.visibility=View.VISIBLE
        binding.tvInstructions.visibility=View.VISIBLE
        binding.tvCategoryInfo.visibility=View.VISIBLE
        binding.tvAreaInfo.visibility=View.VISIBLE
        binding.imgYoutube.visibility=View.VISIBLE
    }
}