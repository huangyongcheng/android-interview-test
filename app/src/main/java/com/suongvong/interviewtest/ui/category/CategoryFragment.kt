package com.suongvong.interviewtest.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.suongvong.interviewtest.R
import com.suongvong.interviewtest.adapter.CategoryTabAdapter
import com.suongvong.interviewtest.constants.CATEGORY_POSITION
import com.suongvong.interviewtest.extentions.setOnTabSelected
import com.suongvong.interviewtest.interfaces.OnTabSelectedListener

class CategoryFragment : Fragment(), OnTabSelectedListener {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    // --- Lifecycle Methods ---
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews(view)
        setupTabs()
    }

    // --- Setup Methods ---
    private fun setupViews(view: View) {
        viewPager = view.findViewById(R.id.viewPager)
        tabLayout = view.findViewById(R.id.tabLayout)
        viewPager.isUserInputEnabled = false
    }

    private fun setupTabs() {
        val tabTitles = resources.getStringArray(R.array.news_categories)
        val fragments = tabTitles.map { TabItemFragment(it) }

        val adapter = CategoryTabAdapter(requireActivity(), fragments)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        tabLayout.setOnTabSelected(this)

        val defaultPosition = arguments?.getInt(CATEGORY_POSITION) ?: 0
        viewPager.currentItem = defaultPosition
    }

    // --- Tab Event ---
    override fun onTabSelected(position: Int) {
        viewPager.currentItem = position
    }
}
