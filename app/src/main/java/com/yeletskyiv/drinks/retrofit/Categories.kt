package com.yeletskyiv.drinks.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Categories(
    @SerializedName("drinks")
    @Expose
    val category: List<Category>
)