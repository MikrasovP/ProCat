package vsu.csf.procat.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import vsu.csf.procat.R
import vsu.csf.procat.databinding.ActivityAuthBinding

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth)

        setSupportActionBar(binding.authToolbar)
    }

    companion object {

        fun startForLogin(context: Context) {
            context.startActivity(Intent(context, AuthActivity::class.java))
        }

        fun startForRegistration(context: Context) {
            context.startActivity(Intent(context, AuthActivity::class.java))
        }

    }
}