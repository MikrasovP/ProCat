package vsu.csf.procat.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {


    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ProfileActivity::class.java))
        }
    }
}