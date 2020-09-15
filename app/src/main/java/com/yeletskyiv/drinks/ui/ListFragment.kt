package com.yeletskyiv.drinks.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yeletskyiv.drinks.R
import com.yeletskyiv.drinks.recyclerview.CocktailAdapter
import com.yeletskyiv.drinks.recyclerview.CocktailScrollListener
import com.yeletskyiv.drinks.retrofit.Category
import com.yeletskyiv.drinks.retrofit.Cocktail
import com.yeletskyiv.drinks.viewmodel.NetworkViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class ListFragment(private var categories: List<Category>) : Fragment(R.layout.fragment_list) {

    private val cocktailAdapter = CocktailAdapter()

    private val networkViewModel: NetworkViewModel by sharedViewModel()

    private var categoryIndex = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        networkViewModel.loadMoreData.observe(this) {
            if (categoryIndex + 1 < categories.size) {
                networkViewModel.getList(categories[categoryIndex + 1])
                categoryIndex++
            }
            else {
                cocktailAdapter.addElement(Cocktail(getString(R.string.end_list), ""))
                networkViewModel.isLastList = true
            }
        }
        networkViewModel.categoriesData.observe(this) {
            categoryIndex = 0
            networkViewModel.isLastList = false
            cocktailAdapter.clearAdapter()
            categories = it
        }
        networkViewModel.cocktailsData.observe(this) {
            cocktailAdapter.setElements(it)
        }
        networkViewModel.getList(categories[categoryIndex])

        cocktailRv.adapter = cocktailAdapter

        val divider = DividerItemDecoration(cocktailRv.context, RecyclerView.VERTICAL)
        cocktailRv.addItemDecoration(divider)

        cocktailRv.addOnScrollListener(
            object : CocktailScrollListener(cocktailRv.layoutManager as LinearLayoutManager) {

                override fun loadMoreCocktails() {
                    networkViewModel.loadMore()
                }
        })

        super.onViewCreated(view, savedInstanceState)
    }

    companion object {

        fun newInstance(categories: List<Category>): ListFragment {
            return ListFragment(categories)
        }
    }
}