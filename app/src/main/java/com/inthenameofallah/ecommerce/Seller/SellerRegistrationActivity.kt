package com.inthenameofallah.ecommerce.Seller

import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.inthenameofallah.ecommerce.Admin.AdminCategoryActivity
import com.inthenameofallah.ecommerce.NetworkConnection
import com.inthenameofallah.ecommerce.R
import com.inthenameofallah.ecommerce.databinding.ActivitySellerRegistrationBinding

class SellerRegistrationActivity : AppCompatActivity() {

    val mAuth=FirebaseAuth.getInstance()

    private lateinit var binding: ActivitySellerRegistrationBinding
    val nameregex = "[a-zA-Z][a-zA-Z ]*".toRegex()
    val phoneregex = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[6789]\\d{9}$".toRegex()
    val pincoderegex = "^[1-9]{1}[0-9]{2}\\s{0,1}[0-9]{3}$".toRegex()
    val addrregex = "^([a-zA-Z\u0080-\u024F]+(?:. |-| |'))*[a-zA-Z\u0080-\u024F]*$".toRegex()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellerRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, Observer { isConnected->
            if (isConnected){
                binding.nointenet.visibility= View.GONE
                binding.interneton.visibility= View.VISIBLE

            }else{
                binding.nointenet.visibility= View.VISIBLE
                binding.interneton.visibility= View.GONE
            }

        })

        // Toolbar Logic below

        val toolbar = findViewById<Toolbar>(R.id.toolbarnewmade)
        setSupportActionBar(toolbar)

//        val textView = findViewById<TextView>(R.id.toolbartext)
//        textView.text="It is working"


//        supportActionBar!!.setDisplayShowTitleEnabled(false)
//        val ab = supportActionBar
//        ab!!.setDisplayHomeAsUpEnabled(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = resources.getColor(R.color.status, this.theme)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.status)
        }


        //Toolbar Logic Above



        binding.submitForm.setOnClickListener {
            registerseller()
        }
    }

    private fun registerseller() {

        val name=binding.shopname.text.toString()
        val phone=binding.sellercontact.text.toString()
        val type=binding.sellertype.text.toString()
        val address=binding.selleraddress.text.toString()


    when {

        TextUtils.isEmpty(name) -> Toast.makeText(this, "Shop Name is Empty", Toast.LENGTH_SHORT).show()
        TextUtils.isEmpty(phone) -> Toast.makeText(this, "Shop Phone is Empty", Toast.LENGTH_SHORT).show()
        TextUtils.isEmpty(type) -> Toast.makeText(this, "Type of Business is Empty", Toast.LENGTH_SHORT).show()
        TextUtils.isEmpty(address) -> Toast.makeText(this, "Shop Address is Empty", Toast.LENGTH_SHORT).show()

        (!name.matches(nameregex))-> {Toast.makeText(this, "WARNING: Enter a valid name !", Toast.LENGTH_SHORT).show() }

        (!phone.matches(phoneregex))->{Toast.makeText(this, "WARNING: Enter a valid Number !", Toast.LENGTH_SHORT).show() }

        (!address.matches(addrregex))->{Toast.makeText(this, "WARNING: Enter Valid Address!", Toast.LENGTH_SHORT).show() }

        (!type.matches(nameregex))->{Toast.makeText(this, "WARNING: Enter Valid Type of Business!", Toast.LENGTH_SHORT).show() }



        else -> {

            val sellerdialog = ProgressDialog(this)
            sellerdialog.setTitle("Updating Seller Account")
            sellerdialog.setMessage("Please wait,while we are checking the credentials..")
            sellerdialog.setCanceledOnTouchOutside(false)
            sellerdialog.show()

            var sid = mAuth.currentUser.uid
            val rootref = FirebaseDatabase.getInstance().reference.child("Sellers")

            val sellermap = java.util.HashMap<String, Any>()

            sellermap["shopphone"] = phone
            sellermap["shopname"] = name
            sellermap["shopaddress"] = address
            sellermap["shoptype"] = type
                    rootref.child(sid).updateChildren(sellermap)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(applicationContext, "Seller Information Updated Successful", Toast.LENGTH_SHORT).show()
                                    sellerdialog.dismiss()

                                    val intent = Intent(this, AdminCategoryActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK + Intent.FLAG_ACTIVITY_NEW_TASK)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    task.exception?.let { regisexception ->
                                        throw regisexception

                                    }
                                }
                            }


                }
            }
        }
    }






