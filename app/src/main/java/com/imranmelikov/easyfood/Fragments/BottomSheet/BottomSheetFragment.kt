package com.imranmelikov.easyfood.Fragments.BottomSheet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.imranmelikov.easyfood.Activities.MainActivity
import com.imranmelikov.easyfood.Activities.MealActivity
import com.imranmelikov.easyfood.Fragments.HomeFragment
import com.imranmelikov.easyfood.R
import com.imranmelikov.easyfood.databinding.FragmentBottomSheetBinding
import com.imranmelikov.easyfood.viewModel.HomeViewModel

private const val MEAL_ID = "param1"

class BottomSheetFragment : BottomSheetDialogFragment() {
    private var mealId: String? = null
    private lateinit var binding:FragmentBottomSheetBinding
    private lateinit var viewModel:HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)
        }
        viewModel=(activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mealId?.let { viewModel.getMealById(it) }
        observeBottomSheetMeal()
        onBottomSheetDialogClick()
    }

    private fun onBottomSheetDialogClick() {
        binding.bottomsheetconstraint.setOnClickListener {
            if(mealName!=null && mealThumb!=null){
                val intent=Intent(activity,MealActivity::class.java)
                intent.apply {
                    putExtra(HomeFragment.HEAD_ID,mealId)
                    putExtra(HomeFragment.HEAD_NAME,mealName)
                    putExtra(HomeFragment.HEAD_THUMB,mealThumb)
                }
                startActivity(intent)

            }
        }
    }
    private var mealName:String?=null
    private var mealThumb:String?=null
    private fun observeBottomSheetMeal() {
        viewModel.observeBottomSheetMeal().observe(viewLifecycleOwner, Observer {
            Glide.with(this)
                .load(it.strMealThumb)
                .into(binding.imgCategory)
            binding.tvMealNameInBtmsheet.text=it.strMeal
            binding.tvMealCategory.text=it.strCategory
            binding.tvMealCountry.text=it.strArea

            mealName=it.strMeal
            mealThumb=it.strMealThumb
        })
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            BottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, param1)
                }
            }
    }
}