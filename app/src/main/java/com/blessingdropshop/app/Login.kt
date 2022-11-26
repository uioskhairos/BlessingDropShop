package com.blessingdropshop.app

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.blessingdropshop.app.admin.AdminDashboard
import com.blessingdropshop.app.admin.ApproveItemsAdminActivity
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {
    //firebaseauth
    private lateinit var auth: FirebaseAuth
    private lateinit var dbUser: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        val registertext: TextView = findViewById(R.id.textView_register_now)

        registertext.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
        forgotPassword.setOnClickListener{
            val intent = Intent(this, ForgotPassword::class.java)
            startActivity(intent)
        }

        val loginButton: Button = findViewById(R.id.button_login)

        loginButton.setOnClickListener{
            loginButton.isEnabled = false
//            loginButton.setTextColor(ContextCompat.getColor(textView.context, R.color.white))
            loginButton.text = "Loading..."
//            loginButton.setBackgroundColor(ContextCompat.getColor(loginButton.context, R.color.light_gray))
            ResourcesCompat.getDrawable(resources, R.drawable.disabled, null);
            performLogin()
        }
    }
    private fun performLogin(){
        //get input from user
        val email: EditText = findViewById(R.id.editText_email_login)
        val password: EditText = findViewById(R.id.editText_password_login)
        val loginButton: Button = findViewById(R.id.button_login)

        //null check of inputs
        if (email.text.isEmpty() || password.text.isEmpty()){
            Toast.makeText(this, "Please fill all the required fields", Toast.LENGTH_SHORT)
                .show()
            loginButton.isEnabled = true
            loginButton.text = "Login"
            ResourcesCompat.getDrawable(resources, R.drawable.rounded, null)
            return
        }
        val emailInput = email.text.toString()
        val passwordInput = password.text.toString()

        auth.signInWithEmailAndPassword(emailInput, passwordInput)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    retrieveAndStoreToken()
//                    go to admin
                    if(emailInput=="blessing@dropshop.com"){
                        val intent = Intent(this, AdminDashboard::class.java)
                        //to prevent user from returning to Register Activity
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish() // to close register activity
                        Toast.makeText(baseContext, "Welcome Admin.",
                            Toast.LENGTH_SHORT).show()
                    }
                    else{
//                        go to main

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(baseContext, "Success.",
                            Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    loginButton.isEnabled = true
                    loginButton.text = "Login"
                    ResourcesCompat.getDrawable(resources, R.drawable.rounded, null)
                }
            }
            .addOnFailureListener {
                Toast.makeText(baseContext, "Authentication failed. ${it.localizedMessage}",
                    Toast.LENGTH_SHORT).show()
                loginButton.isEnabled = true
                loginButton.text = "Login"
                ResourcesCompat.getDrawable(resources, R.drawable.rounded, null)
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