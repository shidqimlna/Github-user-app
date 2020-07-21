package com.example.githubuserapp.adapter

import android.content.Context
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.githubuserapp.R
import com.example.githubuserapp.view.fragment.FollowerFragment
import com.example.githubuserapp.view.fragment.FollowingFragment

class SectionsPagerAdapter(private val mContext: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private var username: String? = null

    fun setUsername(username: String?) {
        this.username = username
    }

    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.follower, R.string.following)
    override fun getItem(position: Int): Fragment {

        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowerFragment.newInstance(this.username)
            1 -> fragment = FollowingFragment.newInstance(this.username)
        }
        return fragment as Fragment
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 2
    }
}