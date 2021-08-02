package com.example.android.newsupdate.activity

import FirestoreClass
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.bumptech.glide.Glide
import com.example.android.newsupdate.R
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.botton_menu.*
import kotlinx.android.synthetic.main.nav_header_main.*
import model.User

class MainActivity : BaseActivity() , NavigationView.OnNavigationItemSelectedListener{

    companion object{
        const val MY_PROFILE_REQUEST_CODE: Int = 11
        const val CREATE_BOARD_REQUEST_CODE: Int = 12
    }
    private lateinit var  mUserName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBar()

        nav_view.setNavigationItemSelectedListener(this)

        FirestoreClass().loadUserData(this)



    }



    private fun setupActionBar() {
        setSupportActionBar(toolbar_main_activity)
        toolbar_main_activity.setNavigationIcon(R.drawable.ic_action_navigation_menu)

        toolbar_main_activity.setNavigationOnClickListener {
            //Toggle drawer
            toggleDrawer()
        }
    }

    private fun toggleDrawer() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            drawer_layout.openDrawer(GravityCompat.START)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_my_profile ->{
                 startActivity(Intent(this,MyProfileActivity::class.java))
            }
            R.id.nav_sign_out ->{
                FirebaseAuth.getInstance().signOut()

                startActivity(Intent(this,IntroActivity::class.java))
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                finish()
            }
            R.id.nav_share ->{
                Toast.makeText(this,"Share your app",Toast.LENGTH_LONG).show()
            }
            R.id.nav_covid_cases ->{
              startActivity(Intent(this,covid_cases::class.java))
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                finish()
            }
        }

        return true
    }


    fun updateNavigationUserDetails(user: User){
        hideProgressDialog()
        mUserName = user.name

        Glide.with(this)
            .load(user.image)
            .centerCrop()
            .placeholder(R.drawable.ic_user_place_holder)
            .into(nav_user_image)

        tv_username.text = user.name
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            doubleBackToExit()
        }
    }


}