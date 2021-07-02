package com.inthenameofallah.ecommerce.Seller

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.inthenameofallah.ecommerce.Admin.AdminCategoryActivity
import com.inthenameofallah.ecommerce.NetworkConnection
import com.inthenameofallah.ecommerce.R
import com.inthenameofallah.ecommerce.databinding.ActivityAdminAddNewProductBinding
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*
import androidx.lifecycle.Observer


class SellerAddNewProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminAddNewProductBinding
    private var categoryName: String = ""

    private var Randomkey: String = ""
    private var myUri=""
    val mAuth= FirebaseAuth.getInstance()
    private var Savecurrentdate: String = ""
    private var Savecurrenttime: String = ""
    val GalleryPick: Int = 1
    var sellername:String=""
    var selleremail:String=""
    var selleraddress : String=""
    var sellerphone:String=""
    var sellerid:String=""
    var sellertype:String=""

    val addrregex = "^([a-zA-Z\u0080-\u024F]+(?:. |-| |'))*[a-zA-Z\u0080-\u024F]*$".toRegex()
    val nameregex = "[a-zA-Z][a-zA-Z ]*".toRegex()



    private var storeprodref: StorageReference? = null
    private var imgUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminAddNewProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, Observer { isConnected->
            if (isConnected){
                binding.nointenetlayout.visibility= View.GONE
                binding.interneton.visibility= View.VISIBLE

            }else{
                binding.nointenetlayout.visibility= View.VISIBLE
                binding.interneton.visibility= View.GONE
            }

        })

        // Toolbar Logic below

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        var textView = findViewById<TextView>(R.id.toolbartext)

        textView.text="Add Product Info"
        textView.foregroundGravity= Gravity.CENTER_HORIZONTAL
        textView.textSize= 20F

        supportActionBar!!.setDisplayShowTitleEnabled(false)
        val ab = supportActionBar
//        ab!!.setDisplayHomeAsUpEnabled(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = resources.getColor(R.color.status, this.theme)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.status)
        }


        //Toolbar Logic Above





        categoryName = intent.extras!!.get("category").toString()

        Toast.makeText(applicationContext, "category name is $categoryName", Toast.LENGTH_SHORT).show()


        binding.selectProductImage.setOnClickListener {

            opengallery()
        }

        binding.addNewProduct.setOnClickListener {
            ValidateProductdata()
        }

        sellerinformation()



    }

    private fun ValidateProductdata() {

        when {
            imgUri == null -> Toast.makeText(this, "Please Select an Image", Toast.LENGTH_SHORT).show()

            TextUtils.isEmpty(binding.produtcDesc.text) ->Toast.makeText(applicationContext, "Please write Something ...", Toast.LENGTH_SHORT)
                        .show()


            TextUtils.isEmpty(binding.produtcPrice.text) ->Toast.makeText(applicationContext, "Please write Price ...", Toast.LENGTH_SHORT)
                        .show()

            TextUtils.isEmpty(binding.productName.text) ->Toast.makeText(applicationContext, "Please write Product Name ...", Toast.LENGTH_SHORT)
                    .show()


        else-> {

            StoreprodcutInformation()

        }

        }
    }

    private fun StoreprodcutInformation() {

        val progressdialoglogin = ProgressDialog(this)

        progressdialoglogin.setTitle("Adding New Product")
        progressdialoglogin.setMessage("Please wait,while we are adding the product..")
        progressdialoglogin.setCanceledOnTouchOutside(false)
        progressdialoglogin.show()

        var calendar :Calendar= Calendar.getInstance()

        var currentdate=SimpleDateFormat("MMM dd,yyyy")
        Savecurrentdate = currentdate.format(calendar.time)

        var currenttime=SimpleDateFormat("HH:mm:ss a")
        Savecurrenttime = currenttime.format(calendar.time)

        Randomkey=Savecurrentdate+Savecurrenttime


        storeprodref= FirebaseStorage.getInstance().reference.child("Products Images").child(FirebaseAuth.getInstance().currentUser.toString())

        val fileRef = storeprodref!!.child(System.currentTimeMillis().toString() + ".jpg")  // creating the file first

        var uploadtask : StorageTask<*>
        uploadtask=fileRef.putFile(imgUri!!)  // storing it in the storage


        uploadtask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
            if (!task.isSuccessful) {

                task.exception?.let {
                    throw it
                    progressdialoglogin.dismiss()
                }

            }
            return@Continuation fileRef.downloadUrl

        }).addOnCompleteListener(OnCompleteListener<Uri> { task ->

            if (task.isSuccessful) {   // if upload task is suucessful


                val downlodurl = task.result
                myUri = downlodurl.toString()
                val productref = FirebaseDatabase.getInstance().reference.child("Products").child(FirebaseAuth.getInstance().currentUser.uid)



                val productId = productref.push().key // will create random number
                val newprodcutpmap = HashMap<String, Any>()


                newprodcutpmap["productid"] = productId!!
                newprodcutpmap["date"] = Savecurrentdate
                newprodcutpmap["time"] = Savecurrenttime
                newprodcutpmap["description"] = binding.produtcDesc.text.toString().toLowerCase()
                newprodcutpmap["productimg"] = myUri
                newprodcutpmap["category"] = categoryName
                newprodcutpmap["price"] = binding.produtcPrice.text.toString()
                newprodcutpmap["productname"] = binding.productName.text.toString()
                newprodcutpmap["sellername"] = sellername
                newprodcutpmap["selleraddress"] = selleraddress
                newprodcutpmap["sellerphone"] = sellerphone
                newprodcutpmap["selleremail"] = selleremail
                newprodcutpmap["sellerid"] = sellerid


                newprodcutpmap["productstatus"] = "pending"

                productref.child(productId).updateChildren(newprodcutpmap).addOnCompleteListener {

                    if(task.isSuccessful){
                        progressdialoglogin.dismiss()
                        val intent = Intent(applicationContext, AdminCategoryActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(applicationContext, "Product Added Successfully KI", Toast.LENGTH_SHORT).show()
                }else {

                         task.exception?.let {
                            throw it
                             progressdialoglogin.dismiss()


                         }
                    }


            }} else {
                 task.exception?.let {
                    throw it
                }
            }





    })
    }

    private fun sellerinformation() {




        val sellerref = FirebaseDatabase.getInstance().reference.child("Sellers").child(mAuth.currentUser.uid)


        sellerref.addValueEventListener( object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){

                    selleraddress=snapshot.child("shopaddress").value.toString()
                    sellername=snapshot.child("shopname").value.toString()
                    selleremail=snapshot.child("email").value.toString()
                    sellertype = snapshot.child("shoptype").value.toString()
                    sellerid=snapshot.child("userid").value.toString()
                    sellerphone=snapshot.child("shopphone").value.toString()



                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

    }


    private fun opengallery() {

        val galleryintent = Intent(Intent.ACTION_GET_CONTENT)
        galleryintent.setType("image/*")
        startActivityForResult(galleryintent,GalleryPick)


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode==GalleryPick && resultCode == RESULT_OK && data!=null)
        {
            imgUri = data.data  // this is the local path

//            binding.selectProductImage.setImageURI(imgUri)
            Picasso.get().load(imgUri).fit().centerCrop().placeholder(R.drawable.profile).into(binding.selectProductImage)
//            Picasso.get().load(imgUri).resize(600,200).centerInside().placeholder(R.drawable.profile).into(binding.selectProductImage)


        }


    }
}