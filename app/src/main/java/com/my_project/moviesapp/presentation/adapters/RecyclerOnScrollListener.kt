package com.my_project.moviesapp.presentation.adapters

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created Максим on 07.11.2019.
 * Copyright © Max
 */
abstract class RecyclerOnScrollListener (private val mLinearLayoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {

    private val visibleThreshold: Int = 3
    private var lastVisibleItem: Int = 0
    private var totalItemCount: Int = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        totalItemCount = mLinearLayoutManager.itemCount
        lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition()
        if (totalItemCount <= (lastVisibleItem + visibleThreshold)) {
            onLoadMore()
        }
    }

    abstract fun onLoadMore()
}