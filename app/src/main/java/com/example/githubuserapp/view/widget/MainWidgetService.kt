package com.example.githubuserapp.view.widget

import android.content.Intent
import android.widget.RemoteViewsService

class MainWidgetService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory =
        MainWidgetRemoteViewsFactory(this.applicationContext)

}