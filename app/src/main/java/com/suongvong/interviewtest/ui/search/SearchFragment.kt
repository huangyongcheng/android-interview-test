package com.suongvong.interviewtest.ui.search

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.suongvong.interviewtest.R
import com.suongvong.interviewtest.adapter.NewsAdapter
import com.suongvong.interviewtest.binder.SearchBinderView
import com.suongvong.interviewtest.constants.SEARCH_PARAMS
import com.suongvong.interviewtest.model.SearchParams
import com.suongvong.interviewtest.network.response.ApiErrorResponse
import com.suongvong.interviewtest.network.response.Article
import com.suongvong.interviewtest.ui.base.BaseFragment
import com.suongvong.interviewtest.ui.base.adapter.ItemViewBinder
import com.suongvong.interviewtest.utils.ArticleDataSource

class SearchFragment : BaseFragment<SearchViewModel>(), SearchNavigator, SearchBinderView.OnItemClickListener {

    private var rvArticle: RecyclerView? = null
    private var newsAdapter: NewsAdapter? = null
    private var searchParams: SearchParams? = null
    private var slArticle: LinearLayout? = null
    private var tvNoData: TextView? = null

    override fun getLayoutId(): Int = R.layout.fragment_search

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
        tvNoData = findViewById(R.id.tvNoData) as TextView
        slArticle = findViewById(R.id.slArticle) as LinearLayout
    }

    override fun setupData(savedInstanceState: Bundle?) {
        viewModel = setupViewModel()
        viewModel.setNavigator(this)
        setupRecyclerView()
        startShimmerByViews(slArticle)
        viewModel.searchNews(context, searchParams)
    }

    override fun getArgumentIntent() {
        super.getArgumentIntent()
        searchParams = arguments?.let {
            it.getParcelable(SEARCH_PARAMS)
        }
    }


    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        rvArticle?.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rvArticle?.adapter = newsAdapter
        newsAdapter?.register(Article::class.java, SearchBinderView(this) as ItemViewBinder<Article, RecyclerView.ViewHolder>)
    }

    override fun onGetNewsEverythingSuccessful(articles: List<Article>) {
        ArticleDataSource.createDataSource(articles).observe(this) {
            newsAdapter?.submitList(null)
            newsAdapter?.submitList(it)
            stopShimmerByViews(slArticle)
        }

    }

    override fun onGetNewsEverythingFail(apiErrorResponse: ApiErrorResponse) {
        Toast.makeText(context, apiErrorResponse.message, Toast.LENGTH_LONG).show()
        stopShimmerByViews(slArticle)

    }

    override fun onApiFailure() {
        TODO("Not yet implemented")
    }

    override fun onItemClick(article: Article) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(article)
        findNavController().navigate(action)
    }


}