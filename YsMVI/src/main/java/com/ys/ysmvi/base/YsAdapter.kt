package com.ys.ysmvi.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class YsAdapter<T>(private val data: List<T>, private val layout: Int): RecyclerView.Adapter<YsAdapter<T>.ViewHolder>() {
    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        init {
            viewInit(view, this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    abstract override fun onBindViewHolder(holder: ViewHolder, position: Int)

    open fun viewInit(mView: View, holder: ViewHolder) {}
}