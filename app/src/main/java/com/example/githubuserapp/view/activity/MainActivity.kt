package com.example.githubuserapp.view.activity

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.GithubAdapter
import com.example.githubuserapp.viewmodel.GithubViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    lateinit var githubAdapter: GithubAdapter
    lateinit var githubViewModel: GithubViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showLoading(true)

        activity_main_swipeRefreshLayout.setOnRefreshListener(this)
        activity_main_swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent)

        githubAdapter = GithubAdapter()
        githubAdapter.notifyDataSetChanged()

        activity_main_recyclerView.setHasFixedSize(true)
        activity_main_recyclerView.layoutManager = GridLayoutManager(this, 2)
        activity_main_recyclerView.adapter = githubAdapter

        githubViewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                GithubViewModel::class.java
            )

        onRefresh("")

        activity_main_searchView.setOnClickListener { activity_main_searchView.isIconified = false }

        activity_main_searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(keyword: String): Boolean {
                onRefresh(keyword)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun loadData(keyword: String) {
        showLoading(true)
        if (keyword.isNotEmpty()) githubViewModel.setUsers(keyword)
        else {
            val random = (1..60000000).random()
            githubViewModel.setUsers(random)
        }

        githubViewModel.getUsers().observe(this, Observer { resultItems ->
            if (resultItems != null) {
                githubAdapter.setData(resultItems)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            activity_main_progressBar_layout.visibility = View.VISIBLE
        } else {
            activity_main_swipeRefreshLayout.isRefreshing = false
            Handler().postDelayed({
                activity_main_progressBar_layout.visibility = View.GONE
            }, 1000)
        }
    }

    override fun onRefresh() {
        activity_main_swipeRefreshLayout.isRefreshing = true
        loadData("")
    }

    private fun onRefresh(keyword: String) {
        activity_main_swipeRefreshLayout.post {
            loadData(keyword)
        }
    }
}