package vsu.csf.procat.ui.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
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
import vsu.csf.procat.ui.rentstationslist.RentStationsListActivity
import vsu.csf.procat.ui.scanner.ScannerActivity

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private val viewModel: ProfileViewModel by viewModels()
    private val adapter = CurrentRentInventoryListAdapter { onCurrentRentItemClicked() }

    private var isMenuLogoutVisible = false

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        binding.activity = this
        binding.lifecycleOwner = this
        binding.vm = viewModel
        binding.rentItemsRv.adapter = adapter

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
            isMenuLogoutVisible = isAuthorized
            invalidateOptionsMenu()
        }
        viewModel.currentRentInventoryList.observe(this) { items ->
            adapter.setItems(items)
        }
        viewModel.networkError.observe(this) { isError ->
            if (isError)
                Toast.makeText(applicationContext, R.string.network_error, Toast.LENGTH_SHORT)
                    .show()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateAuthStatus()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (isMenuLogoutVisible) {
            menuInflater.inflate(R.menu.menu_logout_toolbar, menu)
        } else {
            menuInflater.inflate(R.menu.menu_home_toolbar, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout_item -> viewModel.logout()
            R.id.home_item -> openHomeActivity()
        }
        return true
    }

    fun openAuthActivity() {
        AuthActivity.start(this)
    }

    private fun openHomeActivity() {
        RentStationsListActivity.start(this)
        finish()
    }

    fun onScanButtonClick() {
        ScannerActivity.startForResult(this, resultLauncher)
    }

    private fun onCurrentRentItemClicked() {
        Toast.makeText(applicationContext, R.string.rent_item_finish_hint, Toast.LENGTH_SHORT)
            .show()
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