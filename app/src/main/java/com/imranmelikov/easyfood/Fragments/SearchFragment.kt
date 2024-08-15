package com.imranmelikov.easyfood.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.imranmelikov.easyfood.Activities.MainActivity
import com.imranmelikov.easyfood.Adapters.CategoriesAdapter
import com.imranmelikov.easyfood.Adapters.MealsAdapter
import com.imranmelikov.easyfood.R
import com.imranmelikov.easyfood.databinding.FragmentSearchBinding
import com.imranmelikov.easyfood.viewModel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {
    private lateinit var binding:FragmentSearchBinding
    private lateinit var viewModel:HomeViewModel
    private lateinit var searchRecyclerviewAdapter: MealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=(activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        binding.icSearch.setOnClickListener { searchMeals() }

        observeSearchMealsLiveData()

        var searchJob:Job?=null
        binding.edSearch.addTextChangedListener {
            searchJob?.cancel()
            searchJob=lifecycleScope.launch {
                delay(500)
                viewModel.searchMeals(it.toString())
            }
        }
    }

    private fun observeSearchMealsLiveData() {
        viewModel.observeSearchedMealsLiveData().observe(viewLifecycleOwner, Observer {
            searchRecyclerviewAdapter.differ.submitList(it)
        })
    }

    private fun searchMeals() {
        val searchQuery=binding.edSearch.text.toString()
        if(searchQuery.isNotEmpty()){
            viewModel.searchMeals(searchQuery)
        }
    }

    private fun prepareRecyclerView() {
        searchRecyclerviewAdapter= MealsAdapter()
        binding.recyclerView.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=searchRecyclerviewAdapter
        }
    }
}