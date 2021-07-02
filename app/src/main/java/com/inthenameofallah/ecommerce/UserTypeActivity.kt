package com.inthenameofallah.ecommerce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.inthenameofallah.ecommerce.Buyers.LoginActivity
import com.inthenameofallah.ecommerce.databinding.ActivityUserTypeBinding

class UserTypeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserTypeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.admin.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            intent.putExtra("category","Admin")
            startActivity(intent)
        }

        binding.seller.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            intent.putExtra("category","Seller")
            startActivity(intent)
        }

        binding.buyer.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            intent.putExtra("category","Buyer")
            startActivity(intent)
        }
    }
}