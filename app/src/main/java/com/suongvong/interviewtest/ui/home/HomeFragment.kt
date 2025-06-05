package com.suongvong.interviewtest.ui.home

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
import com.suongvong.interviewtest.network.response.Article
import com.suongvong.interviewtest.ui.base.BaseFragment
import com.suongvong.interviewtest.ui.base.adapter.ItemViewBinder
import com.suongvong.interviewtest.utils.ArticleDataSource
import com.suongvong.interviewtest.utils.HorizontalMarginItemDecoration
import com.suongvong.interviewtest.utils.SearchUtils
import kotlin.math.abs

class HomeFragment: BaseFragment<HomeViewModel>(), HomeNavigator, NewsBinderView.OnItemClickListener {

    private var rvArticle: RecyclerView? = null
    private var vpTopHead :ViewPager2?=null
    private var newsAdapter: NewsAdapter? = null
    private var linearLayoutManager: LinearLayoutManager? = null

    override fun getLayoutId(): Int  = R.layout.fragment_home

    override fun setupViewModel(): HomeViewModel {
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModel() as T
            }
        })[HomeViewModel::class.java]
        return viewModel
    }

    fun ViewPager2.addCarouselEffect(enableZoom: Boolean = true) {
        clipChildren = false    // No clipping the left and right items
        clipToPadding = false   // Show the viewpager in full width without clipping the padding
        offscreenPageLimit = 3  // Render the left and right items
        (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER // Remove the scroll effect

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer((20 * Resources.getSystem().displayMetrics.density).toInt()))
        if (enableZoom) {
            compositePageTransformer.addTransformer { page, position ->
                val r = 1 - abs(position)
                page.scaleY = (0.80f + r * 0.20f)
            }
        }
        setPageTransformer(compositePageTransformer)
    }

    override fun setupView() {
        super.setupView()
        rvArticle = findViewById(R.id.rvArticle) as RecyclerView
        vpTopHead = findViewById(R.id.viewPager) as ViewPager2
        setHasOptionsMenu(true)

    }

    override fun setupData(savedInstanceState: Bundle?) {
        viewModel = setupViewModel()
        viewModel.setNavigator(this)
        setupRecyclerView()
        setupViewPager()

        viewModel.getTopHeadlines()
        viewModel.getNewsEverything(context)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.news_menu, menu)

//        val searchItem = menu.findItem(R.id.action_search)
//        val searchView = searchItem.actionView as SearchView

//        SearchUtils.setDebouncedListener(searchView) { query ->
//            if (query != null) {
//                viewModel.getNewsEverything(context,query)
//            }
//        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_switch_language -> {
                DialogFactory.openLanguageDialog(context){
                    viewModel.getNewsEverything(context)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupRecyclerView(){
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        newsAdapter = NewsAdapter()

        rvArticle?.layoutManager = linearLayoutManager
        rvArticle?.adapter = newsAdapter
        newsAdapter?.register(Article::class.java, NewsBinderView(this) as ItemViewBinder<Article, RecyclerView.ViewHolder>)
    }

    val items = mutableListOf<Article>()
    var adapter2 :CarouselAdapter? = null

    private fun setupViewPager(){
       // setupCarousel()
       // val testlist = listOf<String>("A","b","c","t","p")
        adapter2 = CarouselAdapter(context,items)

        val transformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
            addTransformer { page, position ->
                val scale = 0.85f + (1 - abs(position)) * 0.15f
                page.scaleY = scale
                page.scaleX = scale
                page.alpha = 0.5f + (1 - abs(position)) * 0.5f
            }
        }
      //  vpTopHead?.addCarouselEffect(false)

        vpTopHead?.apply {
            this.adapter = adapter2
            offscreenPageLimit = 3
           // setPageTransformer(transformer)
//            offscreenPageLimit = 3
            clipToPadding = false
            clipChildren = false
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            setPageTransformer(transformer)
        }
      //  adapter?.notifyDataSetChanged()
    }

    private fun setupCarousel(){

        vpTopHead?.offscreenPageLimit = 1

       // val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
       // val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        //val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -30 * position
            page.scaleY = 1 - (0.25f * kotlin.math.abs(position))
            page.alpha = 0.25f + (1 - kotlin.math.abs(position))
        }
        vpTopHead?.setPageTransformer(pageTransformer)
        val itemDecoration = HorizontalMarginItemDecoration(
            requireContext(),
            R.dimen.viewpager_current_item_horizontal_margin
        )
        vpTopHead?.addItemDecoration(itemDecoration)
    }


    override fun onGetTopHeadlinesSuccessful(articles: List<Article>) {


        ArticleDataSource.createDataSource(articles).observe(this){
            items.clear()
            items.addAll(it)
            adapter2?.notifyDataSetChanged()
        }


    }

    override fun onGetTopHeadlinesFail() {

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

        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(article)
        findNavController().navigate(action)
    }




}