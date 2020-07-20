package com.example.githubuserapp.view.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.example.githubuserapp.R
import com.example.githubuserapp.data.database.MainDatabase
import com.example.githubuserapp.model.UserItem
import com.example.githubuserapp.utils.DownloadImageTask

class MainWidgetRemoteViewsFactory(val context: Context) :
    RemoteViewsService.RemoteViewsFactory {

    //lateinit var userDao : UserFavoriteDAO
    private var items: MutableList<Bitmap> = ArrayList()
    private var users: List<UserItem> = ArrayList()

    override fun onCreate() {
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun onDataSetChanged() {
        val appDatabase = MainDatabase.initDB(context)
        users = appDatabase.userDAO().getAll()
        Log.i("Widget Fav Username", "${users[0].login}")
        setBitmap(users)
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getViewAt(position: Int): RemoteViews {
        val remoteViews = RemoteViews(context.packageName, R.layout.item_widget)
        remoteViews.setImageViewBitmap(R.id.ivItemWidget, items[position])

        val bundle = Bundle()
        bundle.putInt(MainWidget.EXTRA_ITEM, position)

        val fillIntent = Intent()
        fillIntent.putExtras(bundle)

        remoteViews.setOnClickFillInIntent(R.id.ivItemWidget, fillIntent)
        return remoteViews
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun onDestroy() {

    }

    private fun setBitmap(list: List<UserItem>) {
        items.removeAll(items)
        items.clear()

        for (user in list) {
            Log.i("Widget Fav Username", "${user.login}")
            items.add(DownloadImageTask().execute(user.avatar_url).get())
        }
    }
}