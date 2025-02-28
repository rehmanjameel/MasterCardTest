package com.base.mastercardgatewaytest

import android.app.Application
import android.util.Log
import com.mastercard.gateway.android.sdk.GatewayCallback
import com.mastercard.gateway.android.sdk.GatewayMap
import com.mastercard.gateway.android.sdk.GatewayRegion
import com.mastercard.gateway.android.sdk.GatewaySDK
import com.mastercard.gateway.android.sdk.InitializationCallback
import org.emvco.threeds.core.ui.UiCustomization

class App : Application() {
    val uiCustomization = UiCustomization().apply {
        // Customize colors, text, button styles, etc.
    }
    override fun onCreate() {
        super.onCreate()
        // init Gateway SDK
        GatewaySDK.initialize(
            this,
            "TESTMPGSTEST0101",
            "TESTMPGSTEST0101",
            "https://test-gateway.mastercard.com/",
            GatewayRegion.NORTH_AMERICA,
            uiCustomization, // ✅ Required Parameter
            object : GatewayCallback, InitializationCallback {
                override fun onError(throwable: Throwable) {
                    // Handle error
                }

                override fun onSuccess(response: GatewayMap) {
                    Log.e("response of gateway details", response.toString())

                }

                override fun onSuccess() {
                    Log.e("response of gateway details12", "response.toString()")

                }
            }
        )


    }
}