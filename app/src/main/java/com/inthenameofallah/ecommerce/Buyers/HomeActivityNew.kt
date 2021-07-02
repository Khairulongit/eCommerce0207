package com.inthenameofallah.ecommerce.Buyers

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.inthenameofallah.ecommerce.Adapters.ProductAdapterNEW
import com.inthenameofallah.ecommerce.Admin.AdminActivity
import com.inthenameofallah.ecommerce.Admin.AdminCategoryActivity
import com.inthenameofallah.ecommerce.Model.CartModel
import com.inthenameofallah.ecommerce.Model.ProductModel
import com.inthenameofallah.ecommerce.Model.Users
import com.inthenameofallah.ecommerce.NetworkConnection
import com.inthenameofallah.ecommerce.Prevalent.Prevalent
import com.inthenameofallah.ecommerce.R
import com.inthenameofallah.ecommerce.databinding.ActivityHomeNewBinding
import com.nex3z.notificationbadge.NotificationBadge
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import io.paperdb.Paper
import kotlin.system.exitProcess


class HomeActivityNew : AppCompatActivity() {

    private lateinit var binding: ActivityHomeNewBinding
    var toggle: ActionBarDrawerToggle? = null
    lateinit var hearderview:View
    private var Searchiinput:String=""
    private var totalallcart:Int=0
    var searchView: SearchView? = null


    //    private var productAdapter:ProductAdapter?=null
    private var productAdapter:ProductAdapterNEW?=null
    private var productList:MutableList<ProductModel>?=null
    var progressDialog: ProgressDialog? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeNewBinding.inflate(layoutInflater)
        setContentView(binding.root)



        progressDialog = ProgressDialog(this)

        progressDialog?.show()
        progressDialog?.setContentView(R.layout.progress_dialog)
//        progressDialog?.window?.setBackgroundDrawableResource(
//                android.R.color.transparent
//        )



        // Toolbar Logic below

        val toolbar = findViewById<Toolbar>(R.id.toolbarnewmade)
        setSupportActionBar(toolbar)

//        val textView = findViewById<TextView>(R.id.toolbartext)
//        textView.text="It is working"


        supportActionBar!!.setDisplayShowTitleEnabled(false)
//        val ab = supportActionBar
//        ab!!.setDisplayHomeAsUpEnabled(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = resources.getColor(R.color.status, this.theme)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.status)
        }


        //Toolbar Logic Above



//        if (Build.VERSION.SDK_INT < 16) {
//            window.setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN
//            )
//        }

        Log.wtf("KISLAM", "HomeActivityNew")




        val linearLayoutManager = LinearLayoutManager(applicationContext)
        linearLayoutManager.reverseLayout=true  // new product at top
        linearLayoutManager.stackFromEnd=true  // new product at top



        hearderview=binding.navView.getHeaderView(0)


        var recyclerView=findViewById<RecyclerView>(R.id.recycle_view)
        var nointernet=findViewById<ImageView>(R.id.nointenet)
