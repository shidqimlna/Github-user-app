package com.example.githubuserapp.view.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.SectionsPagerAdapter
import com.example.githubuserapp.data.database.MainDatabase
import com.example.githubuserapp.model.UserItem
import com.example.githubuserapp.utils.ConstantValue.REQUEST_INTERVAL_TIME
import com.example.githubuserapp.utils.ConstantValue.WARNING_TIME
import com.example.githubuserapp.viewmodel.MainViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.error_warning.*

class DetailActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        showWarning(false)
        showLoading(true)

        val user = intent.getParcelableExtra<UserItem>("user")

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.setUsername(user?.login!!)
        actvity_detail_view_pager.adapter = sectionsPagerAdapter
        actvity_detail_tabs.setupWithViewPager(actvity_detail_view_pager)
        supportActionBar?.elevation = 0f

        setData(user)
    }

    private fun setData(user: UserItem) {
        val appDatabase = MainDatabase.initDB(this)
        if (appDatabase.userDAO().exists(user.id!!)) {
            activity_detail_fab_favorite.setImageResource(R.drawable.ic_round_favorite_24_red)
            loadData(user)
            activity_detail_fab_favorite.setOnClickListener {
                appDatabase.userDAO().delete(user)
                setData(user)
            }
        } else {
            activity_detail_fab_favorite.setImageResource(R.drawable.ic_round_favorite_24_black)
            mainViewModel =
                ViewModelProvider(
                    this,
                    ViewModelProvider.AndroidViewModelFactory(application)
                ).get(
                    MainViewModel::class.java
                )
            mainViewModel.setUserDetail(user.login!!)
            getData(appDatabase)
        }
    }

    private fun getData(mainDatabase: MainDatabase) {
        if (mainViewModel.getErrorMessage().isNotEmpty()) {
            if (mainViewModel.getErrorMessage() == "success") {
                if (mainViewModel.getUserDetail().avatar_url != null) {
                    val user: UserItem = mainViewModel.getUserDetail()
                    activity_detail_fab_favorite.setOnClickListener {
                        mainDatabase.userDAO().insert(user)
                        setData(user)
                    }
                    loadData(user)
                } else {
                    Handler().postDelayed({
                        getData(mainDatabase)
                    }, REQUEST_INTERVAL_TIME)
                }
            } else {
                showLoading(false)
                showWarning(true)
                Handler().postDelayed({
                    showWarning(false)
                }, WARNING_TIME)
            }
        } else {
            Handler().postDelayed({
                getData(mainDatabase)
            }, REQUEST_INTERVAL_TIME)
        }
    }

    private fun loadData(user: UserItem) {
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
                activity_detail_ll_blog.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(user.blog)
                    startActivity(intent)
                }
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
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            activity_detail_progressBar_layout.visibility = View.VISIBLE
        } else {
            activity_detail_progressBar_layout.visibility = View.GONE
        }
    }

    private fun showWarning(state: Boolean) {
        if (state) {
            activity_detail_warning_layout.visibility = View.VISIBLE
            error_warning_tv.text = mainViewModel.getErrorMessage()
        } else {
            activity_detail_warning_layout.visibility = View.GONE
            error_warning_tv.text = ""
        }
    }

}
