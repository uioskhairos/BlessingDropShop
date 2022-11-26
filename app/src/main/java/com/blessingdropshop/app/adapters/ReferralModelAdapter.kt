package com.blessingdropshop.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blessingdropshop.app.R
import com.blessingdropshop.app.models.ReferralModel

class ReferralModelAdapter(private val referralList: ArrayList<ReferralModel>): RecyclerView.Adapter <ReferralModelAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.referrals_list,
        parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentUser = referralList[position]

        holder.refName.text = currentUser.userFullName
        holder.refShopName.text = currentUser.userShopName
        holder.refEmail.text = currentUser.userEmail
    }

    override fun getItemCount(): Int {
        return referralList.size
    }

    inner class  MyViewHolder (itemView : View): RecyclerView.ViewHolder(itemView){
        val refName : TextView = itemView.findViewById(R.id.refName)
        val refShopName : TextView = itemView.findViewById(R.id.refShopName)
        val refEmail : TextView = itemView.findViewById(R.id.refEmail)
    }
}