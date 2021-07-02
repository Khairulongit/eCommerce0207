//package com.inthenameofallah.ecommerce.Buyers
//
//import android.app.ProgressDialog
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.text.TextUtils
//import android.util.Log
//import android.widget.Toast
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.*
//import com.inthenameofallah.ecommerce.Model.Users
//import com.inthenameofallah.ecommerce.Prevalent.Prevalent
//import com.inthenameofallah.ecommerce.Seller.SellerHomeActivity
//import com.inthenameofallah.ecommerce.Seller.SellerRegistrationActivity
//import com.inthenameofallah.ecommerce.databinding.ActivityMainBinding
//import io.paperdb.Paper
//
//class MainActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityMainBinding
//    var UserPhonekey:String? =""
//    var UserPasswordKey:String? =""
//    var DatabaseDef:String ="Usersecommerce"
//    val mAuth= FirebaseAuth.getInstance()
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding= ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.mainLoginBtn.setOnClickListener{ startActivity(Intent(this, LoginActivity::class.java)) }
//
//        binding.mainJoinNowBtn.setOnClickListener{ startActivity(Intent(this, RegisterActivity::class.java)) }
//
//        binding.textView.setOnClickListener{ startActivity(Intent(this, SellerRegistrationActivity::class.java)) }
//
//        Paper.init(this)
////        UserPhonekey = Paper.book().read(Prevalent.UserPhoneKey)
////        UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey)
////
////        if(UserPhonekey!=""  && UserPasswordKey!=""){
////
////            if(!TextUtils.isEmpty(UserPasswordKey)  && !TextUtils.isEmpty(UserPhonekey)){
////                Allowaccess(UserPhonekey,UserPasswordKey)
////            }
////        }
//
//
//
//
//    }
//
//
//    override fun onStart() {
//        super.onStart()
//
//
//        if (mAuth.currentUser!=null) {
//
//
//            val intent = Intent(this, SellerHomeActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK + Intent.FLAG_ACTIVITY_NEW_TASK)
//            startActivity(intent)
//            finish()
//        }
//    }
//
////    private fun Allowaccess(phone: String?, password: String?) {
////
////      val rootref: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Usersecommerce").child(phone!!)
//////        val rootref: DatabaseReference = FirebaseDatabase.getInstance().reference.child("EcomAdmin").child(phone!!)
////        val progressdialoglogin = ProgressDialog(this)
////
////        progressdialoglogin.setTitle("Already Logged In")
////        progressdialoglogin.setMessage("Please wait,while we are checking the credentials..")
////        progressdialoglogin.setCanceledOnTouchOutside(false)
////        progressdialoglogin.show()
////
////
////        Log.wtf("cred","$UserPhonekey and $UserPasswordKey")
////
////
////        rootref.addValueEventListener(object : ValueEventListener {
////            override fun onDataChange(snapshot: DataSnapshot) {
////
////                if (snapshot.exists()) {
////
////                    val userdata = snapshot.getValue<Users>(Users::class.java)
////
////
//////                    Log.wtf("cred 2","${userdata?.phone} and ${userdata?.password}")
////
////
////
//////                    if (userdata?.getPhone().equals(phone)) {
//////                    if (userdata?.phone.equals(phone)) {
////                    var test="KHAI"
////                    var password="KHAI"
////                    if (test=="islam") {
////
////
//////                        if (userdata?.getPassword().equals(password)) {
//////                        if (userdata?.password.equals(password)) {
////                        if (password=="hsdfdsfsd") {
////                            progressdialoglogin.dismiss()
////                            Toast.makeText(applicationContext, "Login Successful", Toast.LENGTH_SHORT).show()
//////                            val intent = Intent(this@MainActivity, HomeActivityNew::class.java)
////                            val intent = Intent(this@MainActivity, HomeActivityNew::class.java)
////                            if (userdata != null) {
////                                Prevalent.users=userdata
////                            }
////                            startActivity(intent)
////
////
////                        }
////
////                        else{
////                            progressdialoglogin.dismiss()
////                            Toast.makeText(applicationContext, "Incorrect Password", Toast.LENGTH_SHORT).show()
////
////
////                        }
////                    }
////
////
////                } else {
////                    Toast.makeText(applicationContext, "Account with this $phone does not exist", Toast.LENGTH_SHORT).show()
////                    progressdialoglogin.dismiss()
////                }
////
////
////            }
////
////            override fun onCancelled(error: DatabaseError) {
////            }
////
////        })
////
////    }
//}