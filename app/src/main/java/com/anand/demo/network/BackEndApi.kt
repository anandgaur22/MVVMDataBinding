package com.anand.demo.network

import com.google.gson.JsonObject
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface BackEndApi {

    @POST("jlogin")
    suspend fun Login(@Body requestBody: RequestBody): Response<ResponseBody>

    @POST("signup")
    suspend fun Signup(@Body requestBody: RequestBody): Response<ResponseBody>

}