package ir.hossein.digikala_kotlin.checkout

import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import ir.hossein.digikala_kotlin.R
import ir.hossein.digikala_kotlin.utils.MyActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CheckoutActivity : MyActivity() {
    private val checkoutViewModel: CheckoutViewModel by viewModel {
        val uri: Uri? = intent.data
        if (uri != null) {
            parametersOf(uri.getQueryParameter("order_id"))
        } else {
            parametersOf(0)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        val txtMessage = findViewById<TextView>(R.id.txt_checkout_message)
        val txtOrderId = findViewById<TextView>(R.id.txt_checkout_orderId)
        checkoutViewModel.checkoutLiveData.observe(this) {
            if (it.status == "success") {
                txtMessage.text = "Ø®Ø±ÛŒØ¯ Ø¨Ø§ Ù…ÙˆÙÙ‚ÛŒØª Ø§Ù†Ø¬Ø§Ù… Ø´Ø¯"
                txtOrderId.text = it.message
            } else {
                txtMessage.text = "Ø®Ø·Ø§ Ø¯Ø± ØªØ±Ø§Ú©Ù†Ø´"
                txtOrderId.text = 0.toString()
            }
        }
        checkoutViewModel.progressBarLiveData.observe(this) {
            showProgressBar(it)
        }
    }
}