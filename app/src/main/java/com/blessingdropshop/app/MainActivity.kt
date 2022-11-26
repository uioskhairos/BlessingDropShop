package com.blessingdropshop.app

import android.R.layout
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        userRecyclerView = findViewById(R.id.fragment_home)
//        userArrayList= arrayListOf<UserModel>()
//
//        getUserData()
        setSupportActionBar(findViewById(R.id.toolbar))
//        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        val navController = navHostFragment!!.navController
        //bottom nav setup
        NavigationUI.setupWithNavController(bottom_nav, navController);

//        setupBottomNavMenu(navController)
        setupSideNavigationMenu(navController)
        setupActionBar(navController)

    }

    private fun setupBottomNavMenu(navController: NavController) {
        bottom_nav?.let {
            NavigationUI.setupWithNavController(it, navController)
        }
    }

    private fun setupSideNavigationMenu(navController: NavController) {
        nav_view?.let {
            NavigationUI.setupWithNavController(it, navController)
        }
    }

    private fun setupActionBar(navController: NavController) {
        NavigationUI.setupActionBarWithNavController(this, navController, drawer_layout)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        val navController = navHostFragment!!.navController
        NavigationUI.setupWithNavController(bottom_nav, navController);
        val navigated = NavigationUI.onNavDestinationSelected(item, navController)
        return navigated || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.nav_host_fragment),drawer_layout)
    }

//    private fun getUserData() {
//        auth = Firebase.auth
//        database = Firebase.database.reference
//        //get user account type
//        val uId = auth.currentUser?.uid.toString()
//        database = FirebaseDatabase.getInstance().getReference("User")
//
//        database.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if (snapshot.exists()){
//                    val user = snapshot.child(uId).getValue(UserModel::class.java)
//                    userArrayList.add(user!!)
//                }
//                val mAdapter = UserModelAdapter(userArrayList)
//                userRecyclerView.adapter = mAdapter
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })
//    }

}