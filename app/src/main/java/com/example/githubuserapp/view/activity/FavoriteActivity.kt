package com.example.githubuserapp.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.MainAdapter
import com.example.githubuserapp.database.AppDatabase
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {
    private var mainAdapter = MainAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        activity_favorite_recyclerView.setHasFixedSize(true)
        activity_favorite_recyclerView.layoutManager = GridLayoutManager(this, 2)
    }

    override fun onResume() {
        super.onResume()
        val appDatabase = AppDatabase.initDB(this)
        mainAdapter.setData(appDatabase.userDAO().getAll())
        mainAdapter.notifyDataSetChanged()
        activity_favorite_recyclerView.adapter = mainAdapter
    }
}