//        var inputtext=findViewById<EditText>(R.id.search_product_name)
//        var searchbtn=findViewById<Button>(R.id.search_product)
        var addprdbtn=findViewById<FloatingActionButton>(R.id.fabnew)


        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, Observer { isConnected->
            if (isConnected){

                recyclerView.visibility=View.VISIBLE
                nointernet.visibility=View.GONE
            }else{
                recyclerView.visibility=View.GONE
                nointernet.visibility=View.VISIBLE

            }

        })


        if (Prevalent.UserType!="Seller"){

            addprdbtn.visibility=View.INVISIBLE
        }

        if (Prevalent.UserType=="Seller")
        {
            hideItemseller()
            getproduct()

        }else {
            hideItem()
            getproductall()
        }


        addprdbtn.setOnClickListener {
            val intent = Intent(this, AdminCategoryActivity::class.java)
            startActivity(intent)

//            finish()
        }






        productList = ArrayList()
        productAdapter = ProductAdapterNEW(applicationContext)
        productAdapter!!.setData(productList as ArrayList<ProductModel>)

        recyclerView!!.layoutManager =LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView!!.adapter =productAdapter


        Paper.init(this)



        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        toggle = ActionBarDrawerToggle(this, binding.drawer, toolbar, R.string.OPEN, R.string.CLOSE)
        toggle!!.syncState()



        var usernameTextview:TextView=hearderview.findViewById<TextView>(R.id.user_profile_name)
        var profileimageview:CircleImageView=hearderview.findViewById<CircleImageView>(R.id.user_profile_image)

        usernameTextview.text = Prevalent.Displayname
        Picasso.get().load(Prevalent.Photourl).placeholder(R.drawable.profile).into(profileimageview)


        binding.navView.setNavigationItemSelectedListener{

            when (it.itemId) {
                R.id.nav_settings -> {

                    if (Prevalent.UserType == "Buyer") {

                        val intent = Intent(this, SettingsActivity::class.java)
                        startActivity(intent)
//                        finish()
                    }


                }
                R.id.nav_cart -> {


                    if (Prevalent.UserType == "Buyer") {


                        val intent = Intent(this, CartActivity::class.java)

//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK + Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
//                        finish()


//                        Toast.makeText(applicationContext, "Cart clicked", Toast.LENGTH_SHORT)
//                                .show()
                    }
                }
                R.id.nav_manageprd -> {

                    val intent = Intent(applicationContext, AdminCategoryActivity::class.java)
                    startActivity(intent)
//                    finish()

                }
                R.id.nav_myorders -> {
                    // Multiline action


                    val intent = Intent(this, MyOrdersActivity::class.java)

//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK + Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
//                    finish()

                }

                R.id.nav_neworders -> {
                    // Multiline action


                    val intent = Intent(this, AdminActivity::class.java)
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK + Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
//                    finish()

                }

                R.id.nav_canorders -> {
                    // Multiline action


                    val intent = Intent(this, AdminActivity::class.java)
                    intent.putExtra("status", "canorder")
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK + Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
//                    finish()

                }

                R.id.nav_pendingdelivey -> {
                    // Multiline action


                    val intent = Intent(this, AdminActivity::class.java)
                    intent.putExtra("status", "penorder")
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK + Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
//                    finish()

                }

                R.id.nav_delivered -> {
                    // Multiline action


                    val intent = Intent(this, AdminActivity::class.java)
                    intent.putExtra("status", "delorder")
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK + Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
//                    finish()

                }

                R.id.logout -> {
                    // Multiline action
                    FirebaseAuth.getInstance().signOut()
                    Paper.book().destroy()

                    val intent = Intent(this, LoginActivity::class.java)

                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK + Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    finish()

//                    Toast.makeText(applicationContext, "Gallery Clicked", Toast.LENGTH_SHORT).show()
                    binding.drawer.setBackgroundColor(Color.RED)
                }


            }

                // Close the drawer
                binding.drawer.closeDrawer(GravityCompat.START)
                        true
        }




    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)



        val item = menu.findItem(R.id.searchmenu)
        val updatecart = menu.findItem(R.id.updatecart)
        val view = menu.findItem(R.id.updatecart).actionView
        val badge = view.findViewById<View>(R.id.badge) as NotificationBadge
        getcart(badge)

//        badge.setText(totalcount.toString())

        if(Prevalent.UserType=="Seller"){

            item.isVisible=false
            view.isVisible=false
            updatecart.isVisible=false
            badge.isVisible=false
        }
//        updatecartcount()
        view.setOnClickListener {
            //                Toast.makeText(MainActivity.this, "Notthing", Toast.LENGTH_SHORT).show();
            startActivity(Intent(applicationContext, CartActivity::class.java))
            //                finish();
        }


        // SEARCHVIEW LOGIC IS BELOW
        searchView = item.actionView as SearchView
        searchView!!.queryHint = "Type Product Name"
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                productAdapter?.filter?.filter(query)

                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                productAdapter?.filter?.filter(newText)
                return true
            }
        })


