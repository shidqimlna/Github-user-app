package com.example.githubuserapp.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.MainAdapter
import com.example.githubuserapp.utils.ConstantValue.ESTIMATED_TOTAL_GITHUB_USER
import com.example.githubuserapp.utils.ConstantValue.LOADING_TIME
import com.example.githubuserapp.utils.ConstantValue.REQUEST_INTERVAL_TIME
import com.example.githubuserapp.utils.ConstantValue.WARNING_TIME
import com.example.githubuserapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error_warning.*


class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var mainAdapter: MainAdapter
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showWarning(false)
        showLoading(true)

        activity_main_favorite_icon.setOnClickListener {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }
        activity_main_alarm_icon.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

        activity_main_swipeRefreshLayout.setOnRefreshListener(this)
        activity_main_swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent)

        mainAdapter = MainAdapter()
        mainAdapter.notifyDataSetChanged()

        activity_main_recyclerView.setHasFixedSize(true)
        activity_main_recyclerView.layoutManager = GridLayoutManager(this, 2)
        activity_main_recyclerView.adapter = mainAdapter

        mainViewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                MainViewModel::class.java
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

    private fun setData(keyword: String) {
        showLoading(true)
        showWarning(false)
        if (keyword.isNotEmpty()) mainViewModel.setUsers(keyword)
        else {
            val random = (1..ESTIMATED_TOTAL_GITHUB_USER).random()
            mainViewModel.setUsers(random)
        }
        loadData()
    }

    private fun loadData() {
        if (mainViewModel.getErrorMessage().isNotEmpty()) {
            if (mainViewModel.getErrorMessage() == "success") {
                mainViewModel.getUsers().observe(this, Observer { resultItems ->
                    if (resultItems != null) {
                        //mainAdapter.listUsers = resultItems
                        mainAdapter.setData(resultItems)
                        showLoading(false)
                    } else {
                        Handler().postDelayed({
                            loadData()
                        }, REQUEST_INTERVAL_TIME)
                    }
                })
            } else {
                showLoading(false)
                showWarning(true)
                Handler().postDelayed({
                    showWarning(false)
                }, WARNING_TIME)
            }
        } else {
            Handler().postDelayed({
                loadData()
            }, REQUEST_INTERVAL_TIME)
        }
    }

    override fun onRefresh() {
        activity_main_swipeRefreshLayout.isRefreshing = true
        setData("")
    }

    private fun onRefresh(keyword: String) {
        activity_main_swipeRefreshLayout.post {
            setData(keyword)
        }
    }

    fun showPopupMenu(view: View) {
        val popup = PopupMenu(this, view)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.menu, popup.menu)
        popup.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            activity_main_progressBar_layout.visibility = View.VISIBLE
        } else {
            activity_main_swipeRefreshLayout.isRefreshing = false
            Handler().postDelayed({
                activity_main_progressBar_layout.visibility = View.GONE
            }, LOADING_TIME)
        }
    }

    private fun showWarning(state: Boolean) {
        if (state) {
            activity_main_warning_layout.visibility = View.VISIBLE
            error_warning_tv.text = mainViewModel.getErrorMessage()
        } else {
            activity_main_warning_layout.visibility = View.GONE
            error_warning_tv.text = ""
        }
    }

}