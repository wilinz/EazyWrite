package com.eazywrite.app.data.network.service

import com.eazywrite.app.data.model.BillsCropResponseKt
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface BillsService {
    @POST("/robot/v1.0/api/bills_crop")
    suspend fun billsCrop(@Body body: RequestBody): BillsCropResponseKt
}