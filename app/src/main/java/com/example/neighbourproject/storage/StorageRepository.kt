package com.example.neighbourproject.storage

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class StorageRepository : StorageService {
    companion object {
        private const val TAG = "StorageRepository"
    }

    override fun writeSmallImageStorage(filename: String, bitmap: Bitmap): String {
        return if (filename.isNotEmpty()) {
            val outputStream = ByteArrayOutputStream()

            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
            val fileData = outputStream.toByteArray()
            val fullFileName = "images/$filename/small_profile.jpeg"
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

    override fun loadSmallImage(context: Context, url: String, view: ImageView) {
        Log.d(TAG, "loadImage: $url")

        // Reference to an image file in Cloud Storage
        //val storageReference = FirebaseStorage.getInstance().getReference(url);
        val storageRef = Firebase.storage.reference.child(url)
        storageRef.downloadUrl.addOnSuccessListener{
            Log.d(TAG, "Got uri: ${it}")
            // Download directly from StorageReference using Glide
            // (See MyAppGlideModule for Loader registration)
            it?.let {
                Glide.with(context)
                    .load(it)
                    .into(view);
            }
        }
    }
}