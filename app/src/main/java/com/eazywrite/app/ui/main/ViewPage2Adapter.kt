package com.eazywrite.app.ui.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.eazywrite.app.ui.bill.fragment.MainFragment
import com.eazywrite.app.ui.chart.ChartFragment
import com.eazywrite.app.ui.explore.ExploreFragment
import com.eazywrite.app.ui.home.HomeFragment
import com.eazywrite.app.ui.profile.ProfileFragment

class ViewPage2Adapter: FragmentStateAdapter {

    private lateinit var paddingValues: PaddingValues;
    constructor(fragmentActivity: FragmentActivity,paddingValues: PaddingValues) : super(fragmentActivity){
        this.paddingValues = paddingValues
    }
    constructor(fragment: Fragment) : super(fragment)
    constructor(fragmentManager: FragmentManager, lifecycle: Lifecycle) : super(
        fragmentManager,
        lifecycle
    )


    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0-> HomeFragment(paddingValues)
            1 -> ChartFragment(paddingValues)
            2 -> ExploreFragment(paddingValues)
            else -> ProfileFragment(paddingValues)
        }
    }
}