package com.yeletskyiv.drinks.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yeletskyiv.drinks.R
import com.yeletskyiv.drinks.recyclerview.viewholder.BaseViewHolder
import com.yeletskyiv.drinks.retrofit.Category
import kotlinx.android.synthetic.main.category_item.view.*

class CategoryAdapter : RecyclerView.Adapter<BaseViewHolder<Category>>() {

    private val items = mutableListOf<Category>()

    private val checked = mutableListOf<Boolean>()

    fun setElements(elements: List<Category>?) {
        items.clear()
        if (elements != null) {
            items.addAll(elements)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Category> {
        val inflater = LayoutInflater.from(parent.context)
        val layoutId = getLayoutId()
        val view = inflater.inflate(layoutId, parent, false)
        return getViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Category>, position: Int) {
        holder.containerView.filterCheckBox.setOnClickListener {
            checked[position] = !checked[position]
        }
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private fun getLayoutId(): Int = R.layout.category_item

    private fun getViewHolder(view: View): BaseViewHolder<Category>  = CategoryViewHolder(view)

    fun getChecked(): List<Boolean> = checked

    fun setChecked(checked: List<Boolean>) {
        this.checked.clear()
        this.checked.addAll(checked)
    }

    inner class CategoryViewHolder(
        override val containerView: View
    ) : BaseViewHolder<Category>(containerView) {

        override fun bind(data: Category) {
            containerView.categoryTextView.text = data.category
            containerView.filterCheckBox.isChecked = checked[adapterPosition]
        }
    }
}