package com.inthenameofallah.ecommerce.Buyers

import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.inthenameofallah.ecommerce.Model.Users
import com.inthenameofallah.ecommerce.NetworkConnection
import com.inthenameofallah.ecommerce.Prevalent.Prevalent
import com.inthenameofallah.ecommerce.R
import com.inthenameofallah.ecommerce.Seller.SellerRegistrationActivity
import com.inthenameofallah.ecommerce.databinding.ActivityLoginBinding
import io.paperdb.Paper
import kotlin.system.exitProcess


class LoginActivity : AppCompatActivity() {

    val mAuthBuyers= FirebaseAuth.getInstance()
    var mGoogleSignInClient: GoogleSignInClient? = null
    var database = FirebaseDatabase.getInstance()
    var progressDialog: ProgressDialog? = null


    private lateinit var binding: ActivityLoginBinding
    var usertype: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, Observer { isConnected->
            if (isConnected){
                binding.layoutcoonected1.visibility=View.VISIBLE
                binding.layoutnotcoonected1.visibility=View.GONE
            }else{
                binding.layoutcoonected1.visibility=View.GONE
                binding.layoutnotcoonected1.visibility=View.VISIBLE

            }



        })

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = resources.getColor(R.color.status, this.theme)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.status)
        }


        usertype=intent.getStringExtra("category").toString()

        progressDialog = ProgressDialog(this)



        binding.btnSeller.setOnClickListener {
            usertype="Seller"
            loginwithgooogle()



        }

        binding.btnAdmin.setOnClickListener {
            usertype="Admin"
            Toast.makeText(applicationContext, "Sorry This Feature is Locked Now!!", Toast.LENGTH_SHORT).show()
            return@setOnClickListener
//            loginwithgooogle()

        }

        binding.btnGoogle.setOnClickListener {
            usertype="Buyer"
            loginwithgooogle()

        }

        Paper.init(this)
    }

    private fun loginwithgooogle() {

//        showProgressBar()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        progressDialog!!.setTitle("Login")
        progressDialog!!.setMessage("Login to your Google account")
        progressDialog!!.setCanceledOnTouchOutside(false)



        progressDialog?.show()
//        progressDialog?.setContentView(R.layout.progress_dialog)
//        progressDialog?.window?.setBackgroundDrawableResource(
//                android.R.color.transparent
//        )

//        progressDialog!!.setTitle("Login")
//        progressDialog!!.setMessage("Login to your Google account")
        signIn()

    }

    var RC_SIGN_IN = 65
    private fun signIn() {
        val signInIntent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
//        progressDialog!!.show()


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                Log.d("TAG", "firebaseAuthWithGoogle:" + account!!.id)
                account.idToken?.let { firebaseAuthWithGoogle(it) }
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e)
                // ...
            }
        }
    }


    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuthBuyers.signInWithCredential(credential)
            .addOnCompleteListener(this,
                    OnCompleteListener<AuthResult?> { task ->
                        if (task.isSuccessful) {


                            val user: FirebaseUser = mAuthBuyers.currentUser


                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success")

//                            hideProgressBar()


                            val shopname = database.reference.child("Sellers").child(user.uid).child("shopname")


                            val userModel = Users()
                            userModel.email = user.email
                            userModel.userid = user.uid
                            Prevalent.Displayname = user.displayName
                            userModel.displayname = user.displayName
                            Prevalent.Photourl = user.photoUrl.toString()
                            userModel.photourl = user.photoUrl.toString()
                            //                            database.getReference().child("Patient").child(user.getUid()).child("Userdetails").setValue(userModel);


//                            progressDialog?.dismiss()
                            if (usertype == "Buyer") {
                                Prevalent.UserType = "Buyer"

                                database.reference.child("Buyers").child(user.uid).addValueEventListener(object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {

                                        if (snapshot.child("buyercontact").exists()) {

                                            val intent = Intent(applicationContext, HomeActivityNew::class.java)
                                            startActivity(intent)
                                            finish()


                                        } else {

                                            database.reference.child("Buyers").child(user.uid)
                                                    .setValue(userModel)
                                            val intent = Intent(applicationContext, BuyerRegistration::class.java)
                                            startActivity(intent)
                                            finish()


                                        }
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                    }


                                })

                                progressDialog?.dismiss()


                            } else if (usertype == "Seller") {
                                Prevalent.UserType = "Seller"
                                database.reference.child("Sellers").child(user.uid).addValueEventListener(object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {

                                        if (snapshot.child("shopaddress").exists()) {

                                            val intent = Intent(applicationContext, HomeActivityNew::class.java)
                                            startActivity(intent)
                                            finish()

                                        } else {

                                            database.reference.child("Sellers").child(user.uid)
                                                    .setValue(userModel)
                                            val intent = Intent(applicationContext, SellerRegistrationActivity::class.java)
                                            startActivity(intent)
                                            finish()

                                        }
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                    }


                                })

                                progressDialog?.dismiss()


                            } else {
                                Prevalent.UserType = "Admin"

                                database.reference.child("Admin Ecom").child(user.uid)
                                        .setValue(userModel)
                                val intent = Intent(applicationContext, HomeActivityNew::class.java)
                                startActivity(intent)
                                Toast.makeText(
                                        this,
                                        "Google Sign In Successful ADMIN",
                                        Toast.LENGTH_SHORT
                                ).show()


                            }

                            progressDialog?.dismiss()


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.exception)
                            progressDialog?.dismiss()
