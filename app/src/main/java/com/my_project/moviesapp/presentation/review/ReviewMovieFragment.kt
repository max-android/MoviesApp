package com.my_project.moviesapp.presentation.review

import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.my_project.moviesapp.R
import com.my_project.moviesapp.data.entities.review.ReviewEntity
import com.my_project.moviesapp.data.entities.review.ReviewMovie
import com.my_project.moviesapp.presentation.adapters.ReviewAdapter
import com.my_project.moviesapp.presentation.base.BaseFragment
import com.my_project.moviesapp.utilities.gone
import com.my_project.moviesapp.utilities.mainActivity
import com.my_project.moviesapp.utilities.visible
import kotlinx.android.synthetic.main.fragment_review_movie.*


/**
 * Created Максим on 11.11.2019.
 * Copyright © Max
 */
class ReviewMovieFragment:BaseFragment() {

    companion object {
        const val REVIEW_KEY = "review_key"
        fun newInstance(review: ReviewEntity): Fragment {
            return ReviewMovieFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(REVIEW_KEY,review)
                }
            }
        }
    }

    private lateinit var viewModel: ReviewMovieViewModel
    private var updateScreen = true
    private var reviewEntity: ReviewEntity? = null
    private lateinit var reviewAdapter:ReviewAdapter

    override fun getLayoutRes(): Int = R.layout.fragment_review_movie

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReviewMovieViewModel::class.java)
        init(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        updateScreen = false
    }

    private fun init(savedInstanceState: Bundle?) {
        initData()
        updateToolBar()
        initSwipe()
        initAdapter()
        observeData()
        if (savedInstanceState == null && updateScreen) {
            requestReviews()
        }
    }

    private fun initData() {
        reviewEntity = arguments?.getSerializable(REVIEW_KEY) as? ReviewEntity
    }

    private fun updateToolBar() {
        mainActivity?.apply {
            updateToolBar(true)
            titleToolBar.text = String.format(
                rProvider.getString(R.string.title_review_film),
                reviewEntity?.film ?: rProvider.getString(R.string.empty_text)
            )
        }
    }

    private fun initSwipe() {
        reviewSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN)
        reviewSwipeRefreshLayout.setOnRefreshListener { refreshScreen() }
    }

    private fun refreshScreen() {
        reviewSwipeRefreshLayout.isRefreshing = true
        requestReviews()
        reviewSwipeRefreshLayout.isRefreshing = false
    }

    private fun initAdapter() {
        reviewAdapter = ReviewAdapter()
        reviewRecyclerView.apply {
            layoutManager = LinearLayoutManager(
                this@ReviewMovieFragment.context,
                LinearLayoutManager.VERTICAL,
                false
            )
            addItemDecoration(DividerItemDecoration(context!!, LinearLayout.VERTICAL))
            adapter = reviewAdapter
        }
    }

    private fun observeData() =
        viewModel.rLiveData.observe(viewLifecycleOwner, Observer { processViewState(it) })

    private fun processViewState(viewState: ReviewMovieState?) {
        viewState?.let {
            when (it) {
                is LoadingReviewMovie -> showLoading()
                is SuccessReviewMovie -> showReview(it.reviews)
                is ErrorReviewMovie -> showError(it.error)
            }
        }
    }

    private fun showLoading() {
        showProgress()
        reviewEmptyImageView.gone()
        reviewRecyclerView.gone()
    }

    private fun showReview(reviews: List<ReviewMovie>) {
        if (reviews.isEmpty()) {
            removeProgress()
            reviewEmptyImageView.visible()
            return
        }
        reviewAdapter.submitList(reviews)
        reviewAdapter.onItemClick(::launchReview)
        removeProgress()
        reviewEmptyImageView.gone()
        reviewRecyclerView.visible()
    }

    private fun showError(error: Throwable) {
        super.showBaseError(error)
        reviewEmptyImageView.visible()
    }

    private fun requestReviews() {
        reviewEntity?.let {
            viewModel.reviews(it.id)
        }
    }

    private fun launchReview(review: ReviewMovie) {
        viewModel.showWeb(review.url)
        //TODO другой вариант
//        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(review.url))
//        if (intent.resolveActivity(context!!.packageManager) != null)
//        startActivity(intent)
    }
}