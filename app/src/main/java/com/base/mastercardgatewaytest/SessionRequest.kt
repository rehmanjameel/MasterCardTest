package com.base.mastercardgatewaytest

data class SessionRequest(
    val session: SessionData
)

data class SessionData(
    val authenticationLimit: Int
)

data class SessionResponse(
    val session: SessionDetails
)

data class SessionDetails(
    val id: String,  // Session ID returned from Mastercard
    val updateStatus: String
)
