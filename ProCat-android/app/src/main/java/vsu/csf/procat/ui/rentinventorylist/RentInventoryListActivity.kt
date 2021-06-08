package vsu.csf.procat.ui.rentinventorylist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import vsu.csf.procat.R
import vsu.csf.procat.databinding.ActivityRentInventoryListBinding
import vsu.csf.procat.model.RentInventory
import vsu.csf.procat.ui.profile.ProfileActivity
import vsu.csf.procat.ui.scanner.ScannerActivity

@AndroidEntryPoint
class RentInventoryListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRentInventoryListBinding
    private val viewModel: RentInventoryListViewModel by viewModels()
    private val adapter = RentInventoryListAdapter { onInventoryItemClick(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_rent_inventory_list)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        binding.rentInventoryRv.adapter = adapter
        binding.activity = this

        val rentStationId = intent.getLongExtra(RENT_STATION_ID_EXTRA, Long.MIN_VALUE)
        if (rentStationId != Long.MIN_VALUE)
            viewModel.rentStationId = rentStationId

        observeViewModel()

        setSupportActionBar(binding.rentInventoryToolbar)

        viewModel.updateData()
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

    private fun observeViewModel() {
        viewModel.rentInventoryList.observe(this, { adapter.setItems(it) })
        viewModel.networkError.observe(this, { isError ->
            if (isError)
                Toast.makeText(this, getString(R.string.update_error), Toast.LENGTH_LONG).show()
        })
    }

    private fun onInventoryItemClick(inventory: RentInventory) {
        Toast.makeText(this, getString(R.string.rent_item_hint), Toast.LENGTH_SHORT).show()
    }

    fun onScanButtonClick() {
        ScannerActivity.startForResult(this)
    }

    companion object {
        private const val RENT_STATION_ID_EXTRA = "rent_station_id"

        fun start(context: Context, rentStationId: Long) {
            val intent = Intent(context, RentInventoryListActivity::class.java)
            intent.putExtra(RENT_STATION_ID_EXTRA, rentStationId)
            context.startActivity(intent)
        }
    }
}