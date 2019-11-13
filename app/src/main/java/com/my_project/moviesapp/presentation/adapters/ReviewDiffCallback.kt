package com.my_project.moviesapp.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.my_project.moviesapp.data.entities.review.ReviewMovie

/**
 * Created Максим on 11.11.2019.
 * Copyright © Max
 */
class ReviewDiffCallback : DiffUtil.ItemCallback<ReviewMovie>() {
    override fun areItemsTheSame(oldItem: ReviewMovie, newItem: ReviewMovie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ReviewMovie, newItem: ReviewMovie): Boolean {
        return oldItem == newItem
    }
}