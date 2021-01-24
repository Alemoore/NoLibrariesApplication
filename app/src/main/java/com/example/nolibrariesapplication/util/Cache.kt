package com.example.nolibrariesapplication.util

import android.graphics.Bitmap
import android.util.Log
import java.util.*

object Cache {
    private val cache: WeakHashMap<Int, Bitmap> = WeakHashMap()
    fun containsImage(key: Int): Boolean = cache.containsKey(key)

    fun getImage(key: Int): Bitmap? = cache[key]

    fun addImage(key: Int, image: Bitmap) {
        cache[key] = image
    }
}