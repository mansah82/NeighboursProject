package com.example.neighbourproject.storage

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

class StorageRepository : StorageService {
    companion object {
        private const val TAG = "StorageRepository"
    }

    override fun writeSmallImageStorage(filename: String, bitmap: Bitmap): String {
        return if (filename.isNotEmpty()) {
            val outputStream = ByteArrayOutputStream()

            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
            val fileData = outputStream.toByteArray()
            val fullFileName = "/Images/$filename/small_profile.jpeg"
            val ref = FirebaseStorage.getInstance().getReference(fullFileName)
            Log.d(TAG, "About to write ${fileData.size} bytes")
            ref.putBytes(fileData)
                .addOnSuccessListener {
                    Log.d(TAG, "upLoad Bitmap: successfully: ${it.metadata?.path}")
                    ref.downloadUrl.addOnSuccessListener { url ->
                        Log.d(TAG, "File Location: $url")
                    }
                }
            fullFileName
        }else
            ""
    }


    override fun loadImage(context: Context, url: String, view: ImageView) {
        /*
        // Reference to an image file in Cloud Storage
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        // ImageView in your Activity
        ImageView imageView = findViewById(R.id.imageView);

        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)
        Glide.with(this /* context */)
                .load(storageReference)
                .into(imageView);
    */
        if (url != "") {
            Glide.with(context)
                .load(url)
                .into(view)
        }
    }
}