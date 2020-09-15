package com.yeletskyiv.drinks.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yeletskyiv.drinks.R
import com.yeletskyiv.drinks.recyclerview.viewholder.BaseViewHolder
import com.yeletskyiv.drinks.recyclerview.viewholder.CocktailViewHolder
import com.yeletskyiv.drinks.recyclerview.viewholder.HeaderViewHolder
import com.yeletskyiv.drinks.retrofit.Cocktail
import kotlin.Exception

class CocktailAdapter : RecyclerView.Adapter<BaseViewHolder<Cocktail>>() {

    private val items = mutableListOf<Cocktail>()

    fun setElements(elements: List<Cocktail>) {
        items.addAll(elements)
        notifyDataSetChanged()
    }

    fun addElement(cocktail: Cocktail) {
        items.add(cocktail)
        notifyDataSetChanged()
    }

    fun clearAdapter() {
        items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Cocktail> {
        val inflater = LayoutInflater.from(parent.context)
        val layoutId = getLayoutId(viewType)
        val view = inflater.inflate(layoutId, parent, false)
        return getViewHolder(view, viewType)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Cocktail>, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position].strDrinkThumb == "") HEADER
        else ELEMENT
    }

    private fun getLayoutId(viewType: Int): Int {
        return when(viewType) {
            HEADER -> R.layout.header_item
            ELEMENT -> R.layout.cocktail_item
            else -> throw Exception("Wrong viewType")
        }
    }

    private fun getViewHolder(view: View, viewType: Int): BaseViewHolder<Cocktail> {
        return when(viewType) {
            HEADER -> HeaderViewHolder(view)
            ELEMENT -> CocktailViewHolder(view)
            else -> throw Exception("Wrong viewType")
        }
    }

    companion object {

        private const val HEADER = 1

        private const val ELEMENT = 2
    }
}