package com.example.jgodi.jpapp.ui.recyclerx

internal interface Binder<T: Any> {
    fun bind(item: T)
}