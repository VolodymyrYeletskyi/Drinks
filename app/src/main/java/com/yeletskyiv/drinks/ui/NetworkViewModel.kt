package com.yeletskyiv.drinks.ui

import androidx.lifecycle.MutableLiveData
import com.yeletskyiv.drinks.retrofit.Category
import com.yeletskyiv.drinks.retrofit.Cocktail
import com.yeletskyiv.drinks.retrofit.Network
import kotlinx.coroutines.runBlocking

class NetworkViewModel {

    val categoriesData: MutableLiveData<List<Category>> = MutableLiveData()
    val initialData: MutableLiveData<Unit> = MutableLiveData()
    val loadMoreData: MutableLiveData<Unit> = MutableLiveData()
    val cocktailsData: MutableLiveData<List<Cocktail>> = MutableLiveData()

    var isLastList = false
    var checkedFilters = mutableListOf<Boolean>()

    fun getCategories() = runBlocking {
        val response = Network.getInstance().getCocktailsApi().getCategories()
        if (response.isSuccessful) {
            response.body()?.category?.let { categoriesData.value = it }
        }
        initialData.value = Unit
    }

    fun getAllCategories(): List<Category>? = categoriesData.value

    fun changeFilters(filters: List<Category>) {
        categoriesData.value = filters
    }

    fun getList(category: Category) = runBlocking {
        val cocktails = mutableListOf<Cocktail>()
        cocktails.add(Cocktail(category.category, ""))

        val response = Network.getInstance().getCocktailsApi().getDrinks(category.category)
        if (response.isSuccessful) {
            response.body()?.drinks?.let { cocktails.addAll(it) }
        }
        cocktailsData.value = cocktails
    }

    fun loadMore() {
        if (!isLastList) loadMoreData.postValue(Unit)
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

    companion object {

        private var instance: NetworkViewModel? = null

        fun getInstance() : NetworkViewModel {
            return if(instance == null) NetworkViewModel()
            else instance as NetworkViewModel
        }
    }
}