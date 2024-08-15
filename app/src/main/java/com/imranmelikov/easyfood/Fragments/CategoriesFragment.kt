package com.imranmelikov.easyfood.Fragments

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.imranmelikov.easyfood.Activities.MainActivity
import com.imranmelikov.easyfood.Adapters.CategoriesAdapter
import com.imranmelikov.easyfood.R
import com.imranmelikov.easyfood.databinding.FragmentCategoriesBinding
import com.imranmelikov.easyfood.viewModel.HomeViewModel

class CategoriesFragment : androidx.fragment.app.Fragment() {
    private lateinit var bindnig:FragmentCategoriesBinding
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var viewModel:HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=(activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindnig= FragmentCategoriesBinding.inflate(inflater)
        return bindnig.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerview()
        observeCategories()
    }

    private fun observeCategories() {
        viewModel.observerCategoryLiveData().observe(viewLifecycleOwner, Observer {
            categoriesAdapter.setCategorylist(it)
        })
    }

    private fun prepareRecyclerview() {
        categoriesAdapter= CategoriesAdapter()
        bindnig.favoriteRecyclerView.apply {
            layoutManager=GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter=categoriesAdapter
        }
    }
}