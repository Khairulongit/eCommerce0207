//package com.inthenameofallah.ecommerce.Seller
//
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.widget.Toast
//import com.google.android.material.bottomnavigation.BottomNavigationView
//import androidx.appcompat.app.AppCompatActivity
//import androidx.fragment.app.Fragment
//import com.google.firebase.auth.FirebaseAuth
//import com.inthenameofallah.ecommerce.Fragments.HomeSellerFragment
//import com.inthenameofallah.ecommerce.R
//
//class SellerHomeActivity : AppCompatActivity() {
//
//    val mAuth=FirebaseAuth.getInstance()
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_seller_home)
//
//        val homeSellerFragment = HomeSellerFragment()
//
//        makeCurrentFragment(homeSellerFragment)
//
//
//
//
//        val navView: BottomNavigationView = findViewById(R.id.nav_view_seller)
//
//
//        navView.setOnNavigationItemSelectedListener {
//            when(it.itemId){
//
//                R.id.navigation_add_seller -> {
//
//
//                    val intent = Intent(applicationContext, SellerAddNewProductActivity::class.java)
//                    intent.putExtra("category","tshirt")
//                    startActivity(intent)
//                    Toast.makeText(applicationContext, "hello there", Toast.LENGTH_SHORT).show()
//
//                }
//                R.id.navigation_home_seller -> {
//
//                }
//                R.id.navigation_logout_seller -> {
////                    val fragment = StoreFragment()
////                    supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
////                        .commit()
//
//                    mAuth.signOut()
//
//                    val intent = Intent(this, MainActivity::class.java)
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK+ Intent.FLAG_ACTIVITY_NEW_TASK)
//                    startActivity(intent)
//                    finish()
//                }
//            }
//
//        true
//
//
//            }
//        }
//
////        val navController = findNavController(R.id.fl_wrapper)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
////        val appBarConfiguration = AppBarConfiguration(
////            setOf(
////                R.id.navigation_home_seller, R.id.navigation_add_seller, R.id.navigation_logout_seller
////            )
////        )
////        setupActionBarWithNavController(navController, appBarConfiguration)
////        navView.setupWithNavController(navController)
//
////        val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
////
////            when (menuItem.itemId) {
////
////            false
////        }
////        }
//
//    private fun makeCurrentFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
//
//        replace(R.id.fl_wrapper,fragment)
//        commit()
//    }
//
//
//
//
//}
