package com.example.nolibrariesapplication.remote

import android.os.AsyncTask
import android.util.Log
import com.example.nolibrariesapplication.presenters.PhotosPresenter
import com.example.nolibrariesapplication.models.Album
import com.example.nolibrariesapplication.util.Constants
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

@Suppress("Deprecation")
class AlbumsAsyncTask(
        private val searchUserId: Int,
        private val presenter: PhotosPresenter
) : AsyncTask<Unit, Unit, List<Album>>() {
    override fun doInBackground(vararg params: Unit?): List<Album>? {

        val url = URL("${Constants.baseUrl}albums")
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
            val albums = ArrayList<Album>()
            for (i in 0 until jsonArray.length()) {
                val userId = jsonArray.getJSONObject(i).getInt("userId")
                val albumId = jsonArray.getJSONObject(i).getInt("id")
                val title = jsonArray.getJSONObject(i).getString("title")
                albums.add(Album(userId, albumId, title))
            }
            return albums.filter { album -> album.userId == searchUserId }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            connection?.disconnect()
            reader?.close()

        }
        return null
    }

    override fun onPostExecute(result: List<Album>?) {
        presenter.getPhotosFromAlbums(result)
    }
}