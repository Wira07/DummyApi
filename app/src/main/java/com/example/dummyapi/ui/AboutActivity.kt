package com.example.dummyapi.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.dummyapi.R
import com.example.dummyapi.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAboutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnBack: ImageButton = binding.btnBack
        // Menangani klik tombol kembali
        btnBack.setOnClickListener {
            finish() // Menutup halaman AboutActivity dan kembali ke MainActivity
        }
    }
}