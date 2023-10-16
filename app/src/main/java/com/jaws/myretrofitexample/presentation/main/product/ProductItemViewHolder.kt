package com.jaws.myretrofitexample.presentation.main.product

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jaws.myretrofitexample.core.ViewHolderBinder
import com.jaws.myretrofitexample.databinding.ProductListItemBinding
import com.jaws.myretrofitexample.model.Product

class ProductItemViewHolder(
    private val binding: ProductListItemBinding
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Product>{

    override fun bind(item: Product) {
        binding.tvProductName.text = item.title
        binding.tvProductPrice.text = String.format("$ %,.0f", (item.price.toDouble()))
        binding.tvProductDesc.text = item.desc
        binding.ivProductImg.load(item.images[0])
    }
}