package com.example.githubuserapp.adapter

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuserapp.BuildConfig
import com.example.githubuserapp.R
import com.example.githubuserapp.model.UsersItem
import com.example.githubuserapp.view.activity.DetailActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_user.view.*
import kotlin.random.Random

class GithubAdapter : RecyclerView.Adapter<GithubAdapter.ListViewHolder>() {

    private val listUsers = ArrayList<UsersItem>()

    fun setData(items: ArrayList<UsersItem>) {
        listUsers.clear()
        listUsers.addAll(items)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(user: UsersItem?) {
            user?.let {
                with(itemView) {
                    Picasso.get().load(it.avatar_url).into(item_user_image)
                    item_user_tv_username.text = "@${it.login}"
                    item_user_image_bg.setBackgroundColor( Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256)) )
                    item_user_cardView.setOnClickListener {
                        val intent = Intent(context, DetailActivity::class.java)
                        intent.putExtra("user", user)
                        context.startActivity(intent)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): GithubAdapter.ListViewHolder =
        ListViewHolder(
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user, viewGroup, false)
        )

    override fun onBindViewHolder(holder: GithubAdapter.ListViewHolder, position: Int) {
        holder.bindView(listUsers[position])
    }

    override fun getItemCount(): Int = listUsers.size

}