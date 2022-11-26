package com.blessingdropshop.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blessingdropshop.app.R
import com.blessingdropshop.app.models.UserModel

class UserModelAdapter(private val userList: ArrayList<UserModel>): RecyclerView.Adapter <UserModelAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.fragment_home,
            parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentUser = userList[position]

        holder.userFullName.text = currentUser.userFullName
        holder.userShopName.text = currentUser.userShopName
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class  MyViewHolder (itemView : View): RecyclerView.ViewHolder(itemView){
        val userFullName : TextView = itemView.findViewById(R.id.fullName_home)
        val userShopName : TextView = itemView.findViewById(R.id.shopName_home)
    }
}