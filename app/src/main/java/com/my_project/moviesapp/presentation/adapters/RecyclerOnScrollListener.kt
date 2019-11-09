package com.my_project.moviesapp.presentation.adapters

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created Максим on 07.11.2019.
 * Copyright © Max
 */
abstract class RecyclerOnScrollListener(private val mLinearLayoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {

    private val visibleThreshold: Int = 2
    private var lastVisibleItem: Int = 0
    private var totalItemCount: Int = 0
    var loading = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        totalItemCount = mLinearLayoutManager.itemCount
        lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition()
        if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
            onLoadMore()
            loading = true
        }
    }

    fun stopLoading() {
        loading = false
    }

    fun startLoading() {
        loading = true
    }

    abstract fun onLoadMore()
}