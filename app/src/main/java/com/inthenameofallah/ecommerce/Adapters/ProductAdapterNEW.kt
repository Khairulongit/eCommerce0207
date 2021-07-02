package com.inthenameofallah.ecommerce.Adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.inthenameofallah.ecommerce.Admin.ModifyProductAdminActivity
import com.inthenameofallah.ecommerce.Buyers.ProductDetailsActivity
import com.inthenameofallah.ecommerce.Model.ProductModel
import com.inthenameofallah.ecommerce.Prevalent.Prevalent
import com.inthenameofallah.ecommerce.R
import com.squareup.picasso.Picasso


class ProductAdapterNEW(private val mContext: Context): RecyclerView.Adapter<ProductAdapterNEW.ViewHolder>(), Filterable {



    var list =ArrayList<ProductModel>()
    var backup =ArrayList<ProductModel>()
    lateinit var context: Context

    fun setData(list: ArrayList<ProductModel>) {
        this.list=list
        this.context=mContext
        this.backup=list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.product_iitems_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {



        val mainModel: ProductModel = list[position]
        holder.productdesc.text=mainModel.description
        holder.productname.text=mainModel.productname
        holder.productprice.text="₹${mainModel.price}"
        Picasso.get().load(mainModel.productimg).into(holder.productImage)


        holder.itemView.setOnClickListener {
            if (Prevalent.UserType=="Buyer") {

                val intent = Intent(holder.itemView.context, ProductDetailsActivity::class.java)
                intent.putExtra("pid", mainModel.productid)
                intent.putExtra("sid", mainModel.sellerid)
                intent.putExtra("productprice", mainModel.price)
                intent.putExtra("productquantity", "1")
                holder.itemView.context.startActivity(intent)
                (holder.itemView.context as Activity).finish()
            }

            else{


                val intent = Intent(holder.itemView.context, ModifyProductAdminActivity::class.java)
                intent.putExtra("pid", mainModel.productid)
                holder.itemView.context.startActivity(intent)
                (holder.itemView.context as Activity).finish()

            }

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getFilter(): Filter {

        return object :Filter(){
            override fun performFiltering(keywords: CharSequence?): FilterResults {
                val results = FilterResults()
                if (keywords==null||keywords.length<0) {
                    results.count = backup.size
                    results.values = backup
                }else{
                    var searchchar=keywords.toString().toLowerCase()
                    var itemmodel = ArrayList<ProductModel>()
                    for (item in backup){
                        if (item.productname.toLowerCase().contains(searchchar)||item.description.toLowerCase().contains(searchchar)){
                            itemmodel.add(item)
                        }
                    }

                    results.count=itemmodel.size
                    results.values=itemmodel
                }

                return results


            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                list=results!!.values as ArrayList<ProductModel>
                notifyDataSetChanged()
            }

        }
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var  productImage: ImageView = itemView.findViewById(R.id.product_image)
        var  productname: TextView = itemView.findViewById(R.id.product_name1)
        var  productprice: TextView = itemView.findViewById(R.id.product_price1)
        var  productdesc: TextView = itemView.findViewById(R.id.product_desc1)

    }

}