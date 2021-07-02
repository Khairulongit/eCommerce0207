package com.inthenameofallah.ecommerce.Buyers

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.inthenameofallah.ecommerce.Adapters.MyOrdersAdapter
import com.inthenameofallah.ecommerce.Model.OrderModel
import com.inthenameofallah.ecommerce.NetworkConnection
import com.inthenameofallah.ecommerce.Prevalent.Prevalent
import com.inthenameofallah.ecommerce.R
import com.inthenameofallah.ecommerce.databinding.ActivityMyOrdersBinding

class MyOrdersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyOrdersBinding

    private var myOrdersAdapter: MyOrdersAdapter?=null
    private var orderList:MutableList<OrderModel>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.wtf("KISLAM","MyOrdersActivity")


        // Toolbar Logic below

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val textView = findViewById<TextView>(R.id.toolbartext)

        textView.text="Your Current Orders!!"
        textView.gravity= Gravity.CENTER_HORIZONTAL



        supportActionBar!!.setDisplayShowTitleEnabled(false)
        val ab = supportActionBar
//        ab!!.setDisplayHomeAsUpEnabled(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = resources.getColor(R.color.status, this.theme)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.status)
        }


        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, Observer { isConnected->
            if (isConnected){

                binding.orderRecycleView.visibility=View.VISIBLE
                binding.nointenet.visibility=View.GONE
            }else{
                binding.orderRecycleView.visibility=View.GONE
                binding.nointenet.visibility=View.VISIBLE

            }

        })



        //Toolbar Logic Above


        binding.liner1.visibility=View.GONE
        val linearLayoutManager = LinearLayoutManager(applicationContext)
        linearLayoutManager.reverseLayout=true  // new product at top
        linearLayoutManager.stackFromEnd=true  // new product at top


        var recyclerView=findViewById<RecyclerView>(R.id.orderRecycleView)

        recyclerView!!.layoutManager=linearLayoutManager
        recyclerView.setHasFixedSize(true);


        orderList = ArrayList()
        myOrdersAdapter=this?.let { MyOrdersAdapter(it, orderList as ArrayList<OrderModel>) }

        recyclerView!!.adapter =myOrdersAdapter




//        if (Prevalent.UserType=="Seller")
//        {
//            getmyorderssller()
//        }else
            getmyordersbuyer()




    }

    private fun getmyordersbuyer() {

        val BuyerAllOrders = FirebaseDatabase.getInstance().reference.child("Placed Orders").child(FirebaseAuth.getInstance().currentUser.uid)


//        orderList?.clear()

        BuyerAllOrders.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                orderList?.clear()

                for (orders in snapshot.children) {



                    val order = orders.getValue(OrderModel::class.java)


                                if (order != null) {
                                    orderList!!.add(order)
                                }
                                myOrdersAdapter!!.notifyDataSetChanged()
                            }
                        }





            override fun onCancelled(error: DatabaseError) {
            }


        })

    }


    override fun onBackPressed() {

        val intent = Intent(this, HomeActivityNew::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK + Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }
}