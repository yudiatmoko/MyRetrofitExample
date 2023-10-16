package com.jaws.myretrofitexample.core

interface ViewHolderBinder<T> {
    fun bind(item: T)
}