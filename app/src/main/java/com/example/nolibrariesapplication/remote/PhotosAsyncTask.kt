package com.example.nolibrariesapplication.remote

import android.os.AsyncTask
import com.example.nolibrariesapplication.models.Photo
import com.example.nolibrariesapplication.presenters.PhotosPresenter
import com.example.nolibrariesapplication.util.Cache
import com.example.nolibrariesapplication.util.Constants
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

@Suppress("Deprecation")
class PhotosAsyncTask(
        private val presenter: PhotosPresenter,
        private val albumsId: List<Int>) : AsyncTask<Unit, Unit, List<Photo>>() {

    override fun doInBackground(vararg params: Unit?): List<Photo>? {
        val url = URL("${Constants.baseUrl}photos")
        var connection: HttpURLConnection? = null
        var reader: BufferedReader? = null
        try {
            connection = url.openConnection() as HttpURLConnection
            connection.connect()
            val inputStream = connection.inputStream
            reader = BufferedReader(InputStreamReader(inputStream))
            val buffer = StringBuffer()
            reader.lineSequence().forEach {
                buffer.append(it)
            }
            val jsonArray = JSONArray(buffer.toString())
            val photos = ArrayList<Photo>()

            for (i in 0 until jsonArray.length()) {
                val albumId = jsonArray.getJSONObject(i).getInt("albumId")
                val photoId = jsonArray.getJSONObject(i).getInt("id")
                val title = jsonArray.getJSONObject(i).getString("title")
                val url = jsonArray.getJSONObject(i).getString("url")
                val thumbnailUrl = jsonArray.getJSONObject(i).getString("thumbnailUrl")
                photos.add(Photo(albumId, photoId, title, url, thumbnailUrl))
            }
            return photos.filter { photo -> albumsId.contains(photo.albumId) }


        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            connection?.disconnect()
            reader?.close()
        }
        return null
    }

    override fun onPostExecute(result: List<Photo>?) {
        presenter.refreshView(result)
    }
}