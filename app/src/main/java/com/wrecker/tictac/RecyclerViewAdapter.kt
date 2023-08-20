package com.wrecker.tictac

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


class RecyclerViewAdapter() : RecyclerView.Adapter<RecyclerViewAdapter.CustomAdapter>() {


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CustomAdapter, position: Int) {


        val data = differ.currentList[position]


        if (data.first && data.second.not()) {
            holder.icon.setImageResource(R.drawable.baseline_check_24)
        } else if (data.second && data.first.not()) {
            holder.icon.setImageResource(R.drawable.baseline_close_24)
        }

        if (data.third){
            holder.icon.setOnClickListener {
                onItemClick?.let {
                    it(position)
                }
            }
        }
    }

    private val differCallBack =
        object : DiffUtil.ItemCallback<Triple<Boolean, Boolean, Boolean>>() {

            override fun areContentsTheSame(
                oldItem: Triple<Boolean, Boolean, Boolean>,
                newItem: Triple<Boolean, Boolean, Boolean>
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(
                oldItem: Triple<Boolean, Boolean, Boolean>,
                newItem: Triple<Boolean, Boolean, Boolean>
            ): Boolean {
                return oldItem.first == newItem.first
            }

        }

    val differ = AsyncListDiffer(this, differCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cell, parent, false)
        return CustomAdapter(view)
    }


    class CustomAdapter(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.icon)

    }


    private var onItemClick: ((id: Int) -> Unit)? = null

    fun onItemClick(listener: (id: Int) -> Unit) {
        onItemClick = listener
    }

}