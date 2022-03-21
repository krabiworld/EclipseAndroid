package com.eclipse.dashboard.ui.server.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.eclipse.dashboard.ui.server.GeneralFragment

class ViewPager2Adapter(
	fragmentManager: FragmentManager,
	lifecycle: Lifecycle
	) : FragmentStateAdapter(fragmentManager, lifecycle) {
	override fun getItemCount() = 1 // After adding new fragment, increment this value by one.

	override fun createFragment(position: Int): Fragment {
		return when (position) {
			0 -> GeneralFragment()
			else -> throw IllegalStateException()
		}
	}
}
