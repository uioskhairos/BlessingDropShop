package com.blessingdropshop.app

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blessingdropshop.app.adapters.DroppersModelAdapter
import com.blessingdropshop.app.databinding.FragmentDroppersBinding
import com.blessingdropshop.app.models.DroppersModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class DroppersFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var userArrayList: ArrayList<DroppersModel>
    private lateinit var filteredUserArrayList: ArrayList<DroppersModel>
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var newUserRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var binding: FragmentDroppersBinding
    private lateinit var searchView: SearchView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_droppers, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        userRecyclerView = requireView().findViewById(R.id.droppersList)
//        newUserRecyclerView = requireView().findViewById(R.id.droppersList)
//        tvLoadingData = requireView().findViewById(R.id.tvLoadingData)
//        searchView = requireView().findViewById(R.id.search_dropper)
//        userRecyclerView.layoutManager = LinearLayoutManager(this.context)
//        userRecyclerView.setHasFixedSize(true)
//
//        userArrayList= arrayListOf<DroppersModel>()
//        filteredUserArrayList= arrayListOf<DroppersModel>()
//
//        getUserData()
//        searchView.clearFocus()
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//
//                filteredUserArrayList.clear()
//                val searchText = newText!!.lowercase(Locale.getDefault())
//                if (searchText.isNotEmpty()){
//                    userArrayList.forEach{
//                        if(it.userFullName?.lowercase(Locale.getDefault())!!.contains(searchText)||it.userShopName?.lowercase(Locale.getDefault())!!.contains(searchText)){
//                            filteredUserArrayList.add(it)
//                        }
//                    }
//
//                    newUserRecyclerView.adapter!!.notifyDataSetChanged()
//                }
//                else{
//                    filteredUserArrayList.clear()
//                    filteredUserArrayList.addAll(userArrayList)
//                    newUserRecyclerView.adapter!!.notifyDataSetChanged()
//                }
//                return false
//            }
//
//        })
    }


//    private fun getUserData() {
////        userRecyclerView.visibility = view.GONE
//        auth = Firebase.auth
//        database = Firebase.database.reference
//        //get user account type
//        val uId = auth.currentUser?.uid.toString()
//        database = FirebaseDatabase.getInstance().getReference("User")
//
//        database.addValueEventListener(object :ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                userArrayList.clear()
//                filteredUserArrayList.clear()
//                if (snapshot.exists()){
//                    for (i in snapshot.children){
//                        val user = i.getValue(DroppersModel::class.java)
//                        userArrayList.add(user!!)
//                    }
//
//                    filteredUserArrayList.addAll(userArrayList)
//                    val mAdapter = DroppersModelAdapter(filteredUserArrayList)
//                    userRecyclerView.adapter = mAdapter
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })
//    }
}