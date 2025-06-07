package com.suongvong.interviewtest.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.suongvong.interviewtest.R
import com.suongvong.interviewtest.network.response.Article
import com.suongvong.interviewtest.ui.base.BaseFragment

class DetailFragment : BaseFragment<DetailViewModel>(), DetailNavigator, View.OnClickListener {

    private var textTitle: TextView? = null
    private var textContent: TextView? = null
    private var textUrl: TextView? = null
    private lateinit var imageView: ImageView

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
        imageView = findViewById(R.id.imgThumbnail) as ImageView

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
        article?.let {
            textTitle?.text = it.title
            textContent?.text = it.content
            textUrl?.text = it.url

            Glide.with(this)
                .load(it.urlToImage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.image_error)
                .into(imageView)
        }
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.textUrl) {
            val url = textUrl?.text?.toString()
            if (!url.isNullOrBlank()) {
                val action = DetailFragmentDirections.actionDetailFragmentToWebviewFragment(url)
                findNavController().navigate(action)
            }
        }
    }


}
