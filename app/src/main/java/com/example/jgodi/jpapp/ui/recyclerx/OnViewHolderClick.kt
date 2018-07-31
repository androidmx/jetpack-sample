package com.example.jgodi.jpapp.ui.recyclerx

interface OnViewHolderClick<T: Any> {
    fun onItemClick(item: T, position: Int)
}