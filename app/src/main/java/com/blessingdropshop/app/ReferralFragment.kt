package com.blessingdropshop.app

import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet.GONE
import androidx.constraintlayout.widget.ConstraintSet.VISIBLE
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blessingdropshop.app.adapters.ReferralModelAdapter
import com.blessingdropshop.app.databinding.FragmentReferralBinding
import com.blessingdropshop.app.models.ReferralModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.collections.ArrayList

class ReferralFragment : Fragment(R.layout.fragment_referral) {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var referralArrayList: ArrayList<ReferralModel>
    private lateinit var referralRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var binding: FragmentReferralBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentReferralBinding.bind(view)

        referralRecyclerView = requireView().findViewById(R.id.referralsList)
        tvLoadingData = requireView().findViewById(R.id.tvLoadingData)
        referralRecyclerView.layoutManager = LinearLayoutManager(this.context)
        referralRecyclerView.setHasFixedSize(true)

        referralArrayList= arrayListOf<ReferralModel>()

        auth = Firebase.auth
        database = Firebase.database.reference
        //get user account type
        val uId = auth.currentUser?.uid.toString()
        database = FirebaseDatabase.getInstance().getReference("User")

        database.orderByChild("userReferrerId").equalTo(uId).addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                referralArrayList.clear()
                if (snapshot.exists()){
                    for (i in snapshot.children){
                        val user = i.getValue(ReferralModel::class.java)
                        referralArrayList.add(user!!)
                    }

                    val mAdapter = ReferralModelAdapter(referralArrayList)
                    referralRecyclerView.adapter = mAdapter
                }
                else{
                    referralRecyclerView.visibility = GONE
                    binding.tvEmpty.visibility = VISIBLE
                    Toast.makeText(this@ReferralFragment.context,"You don't have any referral.", Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}