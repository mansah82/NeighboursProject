package com.example.neighbourproject.storage

import android.content.Context
import android.widget.ImageView

interface StorageService {
    fun writeImageStorage(byte: ByteArray)

    fun loadImage(context: Context, url: String, view: ImageView)
}