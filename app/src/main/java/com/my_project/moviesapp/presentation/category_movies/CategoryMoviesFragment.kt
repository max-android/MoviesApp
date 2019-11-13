package com.my_project.moviesapp.presentation.category_movies

import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.my_project.moviesapp.R
import com.my_project.moviesapp.data.entities.category_movies.BaseMovie
import com.my_project.moviesapp.data.entities.category_movies.Category
import com.my_project.moviesapp.data.entities.review.ReviewEntity
import com.my_project.moviesapp.data.entities.video.VideoEntity
import com.my_project.moviesapp.presentation.adapters.MovieAdapter
import com.my_project.moviesapp.presentation.adapters.RecyclerOnScrollListener
import com.my_project.moviesapp.presentation.base.BaseFragment
import com.my_project.moviesapp.utilities.*
import kotlinx.android.synthetic.main.fragment_category.*
import timber.log.Timber

/**
 * Created Максим on 04.11.2019.
 * Copyright © Max
 */
class CategoryMoviesFragment : BaseFragment() {

    companion object {
        const val CATEGORY_KEY = "category_key"
        fun newInstance(type: String): Fragment {
            return CategoryMoviesFragment().apply {
                arguments = Bundle().apply {
                    putString(CATEGORY_KEY, type)
                }
            }
        }
    }

    private var cData: String? = null
    private lateinit var viewModel: CategoryMoviesViewModel
    private lateinit var movieAdapter: MovieAdapter
    private var updateScreen = true
    private var countPage = 1
    private var  swipe = false
    private lateinit var recyclerScrollListener: RecyclerOnScrollListener

