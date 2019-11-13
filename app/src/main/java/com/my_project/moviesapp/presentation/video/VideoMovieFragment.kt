package com.my_project.moviesapp.presentation.video

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.my_project.moviesapp.R
import com.my_project.moviesapp.data.entities.video.VideoEntity
import com.my_project.moviesapp.data.entities.video.VideoMovie
import com.my_project.moviesapp.presentation.adapters.VideoAdapter
import com.my_project.moviesapp.presentation.base.BaseFragment
import com.my_project.moviesapp.utilities.ApiConst
import com.my_project.moviesapp.utilities.gone
import com.my_project.moviesapp.utilities.mainActivity
import com.my_project.moviesapp.utilities.visible
import kotlinx.android.synthetic.main.fragment_video_movie.*
import timber.log.Timber


/**
 * Created Максим on 09.11.2019.
 * Copyright © Max
 */
class VideoMovieFragment : BaseFragment() {

    companion object {
        const val VIDEO_KEY = "video_key"
        fun newInstance(video: VideoEntity): Fragment {
            return VideoMovieFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(VIDEO_KEY, video)
                }
            }
        }
    }

    private lateinit var viewModel: VideoMovieViewModel
    private var updateScreen = true
    private var videoEntity: VideoEntity? = null
    private lateinit var videoAdapter: VideoAdapter

    override fun getLayoutRes(): Int = R.layout.fragment_video_movie

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VideoMovieViewModel::class.java)
        init(savedInstanceState)
        Timber.tag("--VIDEO").i("onActivityCreated()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.tag("--VIDEO").i("onDestroyView()")
        updateScreen = false
    }

    private fun init(savedInstanceState: Bundle?) {
        initData()
        updateToolBar()
        initSwipe()
        initAdapter()
        observeData()
        if (savedInstanceState == null && updateScreen) {
            requestVideos()
        }
    }

    private fun initData() {
        videoEntity = arguments?.getSerializable(VIDEO_KEY) as? VideoEntity
    }

    private fun updateToolBar() {
        mainActivity?.apply {
            updateToolBar(true)
            titleToolBar.text = String.format(
                rProvider.getString(R.string.title_video_film),
                videoEntity?.film ?: rProvider.getString(R.string.empty_text)
            )
        }
    }

    private fun initSwipe() {
        videoSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN)
        videoSwipeRefreshLayout.setOnRefreshListener { refreshScreen() }
    }

    private fun refreshScreen() {
        videoSwipeRefreshLayout.isRefreshing = true
        requestVideos()
        videoSwipeRefreshLayout.isRefreshing = false
    }

    private fun initAdapter() {
        videoAdapter = VideoAdapter()
        videoRecyclerView.apply {
            layoutManager = LinearLayoutManager(
                this@VideoMovieFragment.context,
                LinearLayoutManager.VERTICAL,
                false
            )
            addItemDecoration(DividerItemDecoration(context!!, LinearLayout.VERTICAL))
            adapter = videoAdapter
        }
    }

    private fun observeData() =
        viewModel.vLiveData.observe(viewLifecycleOwner, Observer { processViewState(it) })

    private fun processViewState(viewState: VideoMovieState?) {
        viewState?.let {
            when (it) {
                is LoadingVideoMovie -> showLoading()
                is SuccessVideoMovie -> showVideos(it.videos)
                is ErrorVideoMovie -> showError(it.error)
            }
        }
    }

    private fun showLoading() {
        showProgress()
        videoEmptyImageView.gone()
        videoRecyclerView.gone()
    }

    private fun showVideos(videos: List<VideoMovie>) {

        videos.forEach {
            Timber.tag("--VIDEO").i(it.toString())
        }
        if (videos.isEmpty()) {
            removeProgress()
            videoEmptyImageView.visible()
            return
        }
        videoAdapter.submitList(videos)
        videoAdapter.onItemClick(::launchVideo)
        removeProgress()
        videoEmptyImageView.gone()
        videoRecyclerView.visible()
    }

    private fun showError(error: Throwable) {
        super.showBaseError(error)
        videoEmptyImageView.visible()
    }

    private fun requestVideos() {
        videoEntity?.let {
            viewModel.videos(it.id)
        }
    }

    private fun launchVideo(video: VideoMovie) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(ApiConst.BASE_YOUTUBE_URL + video.key))
        if (intent.resolveActivity(context!!.packageManager) != null)
        startActivity(intent)
    }

}