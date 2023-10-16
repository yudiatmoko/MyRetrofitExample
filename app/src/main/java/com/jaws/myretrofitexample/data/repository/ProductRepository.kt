package com.jaws.myretrofitexample.data.repository

import com.jaws.myretrofitexample.data.network.api.datasource.ProductDataSource
import com.jaws.myretrofitexample.model.ProductViewParam
import com.jaws.myretrofitexample.model.toProductViewParams
import com.jaws.myretrofitexample.utils.ResultWrapper
import com.jaws.myretrofitexample.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProductList(): Flow<ResultWrapper<List<ProductViewParam>>>
}

class ProductRepositoryImpl(
    private val dataSource: ProductDataSource
) : ProductRepository{
    override suspend fun getProductList(): Flow<ResultWrapper<List<ProductViewParam>>> {
        return proceedFlow {
            dataSource.getProductList().products.toProductViewParams()
        }
    }
}
