package com.example.favoriteapp.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.favoriteapp.R
import com.example.favoriteapp.model.UserItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        showLoading(true)

        val user = intent.getParcelableExtra<UserItem>("user")

        loadData(user)
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

}
