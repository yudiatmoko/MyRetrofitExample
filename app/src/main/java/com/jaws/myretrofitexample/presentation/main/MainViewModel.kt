package com.jaws.myretrofitexample.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaws.myretrofitexample.data.repository.ProductRepository
import com.jaws.myretrofitexample.data.network.api.model.ProductsResponse
import com.jaws.myretrofitexample.model.ProductViewParam
import com.jaws.myretrofitexample.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val repo: ProductRepository
) : ViewModel() {

    private val _responseLiveData = MutableLiveData<ResultWrapper<List<ProductViewParam>>>()

    val responseLiveData: LiveData<ResultWrapper<List<ProductViewParam>>>
        get() = _responseLiveData

    fun getProducts(){
        viewModelScope.launch(Dispatchers.Main){
            repo.getProductList().collect{ result ->
                _responseLiveData.postValue(result)
            }
        }
    }
}