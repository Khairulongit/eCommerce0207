//package com.inthenameofallah.ecommerce.Adapters
//
//import android.content.Context
//import android.content.Intent
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.*
//import androidx.annotation.NonNull
//import androidx.recyclerview.widget.RecyclerView
//import com.inthenameofallah.ecommerce.Model.ProductModel
//import com.inthenameofallah.ecommerce.Buyers.ProductDetailsActivity
//import com.inthenameofallah.ecommerce.R
//import com.squareup.picasso.Picasso
//import java.util.*
//import kotlin.collections.ArrayList
//
////class ProductAdapter(private val mContext: Context,
////                     private val mProduct: ArrayList<ProductModel>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>(),Filterable {
//class ProductAdapter1 : RecyclerView.Adapter<ProductAdapter.ViewHolder>(),Filterable {
//
////    private var firebaseuser:FirebaseUser?=null
//
//    lateinit var list: java.util.ArrayList<ProductModel>
//    lateinit var mContext: Context
//    lateinit var backup: java.util.ArrayList<ProductModel>
//
//
//    constructor(){
//
//    }
//
//    constructor(list: java.util.ArrayList<ProductModel>?, mContext: Context?, backup: java.util.ArrayList<ProductModel>?) : super() {
//        if (list != null) {
//            this.list = list
//        }
//        if (mContext != null) {
//            this.mContext = mContext
//        }
//        if (backup != null) {
//            this.backup = backup
//        }
//    }
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//
//        val view = LayoutInflater.from(mContext).inflate(R.layout.product_iitems_layout, parent, false)
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
//        holder.productdesc.text = product.description
//        holder.productname.text = product.productname
//        holder.productprice.text = "Price = ${product.price}Rs"
//        Picasso.get().load(product.productimg).into(holder.productImage)
//
//        Log.wtf("pid", "${product.postid}")
//
//        holder.itemView.setOnClickListener {
//            val intent = Intent(mContext, ProductDetailsActivity::class.java)
//            intent.putExtra("pid", product.postid)
//            mContext?.startActivity(intent)
//
//
//        }
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
//        return list.size
//    }
//
//
//    inner class ViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
//        var productImage: ImageView
//        var productname: TextView
//        var productprice: TextView
//        var productdesc: TextView
//
//
//        init {
//            productImage = itemView.findViewById(R.id.product_image)
//            productname = itemView.findViewById(R.id.product_name1)
//            productdesc = itemView.findViewById(R.id.product_desc1)
//            productprice = itemView.findViewById(R.id.product_price1)
//
//        }
//    }
//
//
////    private fun publisherinfo(productImage: ImageView, prdname: TextView, prddes: TextView, prdpric: TextView) {
////        val Productref = FirebaseDatabase.getInstance().reference.child("Products")
////        Productref.addValueEventListener(object : ValueEventListener {
////            override fun onDataChange(snapshot: DataSnapshot) {
////
////                if (snapshot.exists()) {
////                    val prodduct = snapshot.getValue<ProductModel>(ProductModel::class.java)
////                    Picasso.get().load(prodduct!!.productimg).placeholder(R.drawable.profile).into(productImage)
////                    prdname.text = prodduct.productname
////                    prddes.text = prodduct.description
////                    prdpric.text = prodduct.price
////
////                }
////            }
////
////            override fun onCancelled(error: DatabaseError) {
////            }
////        })
////
////    }
//
//    override fun getFilter() {
//        val filter: Filter = object : Filter() {
//            //background thread
//            override fun performFiltering(keywords: CharSequence): FilterResults {
//                val filtereddata: ArrayList<ProductModel> = ArrayList<ProductModel>()
//                if (keywords.toString().isEmpty()) // the whole data is passed in case of no search value given
//                    filtereddata.addAll(backup!!) else {
//                    // Reading many Mainmodel objects from backup and it is putted into obj object
//                    for (obj in backup!!) {
//                        if (obj.productname.toString().toLowerCase().contains(keywords.toString().toLowerCase())) {
//                            filtereddata.add(obj)
//                        }
//                    }
//                }
//                val results = FilterResults()
//                results.values = filtereddata
//                return results
//            }
//
//            // Mail UI
//            override fun publishResults(constraint: CharSequence, results: FilterResults) {
//
////                mProduct.addAll()
//                list.clear()
////                mProduct.addAll(results.values as ArrayList<ProductModel?>)
//                list.addAll(results.values as java.util.ArrayList<ProductModel?>)
//                notifyDataSetChanged()
//            }
//        }
//    }}
//
//
//
//
