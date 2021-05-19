package vsu.csf.procat.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import timber.log.Timber
import vsu.csf.procat.R
import vsu.csf.procat.databinding.ActivityProfileBinding
import vsu.csf.procat.ui.auth.AuthActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        binding.activity = this

        setSupportActionBar(binding.profileToolbar)
    }

    fun openLoginActivity() {
        AuthActivity.startForLogin(this)
    }

    fun openRegistrationActivity() {
        AuthActivity.startForRegistration(this)
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ProfileActivity::class.java))
        }
    }
}