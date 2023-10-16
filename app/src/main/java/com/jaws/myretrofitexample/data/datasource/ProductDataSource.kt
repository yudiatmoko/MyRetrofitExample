package com.jaws.myretrofitexample.data.datasource

import com.jaws.myretrofitexample.data.network.ProductService
import com.jaws.myretrofitexample.model.ProductsResponse
import kotlinx.coroutines.flow.Flow

interface ProductDataSource {
    suspend fun getProductList(): ProductsResponse
}

class ProductDataSourceImpl(
    private val api: ProductService
) : ProductDataSource{
    override suspend fun getProductList(): ProductsResponse {
        return api.getProducts()
    }
}

