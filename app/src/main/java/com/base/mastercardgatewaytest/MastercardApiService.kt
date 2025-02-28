package com.base.mastercardgatewaytest

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface MastercardApiService {

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("api/rest/version/82/merchant/TESTMPGSTEST0101/session")
    fun createSession(
        @Header("Authorization") authHeader: String, // Required for authentication
        @Body requestBody: SessionRequest
    ): Call<SessionResponse>

}
