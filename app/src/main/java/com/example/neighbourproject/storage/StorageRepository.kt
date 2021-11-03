package com.example.neighbourproject.storage

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

class StorageRepository: StorageService {
    override fun writeImageStorage(byte: ByteArray) {
        //TODO write storage code here
    }

    override fun loadImage(context: Context, url: String, view: ImageView) {
        if(url != "") {
            Glide.with(context)
                .load(url)
                .into(view)
        }
    }
}