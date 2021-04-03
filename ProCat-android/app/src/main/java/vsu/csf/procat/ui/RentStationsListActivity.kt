package vsu.csf.procat.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import vsu.csf.procat.R
import vsu.csf.procat.databinding.ActivityRentStationsListBinding

@AndroidEntryPoint
class RentStationsListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRentStationsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rent_stations_list)
        setSupportActionBar(binding.rentStationsToolbar)
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, RentStationsListActivity::class.java))
        }
    }
}