package com.example.githubuserapp.view.fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
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
import kotlinx.android.synthetic.main.fragment_follower.*

class FollowerFragment : Fragment() {
    lateinit var githubAdapter: GithubAdapter
    lateinit var githubViewModel: GithubViewModel
    var i = 0

    companion object {
        @JvmStatic
        fun newInstance(username: String?): FollowerFragment {
            val fragment = FollowerFragment()
            val bundle = Bundle()
            Log.i("FwerInstance1", "$username")
            bundle.putString("username", username)
            fragment.arguments = bundle
            Log.i("FwerInstance2", "${fragment.arguments?.getString("username", "")}")
            return fragment
        }
        /*
        @JvmStatic
        fun newInstance(username: String?) = FollowerFragment().apply {
            arguments = Bundle().apply {
                putString("username", username)
                Log.i("FwerInstance1", "$username")
                Log.i("FwerInstance2", "${arguments?.getString("username", "")}")
            }
        }*/
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("FwerOnCreate", "${arguments?.getString("username", "")}")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("FwerOnView", "${arguments?.getString("username", "")}")

        showLoading(true)

        githubAdapter = GithubAdapter()
        githubAdapter.notifyDataSetChanged()

        fragment_follower_recyclerView.setHasFixedSize(true)
        fragment_follower_recyclerView.layoutManager = GridLayoutManager(context, 2)
        fragment_follower_recyclerView.adapter = githubAdapter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.i("FwerOnCreateView", "${arguments?.getString("username", "")}")
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.i("FwerOnAttach", "${arguments?.getString("username", "")}")
        githubViewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory(activity!!.application)
            ).get(
                GithubViewModel::class.java
            )
        loadData()
    }

    private fun loadData() {
        if (arguments?.getString("username", "") != null) {
            Log.i("FwerLoadData", arguments!!.getString("username", ""))
            githubViewModel.setUserFollower(arguments!!.getString("username", ""))
            githubViewModel.getUsers().observe(this, Observer { resultItems ->
                if (resultItems != null) {
                    githubAdapter.setData(resultItems)
                    showLoading(false)
                }
            })
        } else {
            if(i<50){
                i++
                Log.i("FwerLoadData Else", "${arguments?.getString("username", "")}")
                Handler().postDelayed({
                    loadData()
                }, 100)
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            fragment_follower_progressBar_layout.visibility = View.VISIBLE
        } else {
            fragment_follower_progressBar_layout.visibility = View.GONE
        }
    }
}