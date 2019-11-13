package com.my_project.moviesapp.presentation.web_review

import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.my_project.moviesapp.R

import com.my_project.moviesapp.presentation.base.BaseFragment
import com.my_project.moviesapp.utilities.mainActivity
import kotlinx.android.synthetic.main.fragment_web_review.*


/**
 * Created Максим on 13.11.2019.
 * Copyright © Max
 */
class WebViewReviewFragment : BaseFragment() {

    companion object {
        const val WEB_REVIEW_KEY = "web_review_key"
        fun newInstance(url: String): Fragment {
            return WebViewReviewFragment().apply {
                arguments = Bundle().apply {
                    putString(WEB_REVIEW_KEY, url)
                }
            }
        }
    }

    private lateinit var viewModel: WebViewReviewViewModel
    private var url: String? = null

    override fun getLayoutRes(): Int = R.layout.fragment_web_review

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WebViewReviewViewModel::class.java)
        init()
    }

    private fun init(){
        updateToolBar()
        initData()
        showReview()
    }

    private fun initData() {
        url = arguments?.getString(WEB_REVIEW_KEY)
    }

    private fun updateToolBar() {
        mainActivity?.apply {
            updateToolBar(true)
            titleToolBar.text = rProvider.getString(R.string.web_version_review)
        }
    }

    private fun showReview() {
        reviewWebView.settings.javaScriptEnabled = true
        val webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return false
            }
        }
        reviewWebView.webViewClient = webViewClient

        url?.let {
            reviewWebView.loadUrl(it)
        }
    }

}