package com.mings.fileprovidertest

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

object ImageUtils {
    fun uri2File(context: Context, uri: Uri): File {
        val file = File(context.cacheDir, "temp")
        if (file.exists()) {
            file.delete()
        }
        context.contentResolver.openInputStream(uri)?.let {
            var output: FileOutputStream? = null
            try {
                output = FileOutputStream(file)
                output.write(it.readBytes())
                output.flush()
                it.close()
                output.close()
            } catch (e: Throwable) {
            } finally {
                it.close()
                output?.close()
            }
        }
        return file
    }

    fun createImagePathUri(context: Context): Uri {
        val file = File(context.cacheDir, "${System.currentTimeMillis()}.jpg")
        val pckName = context.packageName
        return FileProvider.getUriForFile(context, "$pckName.provider", file)
    }
}
