package com.blessingdropshop.app.models

data class DroppersModel(val userId: Any? ="", val userType: Any? ="", val userEmail: String? = "", val userFullName: String? = "", val userShopName: String? = "", val userReferrer: String? = "", val time: Any? ="")
//import android.util.Log
//import android.widget.Toast
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.database.*
//import com.google.firebase.database.ktx.database
//import com.google.firebase.ktx.Firebase
//
//private const val TAG = "UserViewModel"
//
//class UserViewModel : ViewModel() {
//    private val userLiveData: MutableLiveData<MutableList<UserModel>>
//    private lateinit var auth: FirebaseAuth
//    private lateinit var database: DatabaseReference
//    private lateinit var user: UserModel
//
//    init {
//        Log.i(TAG, "init")
//        userLiveData = MutableLiveData()
//    }
//
//    private fun userData(): MutableList<UserModel>{
//        auth = Firebase.auth
//        database = Firebase.database.reference
//        //get user account type
//        val userId = auth.currentUser?.uid.toString()
//        database = FirebaseDatabase.getInstance().getReference("User")
//
//        database.child(userId)
//            .addValueEventListener(object: ValueEventListener {
//
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    val userData = dataSnapshot.getValue(UserModel::class.java)!!
//                    return user
//                }
//
//                override fun onCancelled(p0: DatabaseError) {
//                    //do whatever you need
////                    Toast.makeText(this@UserViewModel.requireActivity(), "$userId cancelled", Toast.LENGTH_LONG).show()
//                }
//            })
//    }
//}
