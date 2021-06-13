package vsu.csf.procat.ui.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import vsu.csf.procat.R
import vsu.csf.procat.databinding.ActivityProfileBinding
import vsu.csf.procat.ui.auth.AuthActivity
import vsu.csf.procat.ui.rentinventorydetail.RentInventoryDetailActivity
import vsu.csf.procat.ui.scanner.ScannerActivity

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    private val viewModel: ProfileViewModel by viewModels()

    private var isMenuVisible = false

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        binding.activity = this
        binding.lifecycleOwner = this
        binding.vm = viewModel

        setSupportActionBar(binding.profileToolbar)

        resultLauncher = this.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uuid = result.data?.extras?.getString(ScannerActivity.ITEM_UUID_RESULT_EXTRA)
                uuid?.let {
                    onUuidScanned(it)
                } ?: Timber.e("Intent data is null but result is ok")
            }
        }

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

    fun onScanButtonClick() {
        ScannerActivity.startForResult(this, resultLauncher)
    }

    private fun onUuidScanned(uuid: String) {
        RentInventoryDetailActivity.start(this, uuid)
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ProfileActivity::class.java))
        }
    }
}