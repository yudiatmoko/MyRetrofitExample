package com.jaws.myretrofitexample.presentation.main.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.jaws.myretrofitexample.core.ViewHolderBinder
import com.jaws.myretrofitexample.databinding.ProductListItemBinding
import com.jaws.myretrofitexample.model.Product

class ProductListAdapter() : RecyclerView.Adapter<ProductItemViewHolder>(){

    private val differ = AsyncListDiffer(this,
        object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(
                oldItem: Product,
                newItem: Product,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Product,
                newItem: Product,
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        })

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductItemViewHolder {
        return ProductItemViewHolder(
            binding = ProductListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(
        holder: ProductItemViewHolder,
        position: Int,
    ) {
        (holder as ViewHolderBinder<Product>).bind(differ.currentList[position])
    }

    fun setData(data: List<Product>) {
        differ.submitList(data)
    }

    fun refreshList() {
        notifyItemRangeChanged(0,differ.currentList.size)
    }

}