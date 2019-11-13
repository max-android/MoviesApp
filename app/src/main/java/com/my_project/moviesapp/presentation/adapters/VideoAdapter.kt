package com.my_project.moviesapp.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.my_project.moviesapp.R
import com.my_project.moviesapp.data.entities.video.VideoMovie

/**
 * Created Максим on 09.11.2019.
 * Copyright © Max
 */
class VideoAdapter : ListAdapter<VideoMovie, VideoAdapter.VideoHolder>(VideoDiffCallback()) {

    private var action: (item: VideoMovie) -> Unit = { }

    fun onItemClick(action: (item: VideoMovie) -> Unit){
        this.action = action
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
        return VideoHolder(view)
    }

    override fun onBindViewHolder(holder: VideoHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    inner class VideoHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {

        private var nameMovieTextView = containerView.findViewById(R.id.nameMovieTextView) as AppCompatTextView
        private lateinit var videoMovie: VideoMovie

        init {
            containerView.setOnClickListener { action(videoMovie) }
        }

        fun bindTo(videoMovie: VideoMovie) {
            this.videoMovie = videoMovie
            with(videoMovie) {
                nameMovieTextView.text = name
            }
        }
    }
}