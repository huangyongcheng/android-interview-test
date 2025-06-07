package com.suongvong.interviewtest.ui.category

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.suongvong.interviewtest.R
import com.suongvong.interviewtest.adapter.NewsAdapter
import com.suongvong.interviewtest.binder.NewsBinderView
import com.suongvong.interviewtest.network.response.ApiErrorResponse
import com.suongvong.interviewtest.network.response.Article
import com.suongvong.interviewtest.ui.base.BaseFragment
import com.suongvong.interviewtest.ui.base.adapter.ItemViewBinder
import com.suongvong.interviewtest.utils.ArticleDataSource

class TabItemFragment(var category: String) : BaseFragment<CategoryViewModel>(), CategoryNavigator, NewsBinderView.OnItemClickListener {

    private var rvArticle: RecyclerView? = null
    private var newsAdapter: NewsAdapter? = null
    private var linearLayoutManager: LinearLayoutManager? = null

    override fun getLayoutId(): Int = R.layout.fragment_tab_item

    override fun setupViewModel(): CategoryViewModel {
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CategoryViewModel() as T
            }
        })[CategoryViewModel::class.java]
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
        viewModel.getTopHeadlines(category = category)
    }


    private fun setupRecyclerView() {
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        newsAdapter = NewsAdapter()

        rvArticle?.layoutManager = linearLayoutManager
        rvArticle?.adapter = newsAdapter
        newsAdapter?.register(Article::class.java, NewsBinderView(this) as ItemViewBinder<Article, RecyclerView.ViewHolder>)
    }


    override fun onGetTopHeadlinesSuccessful(articles: List<Article>) {
        ArticleDataSource.createDataSource(articles).observe(this) {
            newsAdapter?.submitList(null)
            newsAdapter?.submitList(it)
        }
    }

    override fun onGetTopHeadlinesFail(apiErrorResponse: ApiErrorResponse) {
        Toast.makeText(context, apiErrorResponse.message, Toast.LENGTH_LONG).show()

    }

    override fun onApiFailure() {

    }

    override fun onItemClick(contact: Article?) {

        val action =  CategoryFragmentDirections.actionCategoryFragmentToDetailFragment(contact)
        findNavController().navigate(action)
    }


}