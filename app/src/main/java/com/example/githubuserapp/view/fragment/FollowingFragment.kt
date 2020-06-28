package com.example.githubuserapp.view.fragment

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
import com.example.githubuserapp.adapter.GithubAdapter
import com.example.githubuserapp.viewmodel.GithubViewModel
import kotlinx.android.synthetic.main.fragment_following.*

class FollowingFragment : Fragment() {
    lateinit var githubAdapter: GithubAdapter
    lateinit var githubViewModel: GithubViewModel

    companion object {
        fun newInstance(username: String?): FollowingFragment {
            val fragment = FollowingFragment()
            val bundle = Bundle()
            bundle.putString("username", username)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading(true)

        githubAdapter = GithubAdapter()
        githubAdapter.notifyDataSetChanged()

        fragment_following_recyclerView.setHasFixedSize(true)
        fragment_following_recyclerView.layoutManager = GridLayoutManager(context, 2)
        fragment_following_recyclerView.adapter = githubAdapter

        githubViewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory(activity!!.application)
            ).get(
                GithubViewModel::class.java
            )

        loadData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    private fun loadData() {
        if (arguments != null) {
            githubViewModel.setUserFollowing(arguments!!.getString("username", ""))
            githubViewModel.getUsers().observe(this, Observer { resultItems ->
                if (resultItems != null) {
                    githubAdapter.setData(resultItems)
                    showLoading(false)
                }
            })
        } else {
            Handler().postDelayed({
                loadData()
            }, 100)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            fragment_following_progressBar_layout.visibility = View.VISIBLE
        } else {
            fragment_following_progressBar_layout.visibility = View.GONE
        }
    }
}