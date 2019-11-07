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


    private var movieLayoutManager: LinearLayoutManager =
        LinearLayoutManager(this@CategoryMoviesFragment.context, LinearLayoutManager.VERTICAL, false)
    private var cData: String? = null
    private lateinit var viewModel: CategoryMoviesViewModel
    private lateinit var movieAdapter: MovieAdapter
    private var updateScreen = true
    private var currentPage = 0
    private var totalPage = 0

    override fun getLayoutRes(): Int = R.layout.fragment_category

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CategoryMoviesViewModel::class.java)
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
            requestMovies()
        }
    }

    private fun initData() {
        cData = arguments?.getString(CATEGORY_KEY)
        //TODO
        Timber.tag("--DATA").i(cData)
    }

    private fun updateToolBar() {
        mainActivity?.apply {
            updateToolBar(false)
        }
    }

    private fun initSwipe(){
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN)
        swipeRefreshLayout.setOnRefreshListener { refreshScreen() }
    }

    private fun refreshScreen() {
        swipeRefreshLayout.isRefreshing = true
        requestMovies()
        swipeRefreshLayout.isRefreshing = false
    }

    private fun initAdapter() {
        movieAdapter = MovieAdapter()
        movieRecyclerView.apply {
            //TODO
            //layoutManager = LinearLayoutManager(context!!, RecyclerView.VERTICAL, false)
            layoutManager = movieLayoutManager
            addItemDecoration(DividerItemDecoration(context!!, LinearLayout.VERTICAL))
            addOnScrollListener(recyclerScrollListener)
            adapter = movieAdapter
        }
    }

    private fun observeData() = viewModel.cLiveData.observe(this, Observer { processViewState(it) })

    private fun processViewState(viewState: CategoryMoviesState?) {
        viewState?.let {
            when (it) {
                is Loading ->  showLoading()
                is SuccessCategoryMovies -> showMovies(it.movies)
                is ErrorCategoryMovies -> showError(it.error)
            }
        }
    }

    private fun showLoading(){
        showProgress()
        emptyImageView.gone()
        movieRecyclerView.gone()
    }

    private fun showMovies(category: Category) {
        if(category.page != null && category.total_pages != null){
            currentPage = category.page
            totalPage = category.total_pages
            viewModel.vmCurrentPage = category.page
            viewModel.vmTotalPage = category.total_pages
            //TODO
            Timber.tag("PAGIN-currentPage:").i(currentPage.toString())
            Timber.tag("PAGIN-totalPage:").i(totalPage.toString())
        }
        Timber.tag("--DATA-LIST").i(category.results.size.toString())
        movieAdapter.submitList(category.results)
        movieAdapter.onItemClick { onItemCityClick(it) }
        removeProgress()
        emptyImageView.gone()
        movieRecyclerView.visible()
    }

    private fun onItemCityClick(baseMovie: BaseMovie){
      //TODO реализовать позже
    }

   //TODO
    //const val NOW_PLAYING = "Сейчас в кино"
    //const val POPULAR = "Популярные"
    //const val TOP_RATED = "Оцененные"
    //const val UP_COMING = "Скоро"
    private fun requestMovies() {
        cData?.let {
            when (it) {
                NOW_PLAYING -> viewModel.nowPlayingMovies(it)
                POPULAR -> viewModel.popularMovies(it, START_PAGE)
                TOP_RATED -> viewModel.topRatedMovies(it, START_PAGE)
                UP_COMING -> viewModel.upcomingMovies(it)
                else -> throw Exception(CATEGORY_EXC)
            }
        }
    }

    private fun requestMoviesIfPagination(){
        cData?.let {
            when (it) {
                POPULAR -> {
                    Timber.tag("PAGIN-current-request:").i(viewModel.vmCurrentPage.toString())
                    Timber.tag("PAGIN-total-request:").i(viewModel.vmTotalPage.toString())
                    if(viewModel.vmCurrentPage <= viewModel.vmTotalPage)
                        viewModel.popularMovies(it, viewModel.vmCurrentPage)
                }
                TOP_RATED -> {
                    if(viewModel.vmCurrentPage <= viewModel.vmTotalPage)
                    viewModel.topRatedMovies(it, START_PAGE)
                }
                else -> {}
            }
        }
    }

    private val recyclerScrollListener = object : RecyclerOnScrollListener(movieLayoutManager) {
        override fun onLoadMore() {
            requestMoviesIfPagination()
        }
    }

}