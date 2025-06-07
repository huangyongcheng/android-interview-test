package com.suongvong.interviewtest.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.suongvong.interviewtest.R
import com.suongvong.interviewtest.adapter.TopHeadLinesCarouselAdapter
import com.suongvong.interviewtest.adapter.NewsAdapter
import com.suongvong.interviewtest.binder.NewsBinderView
import com.suongvong.interviewtest.dialog.DialogFactory
import com.suongvong.interviewtest.extentions.addCarouselEffect
import com.suongvong.interviewtest.network.response.ApiErrorResponse
import com.suongvong.interviewtest.network.response.Article
import com.suongvong.interviewtest.ui.base.BaseFragment
import com.suongvong.interviewtest.ui.base.adapter.ItemViewBinder
import com.suongvong.interviewtest.utils.ArticleDataSource
import com.suongvong.interviewtest.utils.AutoSlideViewPagerUtil

// cơ chế load more
// save data từ database hiện thị offline
// them refresh layout
class HomeFragment : BaseFragment<HomeViewModel>(), HomeNavigator, NewsBinderView.OnItemClickListener,
    TopHeadLinesCarouselAdapter.TopHeadLinesCarouselListener {

    private var rvArticle: RecyclerView? = null
    private var vpTopHead: ViewPager2? = null
    private var slArticle: LinearLayout? = null

    private var slTopHeadLines: LinearLayout? = null
    private var newsAdapter: NewsAdapter? = null
    private var linearLayoutManager: LinearLayoutManager? = null
    private var autoSlideUtil: AutoSlideViewPagerUtil? = null


    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun setupViewModel(): HomeViewModel {
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModel() as T
            }
        })[HomeViewModel::class.java]
        return viewModel
    }

    override fun setupView() {
        super.setupView()
        rvArticle = findViewById(R.id.rvArticle) as RecyclerView
        vpTopHead = findViewById(R.id.viewPager) as ViewPager2
        slArticle = findViewById(R.id.slArticle) as LinearLayout
        slTopHeadLines = findViewById(R.id.slTopHeadLines) as LinearLayout
        setHasOptionsMenu(true)

    }

    override fun setupData(savedInstanceState: Bundle?) {
        viewModel = setupViewModel()
        viewModel.setNavigator(this)
        setupRecyclerView()
        setupViewPager()

        startShimmerByViews(slArticle)
        startShimmerByViews(slTopHeadLines)
        viewModel.getTopHeadlines()
        viewModel.getNewsEverything(context)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.news_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

        val menuItem = menu.findItem(R.id.action_switch_language)
        val icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_language)
        icon?.setBounds(0, 0, 48, 48)
        menuItem.icon = icon
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_switch_language -> {
                DialogFactory.openLanguageDialog(context) {
                    viewModel.getNewsEverything(context)
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupRecyclerView() {
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        newsAdapter = NewsAdapter()

        rvArticle?.layoutManager = linearLayoutManager
        rvArticle?.adapter = newsAdapter
        newsAdapter?.register(Article::class.java, NewsBinderView(this) as ItemViewBinder<Article, RecyclerView.ViewHolder>)
    }

    private val items = mutableListOf<Article>()
    private var topHeadLinesCarouselAdapter: TopHeadLinesCarouselAdapter? = null

    private fun setupViewPager() {
        topHeadLinesCarouselAdapter = TopHeadLinesCarouselAdapter( items, this)
        vpTopHead?.addCarouselEffect(true)
        vpTopHead?.adapter = topHeadLinesCarouselAdapter
        autoSlideUtil = AutoSlideViewPagerUtil(vpTopHead)

    }

    override fun onResume() {
        super.onResume()
        autoSlideUtil?.start()
    }

    override fun onPause() {
        super.onPause()
        autoSlideUtil?.stop()
    }

    override fun onGetTopHeadlinesSuccessful(articles: List<Article>) {


        ArticleDataSource.createDataSource(articles).observe(this) {
            items.clear()
            items.addAll(it)
            topHeadLinesCarouselAdapter?.notifyDataSetChanged()
            stopShimmerByViews(slTopHeadLines)
        }


    }

    override fun onGetTopHeadlinesFail(apiErrorResponse: ApiErrorResponse) {
        Toast.makeText(context, apiErrorResponse.message, Toast.LENGTH_LONG).show()
        stopShimmerByViews(slTopHeadLines)
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

    }

    override fun onItemTopHeadlineClick(article: Article) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(article)
        findNavController().navigate(action)
    }

    override fun onItemClick(article: Article) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(article)
        findNavController().navigate(action)
    }

}