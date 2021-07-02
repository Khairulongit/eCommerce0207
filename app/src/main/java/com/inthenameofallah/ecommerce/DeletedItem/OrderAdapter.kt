//package com.inthenameofallah.ecommerce.Adapters
//
//import android.content.Context
//import android.content.Intent
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.TextView
//import androidx.annotation.NonNull
//import androidx.recyclerview.widget.RecyclerView
//import com.inthenameofallah.ecommerce.Admin.AdminUserProductActivity
//import com.inthenameofallah.ecommerce.Model.OrderModel
//import com.inthenameofallah.ecommerce.R
//
//class OrderAdapter (private val mContext: Context,
//                                         private val mProduct:List<OrderModel>) : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {
//
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//
//        val view = LayoutInflater.from(mContext).inflate(R.layout.orders_layout, parent, false)
//        return ViewHolder(view)
//
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//
//
//        val product = mProduct[position]
//
//
//        holder.username.text = "Name :  ${product.productname}"
//        holder.userphonenumber.text = "Mobile :  ${product.phone}"
//        holder.usertotalprice.text = "Total Amount = ${product.totalamount}Rs"
//        holder.userdatetime.text = "Order At ${product.time} on ${product.date}"
//        holder.usershippingaddress.text = "Shipping Address = ${product.address}"
//
//        holder.showOrdersBtn.setOnClickListener{
//            val intent = Intent(mContext, AdminUserProductActivity::class.java)
//
//
////            Log.wtf("OrderAdapter","${product.useridphone}")
//
////            var uid:String=getItemId(position).getk
//
//            // time 13:19 check
//            // https://www.youtube.com/watch?v=syUkxbK_veo&list=PLxefhmF0pcPlqmH_VfWneUjfuqhreUz-O&index=31
//            intent.putExtra("uid", product.userid)
//            mContext.startActivity(intent)
//
//        }
//
////        holder.itemView.setOnClickListener {
////            val intent = Intent(mContext, ProductDetailsActivity::class.java)
////            intent.putExtra("pid", product.postid)
////            mContext.startActivity(intent)
////
////
////        }
//
//
////        publisherinfo(holder.productImage,holder.productname,holder.productdesc,holder.productprice)
//
//
//    }
//
//
//    override fun getItemCount(): Int {
//
//        return mProduct.size
//    }
//
//
//    inner class ViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
//        var username: TextView
//        var userphonenumber: TextView
//        var usertotalprice: TextView
//        var userdatetime: TextView
//        var usershippingaddress: TextView
//        var showOrdersBtn: Button
//
//
//        init {
//            username = itemView.findViewById(R.id.order_user_name)
//            userphonenumber = itemView.findViewById(R.id.order_phone_number)
//            usertotalprice = itemView.findViewById(R.id.order_total_price)
//            userdatetime = itemView.findViewById(R.id.order_date_time)
//            usershippingaddress = itemView.findViewById(R.id.order_address)
//            showOrdersBtn = itemView.findViewById(R.id.order_show_products)
//
//        }
//    }
//
//
//}
//
//
