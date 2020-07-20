package com.example.githubuserapp.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.MainAdapter
import com.example.githubuserapp.data.database.MainDatabase
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {
    private var mainAdapter = MainAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        activity_favorite_recyclerView.setHasFixedSize(true)
        activity_favorite_recyclerView.layoutManager = GridLayoutManager(this, 2)
        activity_favorite_recyclerView.adapter = mainAdapter

        /*val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadNotesAsync()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)
        if (savedInstanceState == null) {
            loadNotesAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<UserItem>(EXTRA_STATE)
            if (list != null) {
                mainAdapter.listUsers = list
            }
        }*/
    }

    override fun onResume() {
        super.onResume()
        val appDatabase = MainDatabase.initDB(this)
        mainAdapter.setData(appDatabase.userDAO().getAll())
        //mainAdapter.listUsers = appDatabase.userDAO().getAll()
        mainAdapter.notifyDataSetChanged()
    }

    /*private fun loadNotesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            val deferredUsers = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val users = deferredUsers.await()
            if (users.size > 0) {
                mainAdapter.listUsers = users
            } else {
                mainAdapter.listUsers = ArrayList()
                Toast.makeText(this@FavoriteActivity, "No Data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, mainAdapter.listUsers)
    }*/
}