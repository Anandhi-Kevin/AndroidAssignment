package com.example.assignment.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.databinding.ItemListBinding
import com.example.assignment.model.Content_
import com.example.assignment.viewmodel.loadImage

class MovieListAdapter(val context: Context) : PagingDataAdapter<Content_, MovieListAdapter.PassengersViewHolder>(MovieComparator) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PassengersViewHolder {


        return PassengersViewHolder(
            ItemListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }


    override fun onBindViewHolder(holder: PassengersViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bindPassenger(it) }
    }

    inner class PassengersViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindPassenger(item: Content_) = with(binding) {
            val id: Int = context.getResources().getIdentifier(item.poster_image.replace(".jpg", ""), "drawable", context.getPackageName())
            imgMovie.loadImage(id)
            tvTitle.text = item.name

        }
    }

    object MovieComparator : DiffUtil.ItemCallback<Content_>() {
        override fun areItemsTheSame(oldItem: Content_, newItem: Content_): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Content_, newItem: Content_): Boolean {
            return oldItem == newItem
        }
    }
}