package com.noxinfinity.pdate.utils.helper

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class ImageHelper {
    companion object{
        fun uriToFile(context: Context, imageUri: Uri): File {
            // Get the content resolver
            val contentResolver = context.contentResolver

            // Create a temp file in the app's cache directory
            val tempFile = File(context.cacheDir, "temp_image_${System.currentTimeMillis()}.jpg")

            try {
                // Open an InputStream from the Uri
                val inputStream: InputStream? = contentResolver.openInputStream(imageUri)

                // Create a FileOutputStream to the temp file
                val outputStream = FileOutputStream(tempFile)

                // Copy the InputStream to the FileOutputStream
                inputStream?.use { input ->
                    outputStream.use { output ->
                        input.copyTo(output)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return tempFile
        }
    }
}