package vsu.csf.procat.utils

import android.util.Log
import timber.log.Timber

class CrashReportTree : Timber.Tree() {

    override fun isLoggable(tag: String?, priority: Int): Boolean {
        return priority >= Log.INFO
    }

    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        /*Analytics.trackEvent(message)
        if (priority == Log.ERROR) {
            Crashes.trackError(throwable ?: UnknownException(message))
        }*/
    }

    private class UnknownException(message: String) : RuntimeException(message)

}