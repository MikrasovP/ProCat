package vsu.csf.procat

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree
import vsu.csf.procat.utils.CrashReportTree

@HiltAndroidApp
class ProCatApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initLogger()
    }

    private fun initLogger() {
        Timber.plant(CrashReportTree())
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }

}