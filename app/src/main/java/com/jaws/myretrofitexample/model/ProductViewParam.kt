package com.jaws.myretrofitexample.model

import com.jaws.myretrofitexample.data.network.api.model.Product

data class ProductViewParam(
    val id: Int,
    val title: String,
    val desc: String,
    val price: Long,
    val images: List<String>
)

fun Product.toProductViewParam() = ProductViewParam(
    id = this.id,
    title = this.title,
    desc = this.desc,
    price = this.price,
    images = this.images
)

fun Collection<Product>.toProductViewParams() = this.map{
    it.toProductViewParam()
}