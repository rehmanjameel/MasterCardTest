package com.base.mastercardgatewaytest

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.base.mastercardgatewaytest.databinding.ActivityCardBinding
import com.mastercard.gateway.android.sdk.GatewayAPI
import com.mastercard.gateway.android.sdk.GatewayCallback
import com.mastercard.gateway.android.sdk.GatewayMap
import com.mastercard.gateway.android.sdk.GatewaySDK
import com.mastercard.gateway.android.sdk.Session

class CardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCardBinding
    private lateinit var session: Session
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardBinding.inflate(layoutInflater)
        setContentView(binding.root)




        session = Session(
            id = "your-session-id",
            amount = "1.23",
            currency = "PKR",
            apiVersion = "61", // api version used to create the session
            orderId = "121" // must match order id used on your server
        )

        binding.btnPay.setOnClickListener {
            getCardDetails()
        }

    }

    private fun getCardDetails() {

        if (binding.cardName.toString().isNotEmpty() || binding.cardNumber.length() != 16 ||
            binding.expiryMonth.length() != 2 || binding.expiryYear.length() != 4 ||
            binding.cvv.length() != 3) {
            Toast.makeText(this, "Invalid card details", Toast.LENGTH_SHORT).show()
            return
        }

        // The GatewayMap object provides support for building a nested map
        //        structure using key-based dot(.) notation.
        // Each parameter is similarly defined in your online integration guide.
        val request = GatewayMap()
            .set("sourceOfFunds.provided.card.nameOnCard", binding.cardName)
            .set("sourceOfFunds.provided.card.number", binding.cardNumber)
            .set("sourceOfFunds.provided.card.securityCode", binding.cvv)
            .set("sourceOfFunds.provided.card.expiry.month", binding.expiryMonth)
            .set("sourceOfFunds.provided.card.expiry.year", binding.expiryYear)

        GatewayAPI.updateSession(session, request,
            object: GatewayCallback {
                override fun onError(throwable: Throwable) {
                    Log.e("errorr of card details", throwable.message.toString())
                }

                override fun onSuccess(response: GatewayMap) {
                    Log.e("response of card details", response.toString())

                }

            })

    }

}