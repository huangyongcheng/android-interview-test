package com.suongvong.interviewtest.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.suongvong.interviewtest.R
import com.suongvong.interviewtest.binder.NewsBinderView
import com.suongvong.interviewtest.network.response.Article
import com.suongvong.interviewtest.ui.base.BaseFragment


class DetailFragment : BaseFragment<DetailViewModel>(), DetailNavigator, NewsBinderView.OnItemClickListener, View.OnClickListener {


    private var textTitle: TextView? = null
    private var textContent: TextView? = null
    private var textUrl: TextView? = null
    private lateinit var imageView:ImageView

    private var article: Article? = null
    override fun getLayoutId(): Int = R.layout.fragment_detail

    override fun setupViewModel(): DetailViewModel {
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DetailViewModel() as T
            }
        })[DetailViewModel::class.java]
        return viewModel
    }

    override fun setupView() {
        super.setupView()
        textTitle = findViewById(R.id.textTitle) as TextView
        textContent = findViewById(R.id.textContent) as TextView
        textUrl = findViewById(R.id.textUrl) as TextView
        imageView = findViewById(R.id.imageView) as ImageView

        textUrl?.setOnClickListener(this)

    }

    override fun getArgumentIntent() {
        super.getArgumentIntent()
        article = arguments?.let {
            DetailFragmentArgs.fromBundle(it).article
        }
    }

    override fun setupData(savedInstanceState: Bundle?) {
        viewModel = setupViewModel()
        viewModel.setNavigator(this)
        loadNewsContent()

    }

    private fun loadNewsContent() {
        textTitle?.text = article?.title
        textContent?.text = article?.content
        textUrl?.text = article?.url
        Glide.with(requireActivity()).load(article?.urlToImage).into(imageView)
    }


    override fun onGetTopHeadlinesSuccessful(articles: List<Article>) {


    }

    override fun onGetTopHeadlinesFail() {

    }

    override fun onApiFailure() {
        TODO("Not yet implemented")
    }

    override fun onItemClick(contact: Article?) {
        TODO("Not yet implemented")
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.textUrl -> {
                val url = textUrl?.text.toString()
                if (url.isNotBlank()) {
                    val action = DetailFragmentDirections.actionDetailFragmentToWebviewFragment(url)
                    findNavController().navigate(action)
                }
            }
        }
    }


}