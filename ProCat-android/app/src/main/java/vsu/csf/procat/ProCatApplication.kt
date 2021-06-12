package vsu.csf.procat

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree
import vsu.csf.procat.utils.CrashReportTree

@HiltAndroidApp
class ProCatApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initLogger()
        createNotificationChannel()
    }

    private fun initLogger() {
        Timber.plant(CrashReportTree())
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.confirmation_notification_channel_name)
            val descriptionText = getString(R.string.confirmation_notification_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CONFIRMATION_CODE_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val CONFIRMATION_CODE_CHANNEL_ID = "confirmation_code"
    }

}