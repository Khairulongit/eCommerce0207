//package com.inthenameofallah.ecommerce.Buyers
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.DatabaseError
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.database.ValueEventListener
//import com.inthenameofallah.ecommerce.DeletedItem.ProductAdapter
//import com.inthenameofallah.ecommerce.Model.ProductModel
//import com.inthenameofallah.ecommerce.databinding.ActivitySearchProductBinding
//
//class SearchProductActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivitySearchProductBinding
//    private var Searchiinput:String=""
//    private var productAdapter: ProductAdapter?=null
//    private var productList:MutableList<ProductModel>?=null
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivitySearchProductBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val linearLayoutManager = LinearLayoutManager(applicationContext)
//        linearLayoutManager.reverseLayout=true  // new post at top
//        linearLayoutManager.stackFromEnd=true  // new post at top
//        binding.recySearchList.layoutManager=linearLayoutManager
//
//        Searchiinput=binding.searchProductName.text.toString()
//
//        productList = ArrayList()
//        productAdapter=this?.let { ProductAdapter(it,productList as ArrayList<ProductModel>) }
//
//        binding!!.recySearchList.adapter =productAdapter
//
//
//
//        binding.searchProduct.setOnClickListener {
//
//            getproduct()
//
//
//
//
//        }
//
//
//
//
//    }
//
//    private fun getproduct() {
//
//        val Productref = FirebaseDatabase.getInstance().reference.child("Products").orderByChild("productname")
//
//        Productref.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//                productList?.clear()
//
////                if(snapshot.exists()) {
//                for (productvalue in snapshot.children) {
//
//                    val post = productvalue.getValue(ProductModel::class.java)
//
////                    for (userid in (followinglist as ArrayList<String>)){
////                        if (post!!.getPublishers() == userid){
//                    if (post != null) {
//                        productList!!.add(post)
//                    }
////                        }
//
//                    productAdapter!!.notifyDataSetChanged()
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
//}