    override fun getLayoutRes(): Int = R.layout.fragment_category

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CategoryMoviesViewModel::class.java)
        init(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        updateScreen = false
        if (viewModel.listWithPagin)
            viewModel.statusDestroyView = true
    }

    private fun init(savedInstanceState: Bundle?) {
        initData()
        updateToolBar()
        initSwipe()
        initAdapter()
        observeData()
        if (savedInstanceState == null && updateScreen) {
            requestMovies()
        }
    }

    private fun initData() {
        cData = arguments?.getString(CATEGORY_KEY)
        if (viewModel.listWithPagin)
            countPage = viewModel.vmCountPage
    }

    private fun updateToolBar() {
        mainActivity?.apply {
            updateToolBar(false)
        }
    }

    private fun initSwipe() {
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN)
        swipeRefreshLayout.setOnRefreshListener { refreshScreen() }
    }

    private fun refreshScreen() {
        swipeRefreshLayout.isRefreshing = true
        if (viewModel.listWithPagin) {
            viewModel.clearDataForPagin()
            movieAdapter.clearList()
            countPage = 1
            swipe = true
        }
        requestMovies()
        swipeRefreshLayout.isRefreshing = false
    }

    private fun initAdapter() {
        movieAdapter = MovieAdapter()

        val movieLayoutManager =
            LinearLayoutManager(
                this@CategoryMoviesFragment.context,
                LinearLayoutManager.VERTICAL,
                false
            )

        val recyclerScrollListener = object : RecyclerOnScrollListener(movieLayoutManager) {
            override fun onLoadMore() {
                if(!swipe){
                    //TODO
                    Timber.tag("PAGIN-Scroll:").i("onLoadMore()")
                    requestMoviesIfPagination()
                }
            }
        }

        this.recyclerScrollListener = recyclerScrollListener
        movieRecyclerView.apply {
            layoutManager = movieLayoutManager
            addItemDecoration(DividerItemDecoration(context!!, LinearLayout.VERTICAL))
            addOnScrollListener(recyclerScrollListener)
            adapter = movieAdapter
        }
    }

    private fun observeData() = viewModel.cLiveData.observe(viewLifecycleOwner, Observer { processViewState(it) })

    private fun processViewState(viewState: CategoryMoviesState?) {
        viewState?.let {
            when (it) {
                is Loading -> showLoading()
                is SuccessCategoryMovies -> {
                    //TODO
                    if(it.movies.page != null){
                        Timber.tag("PAGIN:").i("SuccessCategoryMovies")
                    }
                    showMovies(it.movies)
                }
                is ErrorCategoryMovies -> showError(it.error)
            }
        }
    }

    private fun showLoading() {
        showProgress()
        emptyImageView.gone()
        movieRecyclerView.gone()
    }

    private fun showMovies(category: Category) {
        if (category.page != null && category.total_pages != null) {
            viewModel.vmTotalPage = category.total_pages
            viewModel.vmCountPage = category.page
            recyclerScrollListener.stopLoading()
            //TODO
            Timber.tag("PAGIN-viewModel.vmCountPage:").i(viewModel.vmCountPage.toString())
            Timber.tag("PAGIN-totalPage:").i(viewModel.vmTotalPage.toString())
        }

        if (viewModel.listWithPagin) {
            if (viewModel.statusDestroyView) {
                movieAdapter.addMovies(viewModel.vmListMovies)
                viewModel.statusDestroyView = false
                //TODO
                Timber.tag("PAGIN-ПЕРЕВОРОТ И ПРОЧЕЕ:").i(viewModel.vmListMovies.size.toString())
                viewModel.vmListMovies.forEach {
                    Timber.tag("PAGIN--------------------").i(it.title + "-------" + it.id)
                }
            } else {
                movieAdapter.addMovies(category.results)
                swipe = false
                //TODO
                Timber.tag("PAGIN-СТАНДАРТ:").i(viewModel.vmTotalPage.toString())
                category.results.forEach {
                    Timber.tag("PAGIN--------------------").i(it.title + "-------" + it.id)
                }
            }
            viewModel.vmListMovies = movieAdapter.currentList.toMutableList()
            Timber.tag("PAGIN-movieAdapter.fullList:").i(viewModel.vmListMovies.size.toString())
        } else {
            movieAdapter.submitList(category.results)
        }
        movieAdapter.onItemClick { onItemMovieClick(it) }
        updateUiIfShowMoves()
    }

    private fun showError(error: Throwable) {
        if (viewModel.listWithPagin) {
            recyclerScrollListener.stopLoading()
        }
        if (viewModel.vmListMovies.isEmpty()) {
            super.showBaseError(error)
        } else {
            updateUiIfShowMoves()
            countPage = viewModel.vmCountPage
        }
        emptyImageView.visible()
    }

    private fun updateUiIfShowMoves() {
        removeProgress()
        emptyImageView.gone()
        movieRecyclerView.visible()
    }

    private fun onItemMovieClick(baseMovie: BaseMovie) {
        Timber.tag("--VIDEO-onItemMovieClick").i(baseMovie.id.toString()+"---"+baseMovie.title)
        DialogUtils(context!!).showDetailMessage(viewLifecycleOwner,
            { viewModel.showVideos(VideoEntity(baseMovie.id.toString(),baseMovie.title))},
            { viewModel.showReviews(ReviewEntity(baseMovie.id.toString(),baseMovie.title))}
        )
    }

    private fun requestMovies() {
        cData?.let {
            when (it) {
                NOW_PLAYING ->
                    viewModel.nowPlayingMovies(it)
                POPULAR -> {
                    viewModel.popularMovies(it, START_PAGE)
                    viewModel.listWithPagin = true
                }
                TOP_RATED -> {
                    viewModel.topRatedMovies(it, START_PAGE)
                    viewModel.listWithPagin = true
                }
                UP_COMING -> viewModel.upcomingMovies(it)
                else -> throw Exception(CATEGORY_EXC)
            }
        }
    }

    private fun requestMoviesIfPagination() {

        if (viewModel.listWithPagin) {
            recyclerScrollListener.startLoading()
            countPage++
            //TODO
            Timber.tag("PAGIN-Scroll:").i("requestMoviesIfPagination()")
        }

        cData?.let {
            when (it) {
                POPULAR -> {
                    //TODO
                    Timber.tag("PAGIN-Scroll: countPage:").i(countPage.toString())
                    if (countPage <= viewModel.vmTotalPage) {
                        viewModel.popularMovies(it, countPage)
                    }
                }
                TOP_RATED -> {
                    if (countPage <= viewModel.vmTotalPage) {
                        viewModel.topRatedMovies(it, countPage)
                    }
                }
                else -> {
                }
            }
        }
    }

}