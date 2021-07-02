//package com.inthenameofallah.ecommerce.Buyers
//
//import android.app.ProgressDialog
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.text.TextUtils
//import android.widget.Toast
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.*
//import com.inthenameofallah.ecommerce.databinding.ActivityRegisterBinding
//
//class RegisterActivity : AppCompatActivity() {
//
//
//    private lateinit var binding: ActivityRegisterBinding
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding= ActivityRegisterBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//
//
//        binding.registerBtn.setOnClickListener {
//            Creataaccount()
//        }
//    }
//
//    private fun Creataaccount() {
//        val username = binding.registerUserNameInput.text.toString()
//        val phone = binding.registerPhoneNumberInput.text.toString()
//        val password = binding.registerPasswordInput.text.toString()
//
//        when {
//            TextUtils.isEmpty(username) -> Toast.makeText(this, "User name Login is Empty", Toast.LENGTH_SHORT)
//            TextUtils.isEmpty(phone) -> Toast.makeText(this, "Phone is Empty", Toast.LENGTH_SHORT)
//            TextUtils.isEmpty(password) -> Toast.makeText(this, "Password is Empty", Toast.LENGTH_SHORT)
//
//            else -> {
//                val progressdialog = ProgressDialog(this)
//                progressdialog.setTitle("Create Account")
//                progressdialog.setMessage("Please wait,while we are checking the credentials..")
//                progressdialog.setCanceledOnTouchOutside(false)
//                progressdialog.show()
//
//
//                validatephonenumber(username,phone,password,progressdialog)
//
////                val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
////                mAuth.signInWithEmailAndPassword(emaillogin, passwordlogin).addOnCompleteListener { task ->
////                    if (task.isSuccessful) {
////                        progressdialog.dismiss()
////                        val intent= Intent(this,MainActivity::class.java)
////                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or  Intent.FLAG_ACTIVITY_NEW_TASK)
////                        startActivity(intent)
////                        finish()
////                    }
////                    else{
////
////                        val message=task.exception.toString()
////                        Toast.makeText(this, "Error Logging In $message", Toast.LENGTH_SHORT).show()
////                        FirebaseAuth.getInstance().signOut()
////                        progressdialog.dismiss()
////
////                    }
//                }
//
//
//            }
//        }
//
//
//
//    private fun validatephonenumber(username: String, phone: String, password: String,progressdialog:ProgressDialog) {
//
//        val rootref: DatabaseReference = FirebaseDatabase.getInstance().reference
//
//        rootref.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//                if (!(snapshot.child("Usersecommerce").child(phone).exists())) {
//
//                    val userMap = HashMap<String, Any>()
//                    userMap["phone"] = phone
//                    userMap["password"] = password
//                    userMap["username"] = username.toLowerCase()
//
//
//                    rootref.child("Usersecommerce").child(phone).setValue(userMap)
//                            .addOnCompleteListener {
//                                if (it.isSuccessful) {
//
//                                    progressdialog.dismiss()
//                                    Toast.makeText(applicationContext, "Account has been created successfully", Toast.LENGTH_SHORT).show()
//
//                                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
//                                    startActivity(intent)
//
//
//                                }
//
//                                else{
//
//                                    progressdialog.dismiss()
//                                    Toast.makeText(applicationContext, "Network Error", Toast.LENGTH_SHORT).show()
//
//
//                                }
//                            }
//
//
//                } else {
//                    Toast.makeText(applicationContext, "This $phone already exoist", Toast.LENGTH_SHORT).show()
//                    progressdialog.dismiss()
//
//                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
//                    startActivity(intent)
//
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//            }
//
//        }
//
//
//        )
//
//
//
//
//
//
//    }
//}