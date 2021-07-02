//package com.inthenameofallah.ecommerce.Admin
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.util.Log
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.DatabaseError
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.database.ValueEventListener
//import com.inthenameofallah.ecommerce.Adapters.CartAdapter
//import com.inthenameofallah.ecommerce.Model.CartModel
//import com.inthenameofallah.ecommerce.databinding.ActivityAdminUserProductBinding
//
//class AdminUserProductActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityAdminUserProductBinding
//    private var cartAdapter: CartAdapter?=null
//    private var cartList:MutableList<CartModel>?=null
//    var userID:String=""
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityAdminUserProductBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//
//        userID = intent.getStringExtra("uid").toString()
//
//        binding.recyOrderList.setHasFixedSize(true)
//
//
//        val linearLayoutManager = LinearLayoutManager(applicationContext)
//        linearLayoutManager.reverseLayout=true  // new post at top
//        linearLayoutManager.stackFromEnd=true  // new post at top
//        binding.recyOrderList.layoutManager=linearLayoutManager
//
//
//        cartList = ArrayList()
//        cartAdapter=this?.let { CartAdapter(it,cartList as ArrayList<CartModel>) }
//
//        binding.recyOrderList!!.adapter =cartAdapter
//        getcart()
//
//
//
//
//
//    }
//
//
//    private fun getcart() {
//
//        val productref = FirebaseDatabase.getInstance().reference.child("Cart List")
//            .child("Admin View")
//            .child(userID).child("Products")
//
//        productref.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//                cartList?.clear()
//
////                if(snapshot.exists()) {
//                for (cartvalue in snapshot.children) {
//
//                    val cart = cartvalue.getValue(CartModel::class.java)
//
//
////                    for (userid in (followinglist as ArrayList<String>)){
////                        if (post!!.getPublishers() == userid){
//                    if (cart != null) {
//                        cartList!!.add(cart)
//
////                        overtotalproce += cart.price.toInt() * cart.quantity.toInt()
//
//
//                        }
//
//                        cartAdapter!!.notifyDataSetChanged()
//
//                }
//            }
////                }
//
//
//            override fun onCancelled(error: DatabaseError) {
//            }
//        })
//
//    }
//
//}