//        SEARCHVIEW LOGIC IS ABOVE
        return super.onCreateOptionsMenu(menu)
    }


    override fun onStart() {
        super.onStart()

        progressDialog?.dismiss()




    }



    private fun getproductall() {

        Prevalent.Activityname="HomeActivityNew"
        val Sellerall = FirebaseDatabase.getInstance().reference.child("Products")


        Sellerall.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                productList?.clear()


                for (userid in snapshot.children) {

                    val user = userid.getValue(Users::class.java)
                    user?.userid = userid.key.toString()


                    val Productall =
                            FirebaseDatabase.getInstance().reference.child("Products").child(
                                    userid.key.toString()
                            )

                    Productall.addValueEventListener(object : ValueEventListener {

                        override fun onDataChange(snapshot: DataSnapshot) {
                            for (productvalue in snapshot.children) {
//
                                val product = productvalue.getValue(ProductModel::class.java)
                                product?.productid = productvalue.key.toString()

                                if (product != null) {
                                    productList!!.add(product)
                                }
                                productAdapter!!.notifyDataSetChanged()
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


    private fun getproduct() {

        val onlyseller = FirebaseDatabase.getInstance().reference.child("Products").child(
                FirebaseAuth.getInstance().currentUser.uid
        )



        productList?.clear()

        onlyseller.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {


                for (productvalue in snapshot.children) {
                    val product = productvalue.getValue(ProductModel::class.java)




                    if (product != null) {
                        productList!!.add(product)
                    }
                    productAdapter!!.notifyDataSetChanged()
                }
            }


            override fun onCancelled(error: DatabaseError) {
            }


        })




    }


    private fun hideItem() {
        var navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        val nav_Menu: Menu = navigationView.getMenu()
        nav_Menu.findItem(R.id.nav_manageprd).isVisible = false
        nav_Menu.findItem(R.id.nav_delivered).isVisible = false
        nav_Menu.findItem(R.id.nav_pendingdelivey).isVisible = false
        nav_Menu.findItem(R.id.nav_canorders).isVisible = false
        nav_Menu.findItem(R.id.nav_neworders).isVisible = false
//        nav_Menu.findItem(R.id.searchmenu).isVisible = false

    }


    private fun hideItemseller() {
        var navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        val nav_Menu: Menu = navigationView.getMenu()
        nav_Menu.findItem(R.id.nav_cart).setVisible(false)
//        nav_Menu.findItem(R.id.searchmenu).isVisible = false
        nav_Menu.findItem(R.id.nav_myorders).setVisible(false)
        nav_Menu.findItem(R.id.nav_settings).setVisible(false)
    }

    override fun onBackPressed() {
        run {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Exit")
            builder.setMessage("Are You Sure?")
            builder.setPositiveButton("Yes") { dialog, which ->
                FirebaseAuth.getInstance().signOut()
                finishAffinity()
                exitProcess(0)
            }
            builder.setNegativeButton("No") { dialog, which -> dialog.dismiss() }
            val alert = builder.create()
            alert.show()
        }
    }





    private fun getcart(badge: NotificationBadge) {

        var CartListref = FirebaseDatabase.getInstance().reference.child("Cart List").
        child("User View").child(FirebaseAuth.getInstance().currentUser.uid).child("In Cart Item")

        CartListref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (cartvalue in snapshot.children) {

                    val cart = cartvalue.getValue(CartModel::class.java)

                    if (cart != null) {

                        totalallcart += cart.quantity.toInt()

                    }
                }
                badge.setText(totalallcart.toString())
                Log.wtf("shahnaz", totalallcart.toString())

            }


            override fun onCancelled(error: DatabaseError) {
            }
        })
    }



}
