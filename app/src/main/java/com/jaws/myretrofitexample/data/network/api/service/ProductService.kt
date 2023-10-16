package com.jaws.myretrofitexample.data.network.api.service

import com.jaws.myretrofitexample.BuildConfig
import com.jaws.myretrofitexample.data.network.api.model.ProductsResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface ProductService {
    @GET("products?limit=10")
    suspend fun getProducts(): ProductsResponse

    companion object {
        @JvmStatic
        operator fun invoke(): ProductService {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(
                ProductService::class.java)
        }
    }
}