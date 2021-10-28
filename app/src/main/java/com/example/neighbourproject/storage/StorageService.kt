package com.example.neighbourproject.storage

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView

interface StorageService {
    fun writeImageStorage(filename: String, bitmap: Bitmap): String

    fun loadImage(context: Context, url: String, view: ImageView)
}