package com.suongvong.interviewtest.ui.category

import android.os.Bundle
import android.widget.LinearLayout
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

class TabItemFragment(private val category: String) :
    BaseFragment<CategoryViewModel>(),
    CategoryNavigator,
    NewsBinderView.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var shimmerLayout: LinearLayout
    private lateinit var newsAdapter: NewsAdapter

    override fun getLayoutId(): Int = R.layout.fragment_tab_item

    override fun setupViewModel(): CategoryViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CategoryViewModel() as T
            }
        })[CategoryViewModel::class.java]
    }

    override fun setupView() {
        recyclerView = findViewById(R.id.rvArticle) as RecyclerView
        shimmerLayout = findViewById(R.id.slArticle) as LinearLayout
        setupRecyclerView()
    }

    override fun setupData(savedInstanceState: Bundle?) {
        viewModel.setNavigator(this)
        startShimmerByViews(shimmerLayout)
        viewModel.getTopHeadlines(category)
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = newsAdapter
        }

        newsAdapter.register(
            Article::class.java,
            NewsBinderView(this) as ItemViewBinder<Article, RecyclerView.ViewHolder>
        )
    }

    override fun onGetTopHeadlinesSuccessful(articles: List<Article>) {
        ArticleDataSource.createDataSource(articles).observe(viewLifecycleOwner) { list ->
            newsAdapter.submitList(null)
            newsAdapter.submitList(list)
            stopShimmerByViews(shimmerLayout)
        }
    }

    override fun onGetTopHeadlinesFail(apiErrorResponse: ApiErrorResponse) {
        Toast.makeText(context, apiErrorResponse.message, Toast.LENGTH_LONG).show()
        stopShimmerByViews(shimmerLayout)
    }

    override fun onApiFailure() {
        stopShimmerByViews(shimmerLayout)
        Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(article: Article) {
        val action = CategoryFragmentDirections.actionCategoryFragmentToDetailFragment(article)
        findNavController().navigate(action)
    }
}
