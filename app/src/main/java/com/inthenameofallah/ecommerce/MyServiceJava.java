//package com.inthenameofallah.ecommerce;
//
//import android.app.Service;
//import android.content.BroadcastReceiver;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.Handler;
//import android.os.IBinder;
//import android.os.SystemClock;
//import android.util.Log;
//
//import androidx.annotation.Nullable;
//
//public class MyServiceJava extends Service {
//
//    public static final String BroadcastAction="checktheinternet";
//
//
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        Log.wtf("inside","oncreate");
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        throw new UnsupportedOperationException("Not Yet implement");
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//
//        handler.post(perioidicupdate);
//        return START_STICKY;
//    }
//
//    public boolean isOnline(Context c){
//        ConnectivityManager connectivityManager = (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo ni = connectivityManager.getActiveNetworkInfo();
//
//        if (ni!=null && ni.isConnectedOrConnecting())
//            return true;
//        else
//            return false;
//    }
//
//    Handler handler=new Handler();
//    private Runnable perioidicupdate = new Runnable() {
//        @Override
//        public void run() {
//
//            handler.postDelayed(perioidicupdate,1*1000- SystemClock.elapsedRealtime()%1000);
//            Intent broadcastintent = new Intent();
//            broadcastintent.setAction(NewtorkDemo.BroadcastAction);
//            broadcastintent.putExtra("online_status",""+isOnline(MyServiceJava.this));
//            sendBroadcast(broadcastintent);
//        }
//    };
//
//
//    @Override
//    public ComponentName startService(Intent service) {
//        return super.startService(service);
//    }
//}
