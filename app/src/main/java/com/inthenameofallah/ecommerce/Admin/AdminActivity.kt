package com.inthenameofallah.ecommerce.Admin

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.inthenameofallah.ecommerce.Adapters.MyOrdersAdapter
import com.inthenameofallah.ecommerce.Buyers.HomeActivityNew
import com.inthenameofallah.ecommerce.Model.OrderModel
import com.inthenameofallah.ecommerce.NetworkConnection
import com.inthenameofallah.ecommerce.R
import com.inthenameofallah.ecommerce.databinding.ActivityAdminBinding

class AdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding
    private var orderAdapter:MyOrdersAdapter?=null
    private var orderlist:MutableList<OrderModel>?=null
    private var status:String?=null
    var ordstaus:String=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, Observer { isConnected->
            if (isConnected){
                binding.recyOrderList.visibility= View.VISIBLE
                binding.nointenet.visibility= View.GONE

            }else{
                binding.recyOrderList.visibility= View.GONE
                binding.nointenet.visibility= View.VISIBLE
            }

        })



        // Toolbar Logic below

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        var textView = findViewById<TextView>(R.id.toolbartext)

        textView.foregroundGravity= Gravity.CENTER_HORIZONTAL
        textView.textSize= 15F

        supportActionBar!!.setDisplayShowTitleEnabled(false)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = resources.getColor(R.color.status, this.theme)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.status)
        }


        //Toolbar Logic Above


        val linearLayoutManager = LinearLayoutManager(applicationContext)
        linearLayoutManager.reverseLayout=true  // new post at top
        linearLayoutManager.stackFromEnd=true

        binding.recyOrderList.layoutManager=linearLayoutManager

        status= intent.getStringExtra("status").toString()



        orderlist = ArrayList()
        orderAdapter=this?.let { MyOrdersAdapter(it,orderlist as ArrayList<OrderModel>) }

        binding.recyOrderList!!.adapter =orderAdapter

//        if(status!=null){
            getspeOrders(textView)

//        }else
//            getOrders()




    }

    private fun getspeOrders(textView: TextView) {


        when(status){
            "canorder"-> {status = "Not Approved"
                            binding.orderstypr.text="Cancelled Orders"
                textView.text="Your Cancelled Orders"


            }
            "penorder"-> {status = "Approved"
                binding.orderstypr.text="Pending Delivery"

                textView.text="Your Pending Delivery Orders"

            }
            "delorder"-> {status = "Delivered"
                binding.orderstypr.text="Delivered Orders"
                textView.text="Your Delivered Orders"


            }
            else ->{
                    status = "Pending Approval"
                    binding.orderstypr.text = "New Orders"
                textView.text="Your Pending Approval Orders"




            }
        }



        val Orderref = FirebaseDatabase.getInstance().reference.child("Placed Orders")

        Orderref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                orderlist?.clear()

                for (orderValue in snapshot.children) {

                    val order = orderValue.getValue(OrderModel::class.java)
                    order?.orderid = orderValue.key.toString()

                    val Orderbyseller = FirebaseDatabase.getInstance().reference.child("Placed Orders").child(orderValue.key.toString())

                    Orderbyseller.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {

                            for (ordervaluefinal in snapshot.children) {

                                val product = ordervaluefinal.getValue(OrderModel::class.java)
                                product?.sellerid = ordervaluefinal.child("sellerid").value.toString()



                                if (product != null && ordervaluefinal.child("sellerid").value.toString() == FirebaseAuth.getInstance().currentUser.uid && ordervaluefinal.child("state").value.toString() ==status) {
                                    orderlist?.add(product)
                                }
                                orderAdapter!!.notifyDataSetChanged()

                            }

                        }

                        override fun onCancelled(error: DatabaseError) {
                        }


                    })
                }



            }



            override fun onCancelled(error: DatabaseError) {
            }
        })



    }

//    private fun getOrders() {
//
//        Log.wtf("KISLAM1","KISLAM1")
//
//
//
//        val Orderref = FirebaseDatabase.getInstance().reference.child("Placed Orders")
//
//        Orderref.addValueEventListener(object : ValueEventListener {
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//                orderlist?.clear()
//
//                for (orderValue in snapshot.children) {
//
//                    val order = orderValue.getValue(OrderModel::class.java)
//                    order?.orderid = orderValue.key.toString()
//
//                    val Orderbyseller = FirebaseDatabase.getInstance().reference.child("Placed Orders").child(orderValue.key.toString())
//
//                    Orderbyseller.addValueEventListener(object : ValueEventListener {
//                        override fun onDataChange(snapshot: DataSnapshot) {
//
//                            for (ordervaluefinal in snapshot.children) {
//
//                                val product = ordervaluefinal.getValue(OrderModel::class.java)
//                                product?.sellerid = ordervaluefinal.child("sellerid").value.toString()
//
//
//
//                                if (product != null && ordervaluefinal.child("sellerid").value.toString() == FirebaseAuth.getInstance().currentUser.uid && ordervaluefinal.child("state").value.toString() =="Pending Approval" ) {
//                                    orderlist?.add(product)
//                                }
//                                orderAdapter!!.notifyDataSetChanged()
//
//                            }
//
//                        }
//
//                        override fun onCancelled(error: DatabaseError) {
//                        }
//
//
//                    })
//                }
//
//
//
//                }
//
//
//
//            override fun onCancelled(error: DatabaseError) {
//            }
//        })
//
//    }

    override fun onBackPressed() {

        val intent = Intent(this, HomeActivityNew::class.java)
        startActivity(intent)
        finish()
    }


}