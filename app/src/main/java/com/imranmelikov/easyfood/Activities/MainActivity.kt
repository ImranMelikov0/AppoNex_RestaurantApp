package com.imranmelikov.easyfood.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.imranmelikov.easyfood.Db.MealDatabase
import com.imranmelikov.easyfood.R
import com.imranmelikov.easyfood.databinding.ActivityMainBinding
import com.imranmelikov.easyfood.viewModel.HomeViewModel
import com.imranmelikov.easyfood.viewModel.HomeViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    val viewModel:HomeViewModel by lazy {
        val mealDatabase=MealDatabase.getInstance(this)
        val homeViewModelProviderFactory=HomeViewModelFactory(mealDatabase)
        ViewModelProvider(this,homeViewModelProviderFactory).get(HomeViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        val bottomNavigation=binding.btnNav
        val navController= Navigation.findNavController(this, R.id.host_fragment)
        NavigationUI.setupWithNavController(bottomNavigation,navController)
    }

}