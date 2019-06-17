package mings.fileprovidertest

import android.app.Activity
import android.os.Bundle
import com.mings.fileprovidertest.App
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

const val DEFAULT_COVERAGE_FILE_PATH = "jacoco-coverage.ec"
const val TAG = "StagingApp"
lateinit var reportFile: File

class StagingApp : App() {
    override fun onCreate() {
        super.onCreate()
        Timber.d("StagingApp")
        reportFile = File(cacheDir, DEFAULT_COVERAGE_FILE_PATH)
        if (!reportFile.exists()) {
            try {
                reportFile.createNewFile()
            } catch (e: IOException) {
                Timber.d("异常 : $e")
                e.printStackTrace()
            }
        }



        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            var activitySize = 0
            override fun onActivityPaused(activity: Activity?) {
            }

            override fun onActivityResumed(activity: Activity?) {
            }

            override fun onActivityStarted(activity: Activity?) {
            }

            override fun onActivityDestroyed(activity: Activity?) {
                activitySize -= 1

                if (activitySize <= 0) {
                    generateCoverageReport(reportFile)
                }
            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
            }

            override fun onActivityStopped(activity: Activity?) {
            }

            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                activitySize += 1
            }

        })

    }


    private fun generateCoverageReport(file: File) {
        Timber.d("generateCoverageReport():${file.absolutePath}")
        FileOutputStream(file, false).use {
            val agent = Class.forName("org.jacoco.agent.rt.RT")
                .getMethod("getAgent")
                .invoke(null)


            Timber.d(agent.toString())
            it.write(
                agent.javaClass.getMethod("getExecutionData", Boolean::class.javaPrimitiveType!!)
                    .invoke(agent, false) as ByteArray
            )
        }
    }
}