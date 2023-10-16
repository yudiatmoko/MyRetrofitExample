package com.jaws.myretrofitexample.data.repository

import com.jaws.myretrofitexample.data.datasource.ProductDataSource
import com.jaws.myretrofitexample.model.ProductsResponse
import com.jaws.myretrofitexample.utils.ResultWrapper
import com.jaws.myretrofitexample.utils.proceedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart

interface ProductRepository {
    suspend fun getProductList(): Flow<ResultWrapper<ProductsResponse>>
}

class ProductRepositoryImpl(
    private val dataSource: ProductDataSource
) : ProductRepository{
    override suspend fun getProductList(): Flow<ResultWrapper<ProductsResponse>> {
        return proceedFlow {
            dataSource.getProductList()
        }.onStart {
            emit(ResultWrapper.Loading())
            delay(2000)
        }
    }
}
