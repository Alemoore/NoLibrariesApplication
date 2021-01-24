package com.example.nolibrariesapplication.remote
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import com.example.nolibrariesapplication.util.Cache
import java.io.InputStream
import java.lang.Exception
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL

@Suppress("Deprecation")
class LoadPhotosAsyncTask(
        imageView: ImageView,
        private val photoId: Int,
        private val src: String) : AsyncTask<Unit, Unit, Bitmap>() {

    private val imageViewReference = WeakReference<ImageView>(imageView);
    private val TAG = "LoadPhotosAsyncTask"

    override fun doInBackground(vararg params: Unit?): Bitmap? {
        val url: URL = URL(src)
        var connection: HttpURLConnection? = null
        var inputStream: InputStream? = null
        var bitmap: Bitmap? = null

        try {
            connection = url.openConnection() as HttpURLConnection
            connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Android 9; Mobile; rv:68.0) Gecko/68.0 Firefox/68.0")
            connection.doInput
            connection.connect()
            inputStream = connection.inputStream
            val options = BitmapFactory.Options()
            options.inPreferredConfig = Bitmap.Config.RGB_565
            bitmap = BitmapFactory.decodeStream(inputStream, null, options)
        } catch (e: Exception) {
            Log.d(TAG, e.toString())
        } finally {
            connection?.disconnect()
            inputStream?.close()
        }
        return bitmap
    }

    override fun onPostExecute(bitmap: Bitmap?) {
        if (bitmap != null) {
            val imageView: ImageView? = imageViewReference.get();
            imageView?.setImageBitmap(bitmap)
            Cache.addImage(photoId, bitmap)
        } else {
            Log.d(TAG, "Image == null")
        }
    }
}

