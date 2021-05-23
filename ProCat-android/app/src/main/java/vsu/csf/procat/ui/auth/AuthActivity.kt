package vsu.csf.procat.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.databinding.DataBindingUtil
import com.redmadrobot.inputmask.MaskedTextChangedListener
import dagger.hilt.android.AndroidEntryPoint
import vsu.csf.procat.ProCatApplication
import vsu.csf.procat.R
import vsu.csf.procat.databinding.ActivityAuthBinding

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth)
        binding.lifecycleOwner = this
        binding.vm = viewModel

        setSupportActionBar(binding.authToolbar)
        setUpPhoneMask()
        observeViewModelEvents()
    }

    private fun setUpPhoneMask() {
        val phoneEt = binding.phoneNumberEt
        MaskedTextChangedListener.installOn(
            phoneEt,
            PHONE_NUMBER_MASK,
            object : MaskedTextChangedListener.ValueListener {
                override fun onTextChanged(
                    maskFilled: Boolean,
                    extractedValue: String,
                    formattedValue: String,
                ) {
                    // Method may be called if text was not changed
                    if (viewModel.phoneNumber.value != extractedValue)
                        viewModel.phoneNumber.value = extractedValue
                }
            }
        )
    }

    private fun observeViewModelEvents() {
        observeConfirmationCode()
        observeAuthSuccess()
        observeNetworkError()
    }

    private fun observeConfirmationCode() {
        viewModel.properConfirmationCode.observe(this) { code ->
            val builder =
                NotificationCompat.Builder(this, ProCatApplication.CONFIRMATION_CODE_CHANNEL_ID)
                    .setContentTitle(getString(R.string.confirmation_notification_title))
                    .setSmallIcon(R.drawable.ic_check_notification_24)
                    .setContentText(getString(R.string.confirmation_notification_text, code))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
            with(NotificationManagerCompat.from(this)) {
                // notificationId is a unique int for each notification that you must define
                notify(CONFIRMATION_NOTIFICATION_ID, builder.build())
            }
        }
    }

    private fun observeNetworkError() {
        viewModel.networkError.observe(this) { isError ->
            if (isError)
                Toast.makeText(
                    this,
                    getString(R.string.network_error),
                    Toast.LENGTH_LONG,
                ).show()
        }
    }

    private fun observeAuthSuccess() {
        viewModel.authSuccess.observe(this) { isSuccess ->
            if (isSuccess)
                finish()
        }
    }

    companion object {

        private const val PHONE_NUMBER_MASK = "+7 ([000]) [000]-[00]-[00]"
        private const val CONFIRMATION_NOTIFICATION_ID = 162813

        fun start(context: Context) {
            context.startActivity(Intent(context, AuthActivity::class.java))
        }

    }
}