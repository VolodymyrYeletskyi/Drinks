package com.yeletskyiv.drinks.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yeletskyiv.drinks.retrofit.Category
import com.yeletskyiv.drinks.retrofit.Cocktail
import com.yeletskyiv.drinks.retrofit.CocktailsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class NetworkViewModel(private val cocktailsApiService: CocktailsApi) : ViewModel(){

    val categoriesData: MutableLiveData<List<Category>> = MutableLiveData()
    val initialData: MutableLiveData<Unit> = MutableLiveData()
    val loadMoreData: MutableLiveData<Unit> = MutableLiveData()
    val cocktailsData: MutableLiveData<List<Cocktail>> = MutableLiveData()

    var isLastList = false
    var checkedFilters = mutableListOf<Boolean>()

    fun getCategories() = runBlocking(Dispatchers.IO) {
        val response = cocktailsApiService.getCategories()
        if (response.isSuccessful) {
            response.body()?.category?.let { categoriesData.postValue(it) }
        }
        initialData.postValue(Unit)
    }

    fun getAllCategories(): List<Category>? = categoriesData.value

    fun changeFilters(filters: List<Category>) {
        categoriesData.value = filters
    }

    fun getList(category: Category) = runBlocking {
        val cocktails = mutableListOf<Cocktail>()
        cocktails.add(Cocktail(category.category, ""))

        val response = cocktailsApiService.getDrinks(category.category)
        if (response.isSuccessful) {
            response.body()?.drinks?.let { cocktails.addAll(it) }
        }
        cocktailsData.value = cocktails
    }

    fun loadMore() {
        if (!isLastList) loadMoreData.value = Unit
    }

    fun initializeChecked(size: Int) {
        var index = 0
        while (index < size) {
            checkedFilters.add(true)
            index++
        }
    }

    fun setChecked(checked: List<Boolean>) {
        checkedFilters.clear()
        checkedFilters.addAll(checked)
    }
}