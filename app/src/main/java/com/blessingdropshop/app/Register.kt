package com.blessingdropshop.app

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import com.blessingdropshop.app.databinding.ActivityRegisterBinding
import com.blessingdropshop.app.models.UserRegisterModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Register : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var fullName: EditText
    private lateinit var shopName: EditText
    private lateinit var email: EditText
    private lateinit var referrer: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var registerButton: Button
    private lateinit var binding: ActivityRegisterBinding

    private lateinit var dbUser: DatabaseReference


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        fullName = findViewById(R.id.editText_fullName_addItem)
        shopName = findViewById(R.id.editText_shopName_addItem)
        email = findViewById(R.id.editText_amount_addItem)
        referrer = findViewById(R.id.editText_hf_addItem)
        password = findViewById(R.id.editText_password_register)
        registerButton = findViewById(R.id.button_addCashout)
        confirmPassword = findViewById(R.id.editText_confirmPassword_register)


        dbUser = FirebaseDatabase.getInstance().getReference("User")

        val loginText: TextView = findViewById(R.id.textView_login_now)
        loginText.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        registerButton.setOnClickListener{
            registerButton.isEnabled = false
            registerButton.text = "Loading..."
            ResourcesCompat.getDrawable(resources, R.drawable.disabled, null);
            val userShopNameCheck = shopName.text.toString().uppercase().trim()
            dbUser.orderByChild("userShopName").equalTo(userShopNameCheck).addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        shopName.error = "Shop Name is not available"
                        shopName.requestFocus()
                        registerButton.isEnabled = true
                        registerButton.text = "Register"
                        return
                    }
                    else{
                        val referral = referrer.text.toString().uppercase().trim()
                        dbUser.orderByChild("userShopName").equalTo(referral).addValueEventListener(object :
                            ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if (snapshot.exists()){
                                    for (i in snapshot.children){
                                        val userReferrerId = i.child("userId").value.toString()
                                        signup(userReferrerId)
                                    }
                                }
                                else{
                                    registerButton.isEnabled = true
                                    registerButton.text = "Register"
                                    referrer.error = "Referral shop name does not exist"
                                    referrer.requestFocus()
                                    return
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                registerButton.isEnabled = true
                                registerButton.text = "Register"
                            }

                        })
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    registerButton.isEnabled = true
                    registerButton.text = "Register"
                }

            })
        }

    }

    private fun signup(userReferrerId: String) {
        auth = Firebase.auth
        if (referrer.text.isEmpty()) {
            registerButton.isEnabled = true
            registerButton.text = "Register"
            referrer.error = "Referrer is required"
            referrer.requestFocus()
            return
        }
        if (fullName.text.isEmpty()){
            registerButton.isEnabled = true
            registerButton.text = "Register"
            fullName.error = "Full Name is required"
            fullName.requestFocus()
            return
        }
        else if(fullName.text.toString().length <2){
            registerButton.isEnabled = true
            registerButton.text = "Register"
            fullName.error = "Too short"
            fullName.requestFocus()
            return
        }
        if (shopName.text.isEmpty()){
            registerButton.isEnabled = true
            registerButton.text = "Register"
            shopName.error = "Shop Name is required"
            shopName.requestFocus()
            return
        }
        if (email.text.isEmpty()){
            registerButton.isEnabled = true
            registerButton.text = "Register"
            email.error = "Email is required"
            email.requestFocus()
            return
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()){
            registerButton.isEnabled = true
            registerButton.text = "Register"
            email.error = "Email is not valid"
            email.requestFocus()
            return
        }
        if (password.text.isEmpty()){
            registerButton.isEnabled = true
            registerButton.text = "Register"
            password.error = "Password is required"
            password.requestFocus()
            return
        }

        else if(password.text.toString().length<6){
            registerButton.isEnabled = true
            registerButton.text = "Register"
            password.error = "Too short. Must be al least 6 characters."
            password.requestFocus()
            return
        }

        if (password.text.toString() != confirmPassword.text.toString()) {
            registerButton.isEnabled = true
            registerButton.text = "Register"
            Toast.makeText(this, "Password and Confirm Password do not match", Toast.LENGTH_SHORT)
                .show()
            return
        }


        val inputEmail = email.text.toString().trim()
        val inputPassword = password.text.toString()
        val userFullName = fullName.text.toString().trim()
        val userShopName = shopName.text.toString().uppercase().trim()
        val userEmail = email.text.toString().trim()
        val userReferrer = referrer.text.toString().uppercase().trim()
        //time
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val time = current.format(formatter).toString().trim()

        auth.createUserWithEmailAndPassword(inputEmail,inputPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    retrieveAndStoreToken()
                    val firebaseUser = Firebase.auth.currentUser
                    firebaseUser!!.sendEmailVerification()
                    val uid = auth.currentUser?.uid.toString()
                    //insert to realtime DB
                    val user = UserRegisterModel(uid,userEmail, userFullName, userShopName, userReferrer, userReferrerId, time)
                    dbUser.child(uid).setValue(user)
                        .addOnCompleteListener { Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show() }
                        .addOnFailureListener { err -> Toast.makeText(this, "Data Insertion Error ${err.message}", Toast.LENGTH_SHORT).show() }

                    // Sign in success, move to dashboard
                    val intent = Intent(this, MainActivity::class.java)
                    //to prevent user from returning to Register Activity
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish() // to close register activity
                } else {
                    registerButton.isEnabled = true
                    registerButton.text = "Register"
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Registration failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                registerButton.isEnabled = true
                registerButton.text = "Register"
                Toast.makeText(this, "Error occurred ${it.localizedMessage}",Toast.LENGTH_LONG)
                    .show()
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