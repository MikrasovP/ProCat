package vsu.csf.procat.ui.rentinventorydetail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import vsu.csf.procat.R

class RentInventoryDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rent_inventory_detail)
    }

    companion object {
        private const val INVENTORY_ITEM_UUID_EXTRA = "inventory_item_uuid"

        fun start(context: AppCompatActivity, itemUuid: String) {
            context.startActivity(Intent(context, RentInventoryDetailActivity::class.java).putExtra(INVENTORY_ITEM_UUID_EXTRA, itemUuid))
        }
    }
}