package com.example.dummyapi.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.animation.Animation
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dummyapi.R
import com.example.dummyapi.adapter.ProductAdapter
import com.example.dummyapi.api.ApiConfig
import com.example.dummyapi.data.Product
import com.example.dummyapi.data.ProductAgain
import com.example.dummyapi.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var topAnim: Animation
    private lateinit var bottomAnim: Animation

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var call: Call<ProductAgain>
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Animations
        topAnim = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.top_animation)
        bottomAnim = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.bottom_animation)

        binding.Product.startAnimation(topAnim)

        recyclerView = findViewById(R.id.Product)

        productAdapter = ProductAdapter { product ->  productOnClick(product)}
        recyclerView.adapter = productAdapter
        recyclerView.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

        recyclerView.setOnClickListener {
            getData()
        }
        getData()
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_home -> {
                // Tambahkan logika untuk menangani klik menu "Home"
                Toast.makeText(this, "Menu Home Clicked", Toast.LENGTH_SHORT).show()
                true
            }

            R.id.menu_about -> {
                // Tambahkan logika untuk menangani klik menu "About"
                openAboutPage()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openAboutPage() {
        // Tambahkan logika untuk membuka halaman "About"
        Toast.makeText(this, "Menu About Clicked", Toast.LENGTH_SHORT).show()
        // Misalnya, bisa berupa intent untuk membuka aktivitas AboutActivity
         val intent = Intent(this, AboutActivity::class.java)
         startActivity(intent)
    }

    private fun productOnClick(product: Product) {
        Toast.makeText(applicationContext, product.description, Toast.LENGTH_SHORT).show()
    }

    private fun getData() {
        binding.refreshLayout.isActivated = true

        call = ApiConfig.ApiService().getAll()
        call.enqueue(object : Callback<ProductAgain>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<ProductAgain>, response: Response<ProductAgain>) {
                binding.refreshLayout.isActivated = false
                if (response.isSuccessful) {
                    productAdapter.submitList(response.body()?.products)
                    productAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<ProductAgain>, t: Throwable) {
                binding.refreshLayout.isActivated = false
                Toast.makeText(applicationContext, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }
}
