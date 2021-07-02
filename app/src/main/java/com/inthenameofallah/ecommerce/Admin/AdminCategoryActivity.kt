package com.inthenameofallah.ecommerce.Admin

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.inthenameofallah.ecommerce.Buyers.HomeActivityNew
import com.inthenameofallah.ecommerce.Buyers.LoginActivity
import com.inthenameofallah.ecommerce.NetworkConnection
import com.inthenameofallah.ecommerce.R
import com.inthenameofallah.ecommerce.Seller.SellerAddNewProductActivity
import com.inthenameofallah.ecommerce.databinding.ActivityAdminCategoryBinding
import io.paperdb.Paper

class AdminCategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityAdminCategoryBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, Observer { isConnected->
            if (isConnected){
                binding.intermetoff.visibility= View.GONE
                binding.interneton.visibility= View.VISIBLE

            }else{
                binding.intermetoff.visibility= View.VISIBLE
                binding.interneton.visibility= View.GONE
            }

        })

        // Toolbar Logic below

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        var textView = findViewById<TextView>(R.id.toolbartext)

        textView.text="Add New Product"
        textView.foregroundGravity=Gravity.CENTER_HORIZONTAL
        textView.textSize= 20F

        supportActionBar!!.setDisplayShowTitleEnabled(false)
        val ab = supportActionBar
//        ab!!.setDisplayHomeAsUpEnabled(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = resources.getColor(R.color.status, this.theme)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.status)
        }


        //Toolbar Logic Above


        binding.tshirt.setOnClickListener{
            val intent = Intent(applicationContext, SellerAddNewProductActivity::class.java)
            intent.putExtra("category","tshirt")
            startActivity(intent)
        }

        binding.laptop.setOnClickListener{
            val intent = Intent(this, SellerAddNewProductActivity::class.java)
            intent.putExtra("category","laptop")
            startActivity(intent)
        }

        binding.sunglass.setOnClickListener{
            val intent = Intent(this, SellerAddNewProductActivity::class.java)
            intent.putExtra("category","sunglass")
            startActivity(intent)
        }

        binding.watches.setOnClickListener{
            val intent = Intent(this, SellerAddNewProductActivity::class.java)
            intent.putExtra("category","watches")
            startActivity(intent)
        }

        binding.swater.setOnClickListener{
            val intent = Intent(this, SellerAddNewProductActivity::class.java)
            intent.putExtra("category","swater")
            startActivity(intent)
        }

        binding.hats.setOnClickListener{
            val intent = Intent(this, SellerAddNewProductActivity::class.java)
            intent.putExtra("category","hats")
            startActivity(intent)
        }

        binding.femaleShirt.setOnClickListener{
            val intent = Intent(this, SellerAddNewProductActivity::class.java)
            intent.putExtra("category","femaleShirt")
            startActivity(intent)
        }

        binding.headphones.setOnClickListener{
            val intent = Intent(this, SellerAddNewProductActivity::class.java)
            intent.putExtra("category","headphones")
            startActivity(intent)
        }

        binding.mobiles.setOnClickListener{
            val intent = Intent(this, SellerAddNewProductActivity::class.java)
            intent.putExtra("category","mobiles")
            startActivity(intent)
        }


        binding.adminLogout.setOnClickListener {
            Paper.book().destroy()
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK+Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        binding.chkOrdersBtn.setOnClickListener {
            val intent = Intent(this, AdminActivity ::class.java)
            startActivity(intent)
        }

        binding.modifyProductxBtn.setOnClickListener {
            val intent = Intent(this, HomeActivityNew ::class.java)
            startActivity(intent)
        }

    }

    override fun onBackPressed() {
        val intent = Intent(this, HomeActivityNew ::class.java)
        startActivity(intent)
    }
}