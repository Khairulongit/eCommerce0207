package com.inthenameofallah.ecommerce.Buyers

import android.app.ProgressDialog
import android.content.Context
//import androidx.activity.result.ActivityResultLauncher
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
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
import com.inthenameofallah.ecommerce.NetworkConnection
import com.inthenameofallah.ecommerce.R
import com.inthenameofallah.ecommerce.databinding.ActivitySettingsBinding
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import de.hdodenhof.circleimageview.CircleImageView
import io.paperdb.Paper

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
//    private lateinit var firebaseUser: FirebaseUser
    private lateinit var cropActivityResultLauncher: ActivityResultLauncher<Any?>
    private var checker=""
    private var myUri=""
    private val cropActivityResultContract = object : ActivityResultContract<Any?, Uri?>(){
        override fun createIntent(context: Context, input: Any?): Intent {
            return CropImage.activity()
                    .setAspectRatio(16, 9)
                    .getIntent(this@SettingsActivity)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {

            return CropImage.getActivityResult(intent)?.uri
        }
    }
    private var storimgref: StorageReference?=null
    private var imgUri: Uri?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, Observer { isConnected->
            if (isConnected){
                binding.internetoff.visibility= View.GONE
                binding.interneton.visibility= View.VISIBLE

            }else{
                binding.internetoff.visibility= View.VISIBLE
                binding.interneton.visibility= View.GONE
            }

        })


        // Toolbar Logic below

        val toolbar = findViewById<Toolbar>(R.id.profile_toolbar)
        setSupportActionBar(toolbar)

        val textView = findViewById<TextView>(R.id.toolbartext)

//        textView.text="Your Current Orders!!"
//        textView.gravity= Gravity.CENTER_HORIZONTAL



        supportActionBar!!.setDisplayShowTitleEnabled(false)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = resources.getColor(R.color.status, this.theme)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.status)
        }


        //Toolbar Logic Above




        storimgref= FirebaseStorage.getInstance().reference.child("Profile Pictures")

        userInfodisplay(binding.profilePic,binding.settingsName,binding.settingsContact,binding.settingsAddress,binding.settingsPincode)




        binding.logoutBtnAcccountSettings.setOnClickListener {
            Paper.book().destroy()
            FirebaseAuth.getInstance().signOut()
            val intent= Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }



        cropActivityResultLauncher = registerForActivityResult(cropActivityResultContract){
            it?.let { uri ->

                binding.profilePic.setImageURI(uri)
                imgUri=uri
            }
        }

        binding.changeImageText.setOnClickListener(View.OnClickListener {
            checker = "clicked"
//            val builder = VmPolicy.Builder()
//            StrictMode.setVmPolicy(builder.build())
            cropActivityResultLauncher.launch(null)

//            Log.wtf("bef", "Before Crash")

        })

        binding.closeProfileBtn.setOnClickListener {

            val intent = Intent(this, HomeActivityNew::class.java)
            startActivity(intent)
            finish()
        }


        binding.saveInfoProfileBtn.setOnClickListener {

            if(checker=="clicked"){
                uploadimgandUpdateInfo()
            }
            else
            {
                updateuserInfoonly()
            }
        }

//        userInfo()
    }

    private fun userInfodisplay(profilePic: CircleImageView, fullNameProfileFrag: EditText, phoneProfileFrag: EditText, addressProfileFrag: EditText, settingsPincode: EditText) {


//        val userRef = FirebaseDatabase.getInstance().getReference().child("Usersecommerce").child(Prevalent.users.phone)
        val userRef = FirebaseDatabase.getInstance().getReference().child("Buyers").child(FirebaseAuth.getInstance().currentUser.uid)


        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    if (snapshot.child("photourl").exists())
                    {
                        var image:String=snapshot.child("photourl").value.toString()
                        var name:String=snapshot.child("buyername").value.toString()
                        var address:String=snapshot.child("buyeraddress").value.toString()
                        var phone:String=snapshot.child("buyercontact").value.toString()
                        var pincode:String=snapshot.child("buyerpincode").value.toString()


                        Picasso.get().load(image).into(profilePic)
                        fullNameProfileFrag.setText(name)
                        phoneProfileFrag.setText(phone)
                        addressProfileFrag.setText(address)
                        settingsPincode.setText(pincode)
                    }
//                    val user = snapshot.getValue<Model>(Model::class.java)
////                    Picasso.get().load(user!!.getimage()).placeholder(R.drawable.profile).into(
//                    Picasso.get().load(user!!.getimage()).placeholder(R.drawable.profile).into(
//                            binding.profilePic
//                    )
//                    binding.fullNameProfileFrag.setText(user.getFullname())
//                    binding.usernameProfileFrag.setText(user.getUsername())
//                    binding.bioProfileFrag.setText(user.getBio())
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }




    private fun updateuserInfoonly() {

        when {
            TextUtils.isEmpty(binding.settingsContact.text) -> {
                Toast.makeText(applicationContext, "Please write Phone Number", Toast.LENGTH_SHORT).show()
                return
            }

            TextUtils.isEmpty(binding.settingsPincode.text) -> {
                Toast.makeText(applicationContext, "Please write Valid PIN", Toast.LENGTH_SHORT).show()
                return
            }
            binding.settingsName.text.toString()=="" -> {
                Toast.makeText(
                        applicationContext,
                        "Please write Full User Name",
                        Toast.LENGTH_SHORT
                ).show()
                return
            }
            binding.settingsAddress.text.toString()=="" -> {
                Toast.makeText(applicationContext, "Please write valid Address", Toast.LENGTH_SHORT).show()
                return
            }
            else -> {
                val userRef = FirebaseDatabase.getInstance().getReference().child("Buyers")
                val userMap = HashMap<String, Any>()
                userMap["buyername"] = binding.settingsName.text.toString()
                userMap["buyercontact"] = binding.settingsContact.text.toString()
                userMap["buyeraddress"] = binding.settingsAddress.text.toString()
                userMap["buyerpincode"] = binding.settingsPincode.text.toString()


//                userRef.child(Prevalent.users.phone).updateChildren(userMap)
                userRef.child(FirebaseAuth.getInstance().currentUser.uid).updateChildren(userMap)

                Toast.makeText(
                        applicationContext,
                        "Account Information Updated Successfully",
                        Toast.LENGTH_SHORT
                ).show()


                val intent = Intent(this, HomeActivityNew::class.java)
                startActivity(intent)
                finish()


            }
        }


    }

