package com.yeletskyiv.drinks.recyclerview

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yeletskyiv.drinks.R
import com.yeletskyiv.drinks.retrofit.Cocktail
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.cocktail_item.view.*
import kotlinx.android.synthetic.main.header_item.view.*
import kotlinx.coroutines.*
import java.net.URL
import kotlin.Exception

class CocktailAdapter : RecyclerView.Adapter<CocktailAdapter.CocktailViewHolder>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val layoutId = getLayoutId(viewType)
        val view = inflater.inflate(layoutId, parent, false)
        return getViewHolder(view)
    }

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        when(getItemViewType(position)) {
            HEADER -> holder.bindHeader(items[position].strDrink)
            ELEMENT -> holder.bindCocktail(items[position])
        }
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

    private fun getViewHolder(view: View): CocktailViewHolder {
        return CocktailViewHolder(view)
    }

    class CocktailViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindHeader(data: String) {
            containerView.headerTextView.text = data
        }

        fun bindCocktail(data: Cocktail) = runBlocking{
            val bitmap = async(Dispatchers.IO) {
                BitmapFactory.decodeStream(URL(data.strDrinkThumb).openStream())
            }

            containerView.cocktailNameTextView.text = data.strDrink
            if (data.strDrinkThumb != "") {
                containerView.cocktailImageImageView.setImageBitmap(
                    Bitmap.createScaledBitmap(
                        bitmap.await(), 250, 250, false
                    )
                )
            }
        }
    }

    companion object {

        private const val HEADER = 1

        private const val ELEMENT = 2
    }
}