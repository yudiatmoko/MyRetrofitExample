package com.jaws.myretrofitexample.data.network.api.datasource

import com.jaws.myretrofitexample.data.network.api.service.ProductService
import com.jaws.myretrofitexample.data.network.api.model.ProductsResponse

interface ProductDataSource {
    suspend fun getProductList(): ProductsResponse
}

class ProductDataSourceImpl(
    private val apiService: ProductService
) : ProductDataSource {
    override suspend fun getProductList(): ProductsResponse {
        return apiService.getProducts()
    }
}

