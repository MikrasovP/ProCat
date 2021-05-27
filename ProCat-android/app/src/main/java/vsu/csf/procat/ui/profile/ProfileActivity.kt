package vsu.csf.procat.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import vsu.csf.procat.R
import vsu.csf.procat.databinding.ActivityProfileBinding
import vsu.csf.procat.ui.auth.AuthActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        binding.activity = this
        binding.lifecycleOwner = this

        setSupportActionBar(binding.profileToolbar)
        viewModel.updateAuthStatus()
    }

    fun openAuthActivity() {
        AuthActivity.start(this)
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ProfileActivity::class.java))
        }
    }
}