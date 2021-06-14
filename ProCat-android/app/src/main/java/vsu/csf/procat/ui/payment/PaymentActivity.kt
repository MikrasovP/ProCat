package vsu.csf.procat.ui.payment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.redmadrobot.inputmask.MaskedTextChangedListener
import dagger.hilt.android.AndroidEntryPoint
import vsu.csf.procat.R
import vsu.csf.procat.databinding.ActivityPaymentBinding
import java.math.BigDecimal

@AndroidEntryPoint
class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding

    private val viewModel: PaymentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment)
        binding

        setUpPhoneMask()
    }

    private fun setUpPhoneMask() {
        val creditCardEt = binding.creditCardEt
        MaskedTextChangedListener.installOn(
            creditCardEt,
            CREDIT_CARD_MASK,
            object : MaskedTextChangedListener.ValueListener {
                override fun onTextChanged(
                    maskFilled: Boolean,
                    extractedValue: String,
                    formattedValue: String,
                ) {
                    // Method may be called if text was not changed
                    if (viewModel.creditCardNumber.value != extractedValue)
                        viewModel.creditCardNumber.value = extractedValue
                }
            }
        )
    }

    companion object {
        const val PAYMENT_SUCCESS_EXTRA = "payment_success"
        private const val CREDIT_CARD_MASK = "[0000] [0000] [0000] [0000]"

        private const val RENT_ID_EXTRA = "rent_id"
        private const val PAY_AMOUNT_EXTRA = "pay_amount_id"

        fun start(
            launcher: ActivityResultLauncher<Intent>,
            context: Context,
            payAmount: BigDecimal, rentId: Long,
        ) {
            val intent = Intent(context, PaymentActivity::class.java)
                .putExtra(RENT_ID_EXTRA, rentId)
                .putExtra(PAY_AMOUNT_EXTRA, payAmount)
            launcher.launch(intent)
        }

    }
}