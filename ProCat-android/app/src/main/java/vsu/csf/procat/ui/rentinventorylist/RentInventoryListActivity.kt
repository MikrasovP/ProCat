package vsu.csf.procat.ui.rentinventorylist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RentInventoryListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    companion object {
        private const val RENT_STATION_ID_EXTRA = "rent_station_id"

        fun start(context: Context, rentStationId: Long) {
            val intent = Intent()
            intent.putExtra(RENT_STATION_ID_EXTRA, rentStationId)
            context.startActivity(intent)
        }
    }
}