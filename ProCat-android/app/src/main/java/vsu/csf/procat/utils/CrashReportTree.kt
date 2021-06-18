package vsu.csf.procat.utils

import android.util.Log
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import timber.log.Timber

class CrashReportTree : Timber.Tree() {

    override fun isLoggable(tag: String?, priority: Int): Boolean {
        return priority >= Log.INFO
    }

    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        Firebase.crashlytics.log(message)
        if (priority == Log.ERROR) {
            Firebase.crashlytics.recordException(throwable ?: UnknownException(message))
        }
    }

    private class UnknownException(message: String) : RuntimeException(message)

}