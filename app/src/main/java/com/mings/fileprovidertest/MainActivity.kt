package com.mings.fileprovidertest

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.File

open class MainActivity : AppCompatActivity() {
    private val rxPermissions = RxPermissions(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            rxPermissions.request(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe { granted ->
                    if (granted) {
                        loadPic()
                    }
                }
        }
    }


    private fun loadPic() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }
        startActivityForResult(intent, 444)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 444 && resultCode == Activity.RESULT_OK) {
            Timber.d("result code : $resultCode  data: ${data?.dataString}")
            Timber.d("${data?.data?.path}")

            data?.data?.let { it ->
                Luban.with(this).load(ImageUtils.uri2File(this, it))
                    .setCompressListener(object : OnCompressListener {
                        override fun onSuccess(file: File?) {
                            Timber.d(" on success : ${file?.absolutePath} file exist ${file?.exists()}")
                        }

                        override fun onError(e: Throwable?) {
                            Timber.e(e)
                        }

                        override fun onStart() {
                        }

                    }).launch()
            }


        }

    }

}
