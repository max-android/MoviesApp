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
    private var countPage = 1

    override fun getLayoutRes(): Int = R.layout.fragment_category

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        PRINT("onActivityCreated()")
        viewModel = ViewModelProvider(this).get(CategoryMoviesViewModel::class.java)
        init(savedInstanceState)
    }

    private fun PRINT(text:String){
        cData?.let {
            if(it == TOP_RATED){
            Timber.tag("--LIFE--").i(text)
            }
            if(it == NOW_PLAYING){
                Timber.tag("--LIFE-now--").i(text)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PRINT("onCreate()")
    }

    override fun onStart() {
        super.onStart()
        PRINT("onStart()")
    }

    override fun onResume() {
        super.onResume()
        PRINT("onResume()")
    }

    override fun onPause() {
        super.onPause()
        PRINT("onPause()")
    }

    override fun onStop() {
        super.onStop()
        PRINT("onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        PRINT("onDestroy()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        PRINT("onDestroyView()")
        updateScreen = false
        if(viewModel.listWithPagin)
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
        if(viewModel.listWithPagin)
        countPage = viewModel.vmCountPage
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
        if(viewModel.listWithPagin){
           viewModel.clearDataForPagin()
            movieAdapter.clearList()
            countPage = 1
        }
        requestMovies()
        swipeRefreshLayout.isRefreshing = false
    }

    private fun initAdapter() {
        movieAdapter = MovieAdapter()
        movieRecyclerView.apply {
            if(layoutManager == null){
                layoutManager = movieLayoutManager
            }
            addItemDecoration(DividerItemDecoration(context!!, LinearLayout.VERTICAL))
            addOnScrollListener(recyclerScrollListener)
            adapter = movieAdapter
        }
    }

    private fun observeData() = viewModel.cLiveData.observe(this, Observer { processViewState(it) })

    private fun processViewState(viewState: CategoryMoviesState?) {
        viewState?.let {
            when (it) {
                is Loading -> showLoading()
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
            viewModel.vmTotalPage = category.total_pages
            viewModel.vmCountPage = category.page
            recyclerScrollListener.stopLoading()
            //TODO
            Timber.tag("PAGIN-viewModel.vmCountPage:").i(viewModel.vmTotalPage.toString())
            Timber.tag("PAGIN-totalPage:").i(viewModel.vmTotalPage.toString())
        }

        if(viewModel.listWithPagin){
            if(viewModel.statusDestroyView){
                movieAdapter.addMovies(viewModel.vmListMovies)
                viewModel.statusDestroyView = false
                //TODO
                Timber.tag("PAGIN-ПЕРЕВОРОТ И ПРОЧЕЕ:").i(viewModel.vmListMovies.size.toString())
                viewModel.vmListMovies.forEach {
                    Timber.tag("PAGIN--------------------").i(it.title+"-------"+it.id)
                }
            }else{
                movieAdapter.addMovies(category.results)
                //TODO
                Timber.tag("PAGIN-СТАНДАРТ:").i(viewModel.vmTotalPage.toString())
                category.results.forEach {
                    Timber.tag("PAGIN--------------------").i(it.title+"-------"+it.id)
                }
            }
            viewModel.vmListMovies = movieAdapter.currentList.toMutableList()
            Timber.tag("PAGIN-movieAdapter.fullList:").i(viewModel.vmListMovies.size.toString())
        }else{
            movieAdapter.submitList(category.results)
        }
        movieAdapter.onItemClick { onItemCityClick(it) }
        updateUiIfShowMoves()
    }

    private fun showError(error: Throwable){
        if(viewModel.listWithPagin){ recyclerScrollListener.stopLoading()}
        if(viewModel.vmListMovies.isEmpty()){
            super.showBaseError(error)
        }else{
            updateUiIfShowMoves()
            countPage = viewModel.vmCountPage
        }
    }

    private fun updateUiIfShowMoves(){
        removeProgress()
        emptyImageView.gone()
        movieRecyclerView.visible()
    }

    private fun onItemCityClick(baseMovie: BaseMovie){
      //TODO реализовать позже
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

    private fun requestMoviesIfPagination(){

        if(viewModel.listWithPagin){
            recyclerScrollListener.startLoading()
            countPage++
        }

        cData?.let {
            when (it) {
                POPULAR -> {
                    Timber.tag("PAGIN-Scroll: countPage:").i(countPage.toString())
                    if(countPage <= viewModel.vmTotalPage){
                        viewModel.popularMovies(it,countPage)
                    }
                }
                TOP_RATED -> {
                    if(countPage <= viewModel.vmTotalPage){
                    viewModel.topRatedMovies(it,  countPage)
                    }
                }
                else -> {}
            }
        }
    }

    private val recyclerScrollListener = object : RecyclerOnScrollListener(movieLayoutManager) {
        override fun onLoadMore() {
            Timber.tag("PAGIN-Scroll:").i("onLoadMore()")
            requestMoviesIfPagination()
        }
    }

}