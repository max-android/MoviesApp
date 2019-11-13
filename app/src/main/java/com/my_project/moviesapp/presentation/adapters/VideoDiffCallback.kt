package com.my_project.moviesapp.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.my_project.moviesapp.data.entities.video.VideoMovie

/**
 * Created Максим on 09.11.2019.
 * Copyright © Max
 */
class VideoDiffCallback : DiffUtil.ItemCallback<VideoMovie>() {
    override fun areItemsTheSame(oldItem: VideoMovie, newItem: VideoMovie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: VideoMovie, newItem: VideoMovie): Boolean {
        return oldItem == newItem
    }
}