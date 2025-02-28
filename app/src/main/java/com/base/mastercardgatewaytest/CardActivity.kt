package com.base.mastercardgatewaytest

import android.os.Bundle
import android.util.Base64
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCardBinding
    private lateinit var session: Session
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardBinding.inflate(layoutInflater)
        setContentView(binding.root)


        createMastercardSession()

        binding.btnPay.setOnClickListener {
            getCardDetails()
        }

    }

    private fun createMastercardSession() {
        val username = "merchant.TESTMPGSTEST0101"
        val password = "12acbd9acc25c2cc1dca44a1c2be2a9c"

        // Encode credentials in Base64 for Basic Authentication
        val credentials = "$username:$password"
        val authHeader = "Basic " + Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)

        val request = SessionRequest(SessionData(authenticationLimit = 25))

        RetrofitClient.apiService.createSession(authHeader, request).enqueue(object :
            Callback<SessionResponse> {
            override fun onResponse(call: Call<SessionResponse>, response: Response<SessionResponse>) {
                if (response.isSuccessful) {
                    val sessionId = response.body()?.session?.id
                    println("Session ID: $sessionId")
                    Log.e("session Id", sessionId.toString())
                    val orderId = (0..10).random() // generated random from 0 to 10 included


                    session = Session(
                        id = sessionId.toString(),
                        amount = "0.01",
                        currency = "USD",
                        apiVersion = "82", // api version used to create the session
                        orderId = orderId.toString() // must match order id used on your server
                    )
                } else {
                    println("Failed: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<SessionResponse>, t: Throwable) {
                println("Error: ${t.message}")
            }
        })
    }


    private fun getCardDetails() {

//        if (binding.cardName.toString().isNotEmpty() || binding.cardNumber.length() != 16 ||
//            binding.expiryMonth.length() != 2 || binding.expiryYear.length() != 2 ||
//            binding.cvv.length() != 3) {
//            Toast.makeText(this, "Invalid card details", Toast.LENGTH_SHORT).show()
//            return
//        }

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