package com.example.githubuserapp.view.activity

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.SectionsPagerAdapter
import com.example.githubuserapp.model.UserDetailItem
import com.example.githubuserapp.model.UsersItem
import com.example.githubuserapp.view.fragment.FollowerFragment
import com.example.githubuserapp.view.fragment.FollowingFragment
import com.example.githubuserapp.viewmodel.GithubViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    lateinit var githubViewModel: GithubViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        showLoading(true)

        val intent = intent.getParcelableExtra<UsersItem>("user")
        FollowerFragment.newInstance( intent.login )
        Log.i("Detail FwerInstance", intent.login)
        FollowingFragment.newInstance( intent.login )

        githubViewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                GithubViewModel::class.java
            )

        githubViewModel.setUserDetail(intent.login)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)
        supportActionBar?.elevation = 0f
        loadData()
    }

    private fun loadData() {
        if (githubViewModel.getUserDetail().avatar_url != null) {
            val user: UserDetailItem = githubViewModel.getUserDetail()
            user.let {
                Picasso.get().load(it.avatar_url).into(activity_detail_image_profile)

                activity_detail_tv_name.text = it.name
                activity_detail_tv_username.text = "@${it.login}"
                if (it.location != null) {
                    activity_detail_ll_location.visibility = View.VISIBLE
                    activity_detail_tv_location.text = it.location
                }
                if (it.company != null) {
                    activity_detail_ll_company.visibility = View.VISIBLE
                    activity_detail_tv_company.text = it.company
                }
                if (it.blog!!.isNotEmpty()) {
                    activity_detail_ll_blog.visibility = View.VISIBLE
                    activity_detail_tv_blog.text = it.blog
                }
                if (it.email != null) {
                    activity_detail_ll_email.visibility = View.VISIBLE
                    activity_detail_tv_email.text = it.email
                }
                if (it.bio != null) {
                    activity_detail_tv_bio.visibility = View.VISIBLE
                    activity_detail_tv_bio.text = it.bio
                }
                activity_detail_tv_follower.text = it.followers
                activity_detail_tv_following.text = it.following
                showLoading(false)
            }
        } else {
            Handler().postDelayed({
                loadData()
            }, 100)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            activity_detail_progressBar_layout.visibility = View.VISIBLE
        } else {
            activity_detail_progressBar_layout.visibility = View.GONE
        }
    }
}