//    private fun userInfo(){
//        val userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(
//                firebaseUser.uid
//        )
//        userRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//
////                if(context!=null){
////
////                    return
////
////                }
//                if (snapshot.exists()) {
//                    val user = snapshot.getValue<Model>(Model::class.java)
////                    Picasso.get().load(user!!.getimage()).placeholder(R.drawable.profile).into(
//                    Picasso.get().load(user!!.getimage()).placeholder(R.drawable.profile).into(
//                            binding.profilePic
//                    )
//                    binding.fullNameProfileFrag.setText(user.getFullname())
//                    binding.usernameProfileFrag.setText(user.getUsername())
//                    binding.bioProfileFrag.setText(user.getBio())
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//            }
//        })
//    }

    private fun uploadimgandUpdateInfo() {


        when {

            imgUri==null -> Toast.makeText(this, "Please Select an Image", Toast.LENGTH_SHORT).show()

            TextUtils.isEmpty(binding.settingsContact.text) -> {
                Toast.makeText(applicationContext, "Please write  valid Phone", Toast.LENGTH_SHORT)
                        .show()
                return
            }
            binding.settingsName.text.toString() == "" -> {
                Toast.makeText(
                        applicationContext,
                        "Please write Full User Name",
                        Toast.LENGTH_SHORT
                ).show()
                return
            }
            binding.settingsAddress.text.toString() == "" -> {
                Toast.makeText(applicationContext, "Please write valid Address", Toast.LENGTH_SHORT)
                        .show()
                return
            }

            binding.settingsPincode.text.toString() == "" -> {
                Toast.makeText(applicationContext, "Please write valid PIN", Toast.LENGTH_SHORT)
                        .show()
                return
            }


            else ->{

                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Account Settings")
                progressDialog.setMessage("Please wait ,we are updating your profile..")
                progressDialog.show()

//                val fileRef = storimgref!!.child(Prevalent.users.phone + ".jpg")  // creating the file first
                val fileRef = storimgref!!.child(FirebaseAuth.getInstance().currentUser.uid + ".jpg")  // creating the file first

                var uploadtask : StorageTask<*>
                uploadtask=fileRef.putFile(imgUri!!)  // storing it in the storage

                uploadtask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                    if (!task.isSuccessful) {

                        task.exception?.let {
                            throw it
                            progressDialog.dismiss()
                        }

                    }
                    return@Continuation fileRef.downloadUrl

                }).addOnCompleteListener(OnCompleteListener<Uri> { task ->

                    if (task.isSuccessful) {   // if upload task is suucessful
                        val downlodurl = task.result
                        myUri = downlodurl.toString()
                        val ref = FirebaseDatabase.getInstance().reference.child("Buyers")
                        val userMap = HashMap<String, Any>()
                        userMap["buyername"] =

                                binding.settingsName.text.toString()
                        userMap["buyeraddress"] =
                                binding.settingsAddress.text.toString()

                        userMap["buyerpincode"] =
                                binding.settingsPincode.text.toString()

                        userMap["buyercontact"] = binding.settingsContact.text.toString()
                        userMap["photourl"] = myUri

//                        ref.child(Prevalent.users.phone).updateChildren(userMap)
                        ref.child(FirebaseAuth.getInstance().currentUser.uid).updateChildren(userMap)
                        Toast.makeText(
                                applicationContext,
                                "Account Information Updated Successfully",
                                Toast.LENGTH_SHORT
                        ).show()


                        val intent = Intent(this, HomeActivityNew::class.java)
                        startActivity(intent)
                        finish()
                        progressDialog.dismiss()


                    } else {
                        progressDialog.dismiss()
                    }


                })


            }




        }
    }
}


