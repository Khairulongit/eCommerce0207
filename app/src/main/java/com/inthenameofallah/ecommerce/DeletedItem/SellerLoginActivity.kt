//package com.inthenameofallah.ecommerce.Seller
//
//import android.app.ProgressDialog
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.text.TextUtils
//import android.util.Log
//import android.widget.Toast
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.FirebaseDatabase
//import com.inthenameofallah.ecommerce.Model.Users
//import com.inthenameofallah.ecommerce.Prevalent.Prevalent
//import com.inthenameofallah.ecommerce.R
//import com.inthenameofallah.ecommerce.databinding.ActivitySellerLoginBinding
//import com.inthenameofallah.ecommerce.databinding.ActivitySellerRegistrationBinding
//
//class SellerLoginActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivitySellerLoginBinding
//    val mAuth= FirebaseAuth.getInstance()
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivitySellerLoginBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//
//
//
//        binding.loginSeller.setOnClickListener{
//            loginseller()
//        }
//    }
//
//    private fun loginseller() {
//
//        val email=binding.sellerEmailLogin.text.toString()
//        val password=binding.sellerPasswordLog.text.toString()
//
//        when {
//
//            TextUtils.isEmpty(email) -> Toast.makeText(this, "Email is Empty", Toast.LENGTH_SHORT)
//            TextUtils.isEmpty(password) -> Toast.makeText(this, "Password is Empty", Toast.LENGTH_SHORT)
//
//            else -> {
//
//                val sellerdialog = ProgressDialog(this)
//                sellerdialog.setTitle("Seller Account Login")
//                sellerdialog.setMessage("Please wait,while we are checking the credentials..")
//                sellerdialog.setCanceledOnTouchOutside(false)
//                sellerdialog.show()
//
//                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
//
//
//                    if (it.isSuccessful) {
//
//
////                        var user = Users()
////                        user.password="123456"
////                        user.username="testing"
////                        user.phone="123456"
////                        user.image="123456"
////                        user.address="Panduya"
//
//
////                        Prevalent.users=user
//
//
//                        Prevalent.UserType="Seller"
//
//                        sellerdialog.dismiss()
//                        val intent = Intent(this, SellerHomeActivity::class.java)
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK + Intent.FLAG_ACTIVITY_NEW_TASK)
//                        startActivity(intent)
//                        finish()
//                    } else {
//
//
//                        sellerdialog.dismiss()
//
//                        it.exception?.let { regisexception ->
//                            throw regisexception
//
//                        }
//                                }
//                            }
//
//
//                    }
//                }
//            }
//        }