//                            hideProgressBar()
                            Toast.makeText(
                                    this,
                                    "Login Failed" + task.exception!!.message,
                                    Toast.LENGTH_SHORT
                            ).show()
                        }
                        progressDialog?.dismiss()

                    })

//        hideProgressBar()

    }


//    private fun LoginUser() {
//        val Email = binding.emaillogin.text.toString()
//        val password = binding.pwdlogin.text.toString()
//
//        when {
//            TextUtils.isEmpty(Email) -> Toast.makeText(this, "Email is Empty", Toast.LENGTH_SHORT)
//            TextUtils.isEmpty(password) -> Toast.makeText(
//                    this,
//                    "Password is Empty",
//                    Toast.LENGTH_SHORT
//            )
//
//            else -> {
//                val buyersloginprogressdialog = ProgressDialog(this)
//                buyersloginprogressdialog.setTitle("Logging In")
//                buyersloginprogressdialog.setMessage("Please wait,while we are checking the credentials..")
//                buyersloginprogressdialog.setCanceledOnTouchOutside(false)
//                buyersloginprogressdialog.show()
//
//
//                mAuthBuyers.signInWithEmailAndPassword(Email, password).addOnCompleteListener {
//
//
//                    if (it.isSuccessful) {
//
//                        buyersloginprogressdialog.dismiss()
//                        Prevalent.UserType="Seller"
//
//                        val intent = Intent(this, AdminCategoryActivity::class.java)
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK + Intent.FLAG_ACTIVITY_NEW_TASK)
//                        startActivity(intent)
//                        finish()
//                    } else {
//
//
//                        buyersloginprogressdialog.dismiss()
//
//                        it.exception?.let { regisexception ->
//                            throw regisexception
//
//                        }
//                    }
//                }
//
//
//            }
//        }
//
//        }


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


    fun showProgressBar() {
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        binding.progressBar.visibility = View.VISIBLE
    }


    fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }



    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = FirebaseAuth.getInstance().currentUser
//        updateUI(currentUser)

        if (currentUser != null) {

            Prevalent.UserType="Seller"
            Prevalent.Displayname=currentUser.displayName
            Prevalent.Photourl= currentUser.photoUrl.toString()
            startActivity(Intent(applicationContext, HomeActivityNew::class.java))
            Log.wtf("prevalent",Prevalent.Displayname)
            Log.wtf("prevalent",Prevalent.Photourl)
        }



    }


}

