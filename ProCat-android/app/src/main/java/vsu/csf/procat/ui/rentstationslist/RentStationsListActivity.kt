package vsu.csf.procat.ui.rentstationslist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import vsu.csf.procat.R
import vsu.csf.procat.databinding.ActivityRentStationsListBinding
import vsu.csf.procat.ui.ProfileActivity

@AndroidEntryPoint
class RentStationsListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRentStationsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rent_stations_list)
        setSupportActionBar(binding.rentStationsToolbar)
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

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, RentStationsListActivity::class.java))
        }
    }
}