package com.blessingdropshop.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import com.blessingdropshop.app.admin.AdminDashboard
import com.blessingdropshop.app.databinding.ActivityAdminCashoutBinding
import com.blessingdropshop.app.databinding.ActivityAdminDashboardBinding
import com.blessingdropshop.app.databinding.ActivityLandingBinding
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_landing.*

class Landing : AppCompatActivity() {

    private var  appUpdate: AppUpdateManager? = null
    private val requestcode = 100
    private lateinit var binding: ActivityLandingBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var dbUser: DatabaseReference

    override fun onStart() {
        super.onStart()
        auth = Firebase.auth
        val userId = auth.currentUser?.uid.toString()
        if (FirebaseAuth.getInstance().currentUser !=null){
            retrieveAndStoreToken()
            if(userId != "Y65sEzW2NyXJONVAj0aQljccTlV2") {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else{
                startActivity(Intent(this, AdminDashboard::class.java))
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val networkStatus = NetworkStatusHelper(applicationContext)
        networkStatus.observe(this) {
            if (it==NetworkStatus.Available){
                binding.tvDesp.text = "Connected"
            }
            if(it==NetworkStatus.Unavailable){
                binding.tvDesp.text = "Please Connect to the Internet"
            }
        }

        appUpdate = AppUpdateManagerFactory.create(this)
        checkUpdate()

        val login: TextView = findViewById(R.id.button_login1)
        login.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        val register: TextView = findViewById(R.id.button_register1)

        register.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }

    private fun checkUpdate(){
        appUpdate?.appUpdateInfo?.addOnSuccessListener {
            if (it.updateAvailability()==UpdateAvailability.UPDATE_AVAILABLE
                && it.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)){
                appUpdate?.startUpdateFlowForResult(it, AppUpdateType.IMMEDIATE,this,requestcode)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        inProgressUpdate()
    }

    private fun inProgressUpdate(){
        appUpdate?.appUpdateInfo?.addOnSuccessListener {
            if (it.updateAvailability()==UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS){
                appUpdate?.startUpdateFlowForResult(it, AppUpdateType.IMMEDIATE,this,requestcode)
            }
        }
    }

    private fun retrieveAndStoreToken(){
        auth = Firebase.auth
        val userId = auth.currentUser!!.uid
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener {
                if (it.isSuccessful){
                    val token = it.result
                    FirebaseDatabase.getInstance()
                        .getReference("Tokens")
                        .child(userId)
                        .setValue(token)
                }
            }
    }
}
