package com.example.dummyapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dummyapi.data.Product
import com.example.dummyapi.databinding.ItemLayoutBinding

class ProductAdapter(private val onClick: (Product) -> Unit) :
    ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductCallBack) {

    class ProductViewHolder(private val binding: ItemLayoutBinding, val onClick: (Product) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        private var currentProduct: Product? = null

        init {
            binding.root.setOnClickListener {
                currentProduct?.let {
                    onClick(it)
                }
            }
        }

        fun bind(product: Product) {
            currentProduct = product

            binding.itemNameRick.text = product.title
            binding.itemSpeciesRick.text = product.brand
            binding.itemStatusRick.text = product.price.toString()

            Glide.with(itemView).load(product.thumbnail).centerCrop().into(binding.itemImageRick)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }

}

object ProductCallBack : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }
}
