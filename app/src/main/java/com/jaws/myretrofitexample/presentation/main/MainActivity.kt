package com.jaws.myretrofitexample.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jaws.myretrofitexample.data.network.api.datasource.ProductDataSourceImpl
import com.jaws.myretrofitexample.data.network.api.service.ProductService
import com.jaws.myretrofitexample.data.repository.ProductRepositoryImpl
import com.jaws.myretrofitexample.databinding.ActivityMainBinding
import com.jaws.myretrofitexample.presentation.main.product.ProductListAdapter
import com.jaws.myretrofitexample.utils.GenericViewModelFactory
import com.jaws.myretrofitexample.utils.proceedWhen

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModels {
        GenericViewModelFactory.create(createViewModel())
    }

    private fun createViewModel(): MainViewModel {
        val service = ProductService.invoke()
        val dataSource = ProductDataSourceImpl(service)
        val repo = ProductRepositoryImpl(dataSource)
        return MainViewModel(repo)
    }

    private val adapterProduct: ProductListAdapter by lazy {
        ProductListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setRecyclerViewProduct()
        observeData()
    }

    private fun observeData() {
        viewModel.getProducts()
        viewModel.responseLiveData.observe(this){
            it.proceedWhen(
                doOnSuccess = {
                    binding.rvProduct.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    it.payload?.let { adapterProduct.setData(it) }
                },
                doOnLoading = {
                    binding.rvProduct.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                    binding.layoutState.pbLoading.isVisible = true
                },
                doOnError = {err ->
                    binding.rvProduct.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.text =
                        err.exception?.message.orEmpty()
                }
            )
        }
    }

    private fun setRecyclerViewProduct() {
        binding.rvProduct.apply {
            layoutManager = LinearLayoutManager(
                context,
                RecyclerView.VERTICAL,
                false
            )
            adapter = adapterProduct
        }
    }
}