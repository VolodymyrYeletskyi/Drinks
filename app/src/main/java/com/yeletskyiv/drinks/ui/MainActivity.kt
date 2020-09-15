package com.yeletskyiv.drinks.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.yeletskyiv.drinks.R
import com.yeletskyiv.drinks.retrofit.Category
import com.yeletskyiv.drinks.viewmodel.NetworkViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val categories = mutableListOf<Category>()

    private val networkViewModel: NetworkViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        networkViewModel.initialData.observe(this) {
            networkViewModel.getAllCategories()?.let { it1 -> categories.addAll(it1) }
            networkViewModel.initializeChecked(categories.size)
            loadFragment(LIST_TAG, categories)
        }

        networkViewModel.getCategories()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.item_filter -> {
                loadFragment(FILTER_TAG, categories)
            }
            android.R.id.home -> {
                supportActionBar?.title = getString(R.string.app_name)
                supportActionBar?.setHomeButtonEnabled(false)
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                networkViewModel.categoriesData.value?.let { loadFragment(LIST_TAG, it) }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadFragment(tag: Int, categories: List<Category>) {
        when(tag) {
            FILTER_TAG -> {
                supportFragmentManager
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(
                        R.id.fragment,
                        FilterFragment.newInstance(categories)
                    )
                    .commit()
            }
            LIST_TAG -> {
                supportFragmentManager
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.fragment, ListFragment.newInstance(categories))
                    .commit()
            }
        }
    }

    companion object {

        private const val LIST_TAG = 1

        private const val FILTER_TAG = 2
    }
}