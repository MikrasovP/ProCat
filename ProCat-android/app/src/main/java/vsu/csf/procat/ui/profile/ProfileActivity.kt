package vsu.csf.procat.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import vsu.csf.procat.R
import vsu.csf.procat.databinding.ActivityProfileBinding
import vsu.csf.procat.ui.auth.AuthActivity

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    private val viewModel: ProfileViewModel by viewModels()

    private var isMenuVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        binding.activity = this
        binding.lifecycleOwner = this
        binding.vm = viewModel

        setSupportActionBar(binding.profileToolbar)

        viewModel.isAuthorized.observe(this) { isAuthorized ->
            isMenuVisible = isAuthorized
            invalidateOptionsMenu()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateAuthStatus()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (isMenuVisible) {
            menuInflater.inflate(R.menu.menu_logout_toolbar, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout_item -> viewModel.logout()
        }
        return true
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