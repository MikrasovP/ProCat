package vsu.csf.procat.ui.rentstationslist

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
import vsu.csf.procat.databinding.ActivityRentStationsListBinding
import vsu.csf.procat.model.RentStation
import vsu.csf.procat.ui.ProfileActivity

@AndroidEntryPoint
class RentStationsListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRentStationsListBinding
    private val viewModel: RentStationsListViewModel by viewModels()
    private val adapter: RentStationsListAdapter =
        RentStationsListAdapter { rentStation -> onRentStationClick(rentStation) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_rent_stations_list)
        binding.rentStationsRv.adapter = adapter

        observeViewModel()

        setSupportActionBar(binding.rentStationsToolbar)
    }

    private fun observeViewModel(){
        viewModel.rentStations.observe(this, { adapter.setItems(it) })
        viewModel.networkError.observe(this, {
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

    private fun onRentStationClick(rentStation: RentStation) {
        Toast.makeText(this, "Rent station, id: ${rentStation.id}", Toast.LENGTH_SHORT)
            .show()
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, RentStationsListActivity::class.java))
        }
    }
}