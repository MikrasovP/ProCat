package vsu.csf.procat.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import vsu.csf.procat.R
import vsu.csf.procat.ui.rentstationslist.RentStationsListActivity

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_main)
        startRentStationsActivity()
    }

    private fun startRentStationsActivity() {
        RentStationsListActivity.start(this)
    }
}