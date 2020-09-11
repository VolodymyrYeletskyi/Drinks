package com.yeletskyiv.drinks.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("strCategory")
    @Expose val category: String
)