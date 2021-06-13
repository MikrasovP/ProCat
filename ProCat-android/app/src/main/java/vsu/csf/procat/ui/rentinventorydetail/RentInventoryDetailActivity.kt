package vsu.csf.procat.ui.rentinventorydetail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import vsu.csf.network.BuildConfig
import vsu.csf.procat.R
import vsu.csf.procat.databinding.ActivityRentInventoryDetailBinding
import vsu.csf.procat.ui.profile.ProfileActivity

@AndroidEntryPoint
class RentInventoryDetailActivity : AppCompatActivity() {

    private val viewModel: RentInventoryDetailViewModel by viewModels()

    private lateinit var binding: ActivityRentInventoryDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val itemUUid = intent.getStringExtra(INVENTORY_ITEM_UUID_EXTRA)
        viewModel.setItemUuid(itemUUid)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_rent_inventory_detail)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        binding.activity = this

        setSupportActionBar(binding.rentInventoryDetailToolbar)

        observeImagePath()
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

    private fun observeImagePath() {
        viewModel.imagePath.observe(this) { imagePath ->
            if (!imagePath.isNullOrBlank())
                setImage(imagePath)
        }
    }

    private fun setImage(imagePath: String) {
        Glide.with(this)
            .load(BuildConfig.IMAGE_SERVER_URL + imagePath)
            .centerCrop()
            .into(binding.inventoryIv)
    }

    companion object {
        private const val INVENTORY_ITEM_UUID_EXTRA = "inventory_item_uuid"

        fun start(context: AppCompatActivity, itemUuid: String) {
            context.startActivity(
                Intent(context, RentInventoryDetailActivity::class.java)
                    .putExtra(INVENTORY_ITEM_UUID_EXTRA, itemUuid)
            )
        }
    }
}