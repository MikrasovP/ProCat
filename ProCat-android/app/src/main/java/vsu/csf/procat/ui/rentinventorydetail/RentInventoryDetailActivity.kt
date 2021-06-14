package vsu.csf.procat.ui.rentinventorydetail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import vsu.csf.network.BuildConfig
import vsu.csf.procat.R
import vsu.csf.procat.databinding.ActivityRentInventoryDetailBinding
import vsu.csf.procat.ui.payment.PaymentActivity
import vsu.csf.procat.ui.profile.ProfileActivity

@AndroidEntryPoint
class RentInventoryDetailActivity : AppCompatActivity() {

    private val viewModel: RentInventoryDetailViewModel by viewModels()

    private lateinit var binding: ActivityRentInventoryDetailBinding

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val itemUUid = intent.getStringExtra(INVENTORY_ITEM_UUID_EXTRA)
        viewModel.setItemUuid(itemUUid)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_rent_inventory_detail)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        binding.activity = this
        setSupportActionBar(binding.rentInventoryDetailToolbar)

        resultLauncher =
            this.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    when (result.data?.extras?.getBoolean(PaymentActivity.PAYMENT_SUCCESS_EXTRA)) {
                        true -> onPaymentSuccess()
                        false -> onPaymentError()
                        else -> {
                            Timber.e("Intent data is null but result is ok")
                        }
                    }
                }
            }

        observeViewModelEvents()
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

    fun onStartRentClicked() {
        viewModel.startRent()
    }

    fun onFinishRentClicked() {
        viewModel.stopRent()
    }

    private fun observeViewModelEvents() {
        observeItemDataLoaded()
        observeImagePath()
        observeRentStart()
        observeRentPause()
    }

    private fun observeItemDataLoaded() {
        viewModel.dataLoaded.observe(this) { loaded ->
            if (loaded)
                showItemsWithCrossfade()
        }
    }

    private fun showItemsWithCrossfade() {
        binding.itemData.apply {
            alpha = 0f
            visibility = View.VISIBLE
            animate()
                .setInterpolator(LinearOutSlowInInterpolator())
                .alpha(1f)
                .setDuration(CROSSFADE_ANIMATION_DURATION)
                .setListener(null)
        }
        binding.progressBar.animate()
            .alpha(0f)
            .setInterpolator(LinearOutSlowInInterpolator())
            .setDuration(CROSSFADE_ANIMATION_DURATION)
            .setListener(null)
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

    private fun observeRentStart() {
        viewModel.rentStarted.observe(this) { started ->
            if (started) {
                Toast.makeText(applicationContext, R.string.rent_start_success, Toast.LENGTH_SHORT)
                    .show()
                ProfileActivity.start(this)
                finish()
            }
        }
    }

    private fun observeRentPause() {
        viewModel.rentStopped.observe(this) { rentPauseDto ->
            if (rentPauseDto != null)
                PaymentActivity.start(
                    resultLauncher,
                    this,
                    rentPauseDto.amountToPay, rentPauseDto.rentId,
                )
        }
    }

    private fun onPaymentSuccess() {
        Toast.makeText(applicationContext, getString(R.string.payment_success), Toast.LENGTH_LONG)
            .show()
        ProfileActivity.start(this)
        finish()
    }

    private fun onPaymentError() {
        Toast.makeText(applicationContext, getString(R.string.payment_error), Toast.LENGTH_LONG)
            .show()
        viewModel.retrieveItemData()
    }

    companion object {
        private const val INVENTORY_ITEM_UUID_EXTRA = "inventory_item_uuid"

        private const val CROSSFADE_ANIMATION_DURATION = 500L

        fun start(context: AppCompatActivity, itemUuid: String) {
            context.startActivity(
                Intent(context, RentInventoryDetailActivity::class.java)
                    .putExtra(INVENTORY_ITEM_UUID_EXTRA, itemUuid)
            )
        }
    }
}