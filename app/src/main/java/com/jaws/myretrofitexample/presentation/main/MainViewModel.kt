package com.jaws.myretrofitexample.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaws.myretrofitexample.data.network.ProductService
import com.jaws.myretrofitexample.data.repository.ProductRepository
import com.jaws.myretrofitexample.model.ProductsResponse
import com.jaws.myretrofitexample.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(
    private val repo: ProductRepository
) : ViewModel() {

    private val _responseLiveData = MutableLiveData<ResultWrapper<ProductsResponse>>()

    val responseLiveData: LiveData<ResultWrapper<ProductsResponse>>
        get() = _responseLiveData


    fun getProducts(){
        viewModelScope.launch(Dispatchers.Main){
            try {
                repo.getProductList().collect{ result ->
                    _responseLiveData.postValue(result)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}