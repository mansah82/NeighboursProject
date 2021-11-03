package com.example.neighbourproject.storage

import android.content.Context
import android.widget.ImageView
import com.example.neighbourproject.R

class StorageRepositoryTest: StorageService {
    override fun writeImageStorage(byte: ByteArray) {
        // Do nothing
    }

    override fun loadImage(context: Context, url: String, view: ImageView) {
        when((0..2).random()){
            0 -> view.setImageResource(R.drawable.checkbox)
            1 -> view.setImageResource(R.drawable.duck)
            2 -> view.setImageResource(R.drawable.grannar)
        }
    }
}