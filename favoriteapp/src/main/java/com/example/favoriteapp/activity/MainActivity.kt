package com.example.favoriteapp.activity

import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.favoriteapp.R
import com.example.favoriteapp.adapter.MainAdapter
import com.example.favoriteapp.db.DatabaseContract.NoteColumns.Companion.CONTENT_URI
import com.example.favoriteapp.helper.MappingHelper
import com.example.favoriteapp.model.UserItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val mainAdapter = MainAdapter()

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activity_favorite_recyclerView.setHasFixedSize(true)
        activity_favorite_recyclerView.layoutManager = GridLayoutManager(this, 2)
        activity_favorite_recyclerView.adapter = mainAdapter

        val handlerThread = HandlerThread("DataObserver")
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
        }
    }

    private fun loadNotesAsync() {
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
                Toast.makeText(this@MainActivity, "No Data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, mainAdapter.listUsers)
    }


    /*

     override fun postExecute(cursor: Cursor?) {
        if (cursor != null) {
            setUserAdapter(cursor)
        }
    }

    override fun onResume() {
        super.onResume()
        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()

        val handler = Handler(handlerThread.looper)
        val dataObserver = DataObserver(
            handler,
            this
        )

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadNotesAsync()
            }
        }
        contentResolver?.registerContentObserver(CONTENT_URI, true, dataObserver)
        let { LoadUserTask(it, this).execute() }
    }

    val CONTENT_URI: Uri = Uri.Builder().scheme("content")
            .authority("com.example.githubuserapp")
            .appendPath("user")
            .build()

    val dataObserver = DataObserver(
            handler,
            this
        )

        contentResolver?.registerContentObserver(CONTENT_URI, true, dataObserver)
        let { LoadUserTask(it, this).execute() }



    private val mainAdapter = MainAdapter()

    companion object {
        val CONTENT_URI: Uri = Uri.Builder().scheme("content")
            .authority("com.example.githubuserapp")
            .appendPath("movie")
            .build()

    }

    private fun setUserAdapter(cursor: Cursor) {
        val users = ArrayList<UserItem>()
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            users.add(
                UserItem(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(9),
                    cursor.getString(10)
                )
            )
            cursor.moveToNext()
        }
        //Log.i("FavApp MainActivity","User name Cursor : ${cursor.getString(1)} " )
        //Log.i("FavApp MainActivity","User name : ${users[0].login} " )
        mainAdapter.listUsers = users
        mainAdapter.notifyDataSetChanged()
    }

    private class LoadUserTask(context: Context, callback: LoadUserCallback) :
        AsyncTask<Void, Void, Cursor?>() {
        private val weakReferenceContext: WeakReference<Context> = WeakReference(context)
        private val weakReferenceCallback: WeakReference<LoadUserCallback>? =
            WeakReference(callback)

        override fun doInBackground(vararg params: Void?): Cursor? {
            return weakReferenceContext
                .get()?.contentResolver?.query(
                    CONTENT_URI,
                    null,
                    null,
                    null,
                    null
                )
        }

        override fun onPostExecute(result: Cursor?) {
            super.onPostExecute(result)
            weakReferenceCallback?.get()?.postExecute(result)
        }

    }

    private class DataObserver(handler: Handler, val context: Context) : ContentObserver(handler) {
        override fun onChange(selfChange: Boolean) {
            super.onChange(selfChange)
            LoadUserTask(
                context, context as MainActivity
            ).execute()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activity_favorite_recyclerView.setHasFixedSize(true)
        activity_favorite_recyclerView.layoutManager = GridLayoutManager(this, 2)
    }

    override fun onResume() {
        super.onResume()
        //val appDatabase = MainDatabase.initDB(this)
        //mainAdapter.setData(appDatabase.userDAO().getAll())
        mainAdapter.notifyDataSetChanged()
        activity_favorite_recyclerView.adapter = mainAdapter
    }
    */
}
