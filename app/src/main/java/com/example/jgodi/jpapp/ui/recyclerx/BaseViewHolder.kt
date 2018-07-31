package com.example.jgodi.jpapp.ui.recyclerx

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T: Any>(open val container: View)
    : RecyclerView.ViewHolder(container), Binder<T>, View.OnClickListener {

    protected lateinit var item: T
    lateinit var itemClickListener: OnViewHolderClick<T>

    open fun setClickListener(listener: OnViewHolderClick<T>) {
        this.container.setOnClickListener(this)
        this.itemClickListener = listener
    }

    override fun bind(item: T) {
        this.item = item
    }

    override fun onClick(view: View?) {
        itemClickListener.onItemClick(item, adapterPosition)
    }
}