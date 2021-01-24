package com.example.nolibrariesapplication.remote

import android.os.AsyncTask
import com.example.nolibrariesapplication.presenters.UsersPresenter
import com.example.nolibrariesapplication.models.User
import com.example.nolibrariesapplication.util.Constants.baseUrl
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

@Suppress("Deprecation")
class UsersAsyncTask(private val presenter: UsersPresenter): AsyncTask<Unit, Unit, List<User>>()  {

    override fun doInBackground(vararg params: Unit?): List<User>? {
        val url = URL("${baseUrl}users")
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
            val users = ArrayList<User>()
            for (i in 0 until jsonArray.length()) {
                val id = jsonArray.getJSONObject(i).getInt("id")
                val name = jsonArray.getJSONObject(i).getString("name")
                users.add(User(id, name))
            }
            return users
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            connection?.disconnect()
            reader?.close()
        }
        return null
    }

    override fun onPostExecute(result: List<User>?) {
        presenter.refreshView(result ?: ArrayList<User>())
    }
}