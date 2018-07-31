package com.example.jgodi.jpapp.ui

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

fun Context.toast(message: CharSequence, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, duration).show()
}

fun <T : RecyclerView.ViewHolder> T.listener(event: (view: View, position: Int, type: Int) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(it, adapterPosition, itemViewType)
    }

    return this
}



fun RecyclerView.ViewHolder.listenerRV(event: (view: View, position: Int, type: Int) -> Unit) {
    itemView.setOnClickListener {
        event.invoke(it, adapterPosition, itemViewType)
    }
}