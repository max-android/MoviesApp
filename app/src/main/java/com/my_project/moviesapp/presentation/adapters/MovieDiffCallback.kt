package com.my_project.moviesapp.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.my_project.moviesapp.data.entities.category_movies.BaseMovie

/**
 * Created Максим on 04.11.2019.
 * Copyright © Max
 */
class MovieDiffCallback : DiffUtil.ItemCallback<BaseMovie>() {
    override fun areItemsTheSame(oldItem: BaseMovie, newItem: BaseMovie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: BaseMovie, newItem: BaseMovie): Boolean {
        return oldItem == newItem
    }
}