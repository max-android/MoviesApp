package com.my_project.moviesapp.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.my_project.moviesapp.R
import com.my_project.moviesapp.data.entities.review.ReviewMovie

/**
 * Created Максим on 11.11.2019.
 * Copyright © Max
 */
class ReviewAdapter : ListAdapter<ReviewMovie, ReviewAdapter.ReviewHolder>(ReviewDiffCallback()) {

    private var action: (item: ReviewMovie) -> Unit = { }

    fun onItemClick(action: (item: ReviewMovie) -> Unit){
        this.action = action
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        return ReviewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    inner class ReviewHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {

        private var contentReviewTextView = containerView.findViewById(R.id.contentReviewTextView) as AppCompatTextView
        private var authorTextView = containerView.findViewById(R.id.authorTextView) as AppCompatTextView
        private var linkTextView = containerView.findViewById(R.id.linkTextView) as AppCompatTextView

        private lateinit var reviewMovie: ReviewMovie

        init {
            linkTextView.setOnClickListener { action(reviewMovie) }
        }

        fun bindTo(reviewMovie: ReviewMovie) {
            this.reviewMovie = reviewMovie
            with(reviewMovie) {
                contentReviewTextView.text = content
                authorTextView.text = author
            }
        }
    }
}