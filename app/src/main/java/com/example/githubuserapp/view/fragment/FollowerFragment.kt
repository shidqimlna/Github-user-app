package com.example.githubuserapp.view.fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.MainAdapter
import com.example.githubuserapp.utils.ConstantValue.REQUEST_INTERVAL_TIME
import com.example.githubuserapp.utils.ConstantValue.WARNING_TIME
import com.example.githubuserapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.error_warning.*
import kotlinx.android.synthetic.main.fragment_follower.*

class FollowerFragment : Fragment() {
    private lateinit var mainAdapter: MainAdapter
    private lateinit var mainViewModel: MainViewModel

    companion object {
        @JvmStatic
        fun newInstance(username: String?) = FollowerFragment().apply {
            arguments = Bundle().apply {
                putString("username", username)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showWarning(false)
        showLoading(true)

        mainAdapter = MainAdapter()
        mainAdapter.notifyDataSetChanged()

        fragment_follower_recyclerView.setHasFixedSize(true)
        fragment_follower_recyclerView.layoutManager = GridLayoutManager(context, 2)
        fragment_follower_recyclerView.adapter = mainAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainViewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory(activity!!.application)
            ).get(
                MainViewModel::class.java
            )
        setData()
    }

    private fun setData() {
        if (arguments?.getString("username", "") != null) {
            arguments?.let {
                mainViewModel.setUserFollower(it.getString("username", ""))
                loadData()
            }
        } else {
            Handler().postDelayed({
                setData()
            }, REQUEST_INTERVAL_TIME)
        }
    }

    private fun loadData() {
        if (mainViewModel.getErrorMessage().isNotEmpty()) {
            if (mainViewModel.getErrorMessage() == "success") {
                mainViewModel.getUsers().observe(this, Observer { resultItems ->
                    if (resultItems != null) {
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

    private fun showLoading(state: Boolean) {
        if (state) {
            fragment_follower_progressBar_layout.visibility = View.VISIBLE
        } else {
            fragment_follower_progressBar_layout.visibility = View.GONE
        }
    }

    private fun showWarning(state: Boolean) {
        if (state) {
            fragment_follower_warning_layout.visibility = View.VISIBLE
            error_warning_tv.text = mainViewModel.getErrorMessage()
        } else {
            fragment_follower_warning_layout.visibility = View.GONE
            error_warning_tv.text = ""
        }
    }

}