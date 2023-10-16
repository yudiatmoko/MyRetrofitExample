package com.jaws.myretrofitexample.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jaws.myretrofitexample.data.datasource.ProductDataSourceImpl
import com.jaws.myretrofitexample.data.network.ProductService
import com.jaws.myretrofitexample.data.repository.ProductRepositoryImpl
import com.jaws.myretrofitexample.databinding.ActivityMainBinding
import com.jaws.myretrofitexample.presentation.main.product.ProductListAdapter
import com.jaws.myretrofitexample.utils.GenericViewModelFactory
import com.jaws.myretrofitexample.utils.proceedWhen
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

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
                    it.payload?.let { adapterProduct.setData(it.products) }
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