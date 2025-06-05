package com.suongvong.interviewtest.ui.search

import android.content.res.Resources
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.suongvong.interviewtest.R
import com.suongvong.interviewtest.adapter.CarouselAdapter
import com.suongvong.interviewtest.adapter.NewsAdapter
import com.suongvong.interviewtest.binder.NewsBinderView
import com.suongvong.interviewtest.dialog.DialogFactory
import com.suongvong.interviewtest.model.SearchParams
import com.suongvong.interviewtest.network.response.Article
import com.suongvong.interviewtest.ui.base.BaseFragment
import com.suongvong.interviewtest.ui.base.adapter.ItemViewBinder
import com.suongvong.interviewtest.ui.webview.WebViewFragmentArgs
import com.suongvong.interviewtest.utils.ArticleDataSource
import com.suongvong.interviewtest.utils.HorizontalMarginItemDecoration
import com.suongvong.interviewtest.utils.SearchUtils
import kotlin.math.abs

class SearchFragment: BaseFragment<SearchViewModel>(), SearchNavigator, NewsBinderView.OnItemClickListener {

    private var rvArticle: RecyclerView? = null
    private var newsAdapter: NewsAdapter? = null
    private var linearLayoutManager: LinearLayoutManager? = null
    private var searchParams :SearchParams?=null

    override fun getLayoutId(): Int  = R.layout.fragment_search

    override fun setupViewModel(): SearchViewModel {
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SearchViewModel() as T
            }
        })[SearchViewModel::class.java]
        return viewModel
    }


    override fun setupView() {
        super.setupView()
        rvArticle = findViewById(R.id.rvArticle) as RecyclerView

    }

    override fun setupData(savedInstanceState: Bundle?) {
        viewModel = setupViewModel()
        viewModel.setNavigator(this)
        setupRecyclerView()
        viewModel.searchNews(context, searchParams)
    }

    override fun getArgumentIntent() {
        super.getArgumentIntent()
        searchParams = arguments?.let {
            SearchFragmentArgs.fromBundle(it).searchParams
        }

    }



    private fun setupRecyclerView(){
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        newsAdapter = NewsAdapter()

        rvArticle?.layoutManager = linearLayoutManager
        rvArticle?.adapter = newsAdapter
        newsAdapter?.register(Article::class.java, NewsBinderView(this) as ItemViewBinder<Article, RecyclerView.ViewHolder>)
    }

    override fun onGetNewsEverythingSuccessful(articles: List<Article>) {
        ArticleDataSource.createDataSource(articles).observe(this){
            newsAdapter?.submitList(null)
            newsAdapter?.submitList(it)
        }

    }

    override fun onGetNewsEverythingFail() {

    }

    override fun onItemClick(article: Article?) {

        val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(article)
        findNavController().navigate(action)
    }




}