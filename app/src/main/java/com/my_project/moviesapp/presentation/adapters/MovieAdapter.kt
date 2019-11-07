package com.my_project.moviesapp.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import com.my_project.moviesapp.R
import com.my_project.moviesapp.data.entities.category_movies.BaseMovie
import com.my_project.moviesapp.utilities.ApiConst

/**
 * Created Максим on 04.11.2019.
 * Copyright © Max
 */
class MovieAdapter : ListAdapter<BaseMovie, MovieAdapter.MovieHolder>(MovieDiffCallback()) {

    private var action: (item: BaseMovie) -> Unit = { }

    fun onItemClick(action: (item: BaseMovie) -> Unit){
        this.action = action
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieHolder(view)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    inner class MovieHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {

        private var titleTextView = containerView.findViewById(R.id.titleTextView) as AppCompatTextView
        private var ratingTextView = containerView.findViewById(R.id.ratingTextView) as AppCompatTextView
        private var movieImageView = containerView.findViewById(R.id.movieImageView) as SimpleDraweeView

        private lateinit var baseMovie: BaseMovie

        init { containerView.setOnClickListener { action(baseMovie) } }

        fun bindTo(movie: BaseMovie) {
            this.baseMovie = movie
            with(baseMovie) {

                titleTextView.text = title
                ratingTextView.text = vote_average.toString()
                movieImageView.setImageURI(ApiConst.MIDDLE_IMAGE_URL + poster_path)

            }
        }
    }
}