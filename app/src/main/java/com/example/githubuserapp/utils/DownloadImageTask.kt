package com.example.githubuserapp.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import java.io.InputStream
import java.net.URL

class DownloadImageTask : AsyncTask<String, Void, Bitmap>() {

    override fun doInBackground(vararg params: String?): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val input: InputStream = URL(params[0]).openStream()
            bitmap = BitmapFactory.decodeStream(input)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return bitmap
    }
}