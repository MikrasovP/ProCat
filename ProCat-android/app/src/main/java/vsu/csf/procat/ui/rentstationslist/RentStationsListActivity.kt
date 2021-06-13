package vsu.csf.procat.ui.rentstationslist

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import vsu.csf.procat.R
import vsu.csf.procat.databinding.ActivityRentStationsListBinding
import vsu.csf.procat.model.RentStation
import vsu.csf.procat.ui.profile.ProfileActivity
import vsu.csf.procat.ui.rentinventorydetail.RentInventoryDetailActivity
import vsu.csf.procat.ui.rentinventorylist.RentInventoryListActivity
import vsu.csf.procat.ui.scanner.ScannerActivity

@AndroidEntryPoint
class RentStationsListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRentStationsListBinding
    private val viewModel: RentStationsListViewModel by viewModels()
    private val adapter: RentStationsListAdapter =
        RentStationsListAdapter { rentStation -> onRentStationClick(rentStation) }

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        resultLauncher = this.registerForActivityResult(StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uuid = result.data?.extras?.getString(ScannerActivity.ITEM_UUID_RESULT_EXTRA)
                uuid?.let {
                    onUuidScanned(it)
                } ?: Timber.e("Intent data is null but result is ok")
            }
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_rent_stations_list)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        binding.rentStationsRv.adapter = adapter
        binding.activity = this

        observeViewModel()

        setSupportActionBar(binding.rentStationsToolbar)

        viewModel.updateData()
    }

    private fun observeViewModel() {
        viewModel.rentStations.observe(this, { adapter.setItems(it) })
        viewModel.networkError.observe(this, { isError ->
            if (isError)
                Toast.makeText(this, getString(R.string.update_error), Toast.LENGTH_LONG).show()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_profile_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.profile_item -> ProfileActivity.start(this)
        }
        return true
    }

    fun onScannerButtonClick() {
        ScannerActivity.startForResult(this, resultLauncher)
    }

    private fun onUuidScanned(uuid: String) {
        RentInventoryDetailActivity.start(this, uuid)
    }

    private fun onRentStationClick(rentStation: RentStation) {
        RentInventoryListActivity.start(this, rentStation.id)
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, RentStationsListActivity::class.java))
        }
    }
}