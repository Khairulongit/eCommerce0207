//package com.inthenameofallah.ecommerce.DeletedItem
//
//import android.content.Context
//import android.content.Intent
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.annotation.NonNull
//import androidx.recyclerview.widget.RecyclerView
//import com.inthenameofallah.ecommerce.Model.ProductModel
//import com.inthenameofallah.ecommerce.Admin.ModifyProductAdminActivity
//import com.inthenameofallah.ecommerce.Prevalent.Prevalent
//import com.inthenameofallah.ecommerce.Buyers.ProductDetailsActivity
//import com.inthenameofallah.ecommerce.R
//import com.squareup.picasso.Picasso
//
//class ProductAdapter (private val mContext: Context,
//                      private val mProduct:List<ProductModel>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>()
//{
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//
//        val  view = LayoutInflater.from(mContext).inflate(R.layout.product_iitems_layout,parent,false)
//        return ViewHolder(view)
//
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//
//
//        Log.wtf("KISLAM","ProductAdapter")
//
//
//
//        val product = mProduct[position]
//
//        holder.productdesc.text=product.description
//        holder.productname.text=product.productname
//        holder.productprice.text="₹${product.price}"
//        Picasso.get().load(product.productimg).into(holder.productImage)
//
//
//        holder.itemView.setOnClickListener {
//
//
//            if (Prevalent.UserType=="Buyer") {
//
//                val intent = Intent(mContext, ProductDetailsActivity::class.java)
//                intent.putExtra("pid", product.productid)
//                intent.putExtra("sid", product.sellerid)
//                intent.putExtra("productprice", product.price)
//                intent.putExtra("productquantity", "1")
//                mContext.startActivity(intent)
//
//            }
//
//            else{
//
//
//                val intent = Intent(mContext, ModifyProductAdminActivity::class.java)
//                intent.putExtra("pid", product.productid)
//                mContext.startActivity(intent)
//            }
//
//            }
//
//
//
//        }
//
//
//    override fun getItemCount(): Int {
//
//        return mProduct.size
//    }
//
//
//
//    inner class ViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView)
//    {
//        var  productImage: ImageView
//        var  productname: TextView
//        var  productprice: TextView
//        var  productdesc: TextView
//
//
//        init {
//            productImage = itemView.findViewById(R.id.product_image)
//            productname = itemView.findViewById(R.id.product_name1)
//            productdesc = itemView.findViewById(R.id.product_desc1)
//            productprice = itemView.findViewById(R.id.product_price1)
//
//        }
//    }}
//
//
//
//
