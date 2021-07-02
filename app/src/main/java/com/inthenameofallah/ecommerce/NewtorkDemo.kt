package com.inthenameofallah.ecommerce

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.inthenameofallah.ecommerce.databinding.ActivityNewtorkDemoBinding

class NewtorkDemo : AppCompatActivity() {
    private lateinit var binding: ActivityNewtorkDemoBinding
    val BroadcastAction = "checktheinternet"
//    private var mIntentfilter: IntentFilter = TODO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewtorkDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, Observer { isConnected->
            if (isConnected){
                binding.layoutconnected.visibility=View.VISIBLE
                binding.layoutdisconnected.visibility=View.GONE
        }else{
                binding.layoutconnected.visibility=View.GONE
                binding.layoutdisconnected.visibility=View.VISIBLE

            }



        })

        //****************code inside onCreate to check is online starts******************************


//        mIntentfilter.addAction(BroadcastAction)
//        val serviceIntent = Intent(this, MyServiceJava::class.java)
//        startService(serviceIntent)
//
//        binding.notconnectedtext.visibility=View.GONE
//        if (isOnline(applicationContext)){
//
//            Set_Visibility_ON()
//
//        }else{
//            Set_Visibility_OFF()
//
//
//        }
//
//
//        //****************code to check is online Ends******************************
//
//
//    }

//    companion object {
//        lateinit var BroadcastAction: String
    }


}
//
//    //****************code to check is online starts******************************
//
//
//    var myReciever: BroadcastReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context, intent: Intent) {
//            if (intent.action == BroadcastAction){
//                if (intent.getStringExtra("online_status").equals("true"))
//                {
//                    Set_Visibility_ON()
//                }
//                else
//                {
//                    Set_Visibility_OFF()
//
//                }
//            }
//        }
//    }
//
//    fun isOnline(c: Context): Boolean {
//        val connectivityManager = c.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
//        val ni = connectivityManager.activeNetworkInfo
//        return ni != null && ni.isConnectedOrConnecting
//    }
//
//    fun Set_Visibility_ON(){
//
//        binding.notconnectedtext.visibility = View.GONE
//        binding.button.visibility = View.VISIBLE
//        binding.parent.setBackgroundColor(Color.GRAY)
//
//    }
//    //****************code to check is online ends ******************************
//
//    fun Set_Visibility_OFF(){
//
//        binding.notconnectedtext.visibility = View.VISIBLE
//        binding.button.visibility = View.GONE
//        binding.parent.setBackgroundColor(Color.RED)
//
//
//
//    }
//
//    override fun onRestart() {
//        super.onRestart()
//        registerReceiver(myReciever, mIntentfilter)
//    }
//
//
//    override fun onPause() {
//        super.onPause()
//        unregisterReceiver(myReciever)
//
//    }
//
//    override fun onResume() {
//        super.onResume()
//        registerReceiver(myReciever, mIntentfilter)
//
//
//    }
//}