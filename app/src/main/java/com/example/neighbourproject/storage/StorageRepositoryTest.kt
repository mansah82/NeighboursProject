package com.example.neighbourproject.storage

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import com.example.neighbourproject.R

class StorageRepositoryTest : StorageService {

    private var mBitmap: Bitmap? = null

    override fun writeSmallImageStorage(filename: String, bitmap: Bitmap): String {
        mBitmap = bitmap
        return "local_small_picture.jpeg"
    }

    override fun loadImage(context: Context, url: String, view: ImageView) {
        when ((0..3).random()) {
            0 -> view.setImageResource(R.drawable.checkbox)
            1 -> view.setImageResource(R.drawable.duck)
            2 -> view.setImageResource(R.drawable.grannar)
            3 -> {
                if(mBitmap != null){
                    view.setImageBitmap(mBitmap)
                }else{
                    view.setImageResource(R.drawable.duck)
                }
            }
        }
    }
}