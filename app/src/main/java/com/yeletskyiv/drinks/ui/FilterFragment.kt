package com.yeletskyiv.drinks.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.yeletskyiv.drinks.R
import com.yeletskyiv.drinks.recyclerview.CategoryAdapter
import com.yeletskyiv.drinks.retrofit.Category
import com.yeletskyiv.drinks.viewmodel.NetworkViewModel
import kotlinx.android.synthetic.main.fragment_filter.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class FilterFragment(private val categories: List<Category>) : Fragment(R.layout.fragment_filter) {

    private val categoryAdapter = CategoryAdapter()

    private val networkViewModel: NetworkViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as MainActivity).supportActionBar?.title = getString(R.string.filter)
        (activity as MainActivity).supportActionBar?.setHomeButtonEnabled(true)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        categoryAdapter.setElements(categories)
        categoryAdapter.setChecked(networkViewModel.checkedFilters)
        categoryRv.adapter = categoryAdapter

        val divider = DividerItemDecoration(categoryRv.context, RecyclerView.VERTICAL)
        categoryRv.addItemDecoration(divider)

        applyButton.setOnClickListener {
            val filterCategories = mutableListOf<Category>()
            val checked = categoryAdapter.getChecked()
            val iterator = checked.iterator()
            for((index, value) in iterator.withIndex()) {
                if(value) categories[index].let { it1 ->
                    filterCategories.add(it1)
                }
            }
            networkViewModel.setChecked(checked)
            networkViewModel.changeFilters(filterCategories)
        }

        super.onViewCreated(view, savedInstanceState)
    }

    companion object {

        fun newInstance(categories: List<Category>): FilterFragment {
            return FilterFragment(categories)
        }
    }
}