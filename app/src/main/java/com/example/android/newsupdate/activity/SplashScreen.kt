package com.example.android.newsupdate.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.example.android.newsupdate.R
import com.google.firebase.auth.FirebaseAuth

class SplashScreen : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

       /* firebaseAuth = FirebaseAuth.getInstance()
        checkUser()*/

        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,  //Inorder to hide status bar
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
        )

        Handler().postDelayed({

            var currentUserId = FireStoreClass().getCurrentUserId()

            if(currentUserId.isNotEmpty()){
                startActivity(Intent(this, MainActivity::class.java))
            }else {
                startActivity(Intent(this, IntroActivity::class.java))
            }
            finish()
                              },2500)
    }

    /*private fun checkUser() {
        //get current User
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null){
            startActivity(Intent(this,MainActivity::class.java))
        }else{
            //user logged in and get user info
            startActivity(Intent(this,IntroActivity::class.java))
            finish()
        }
    }*/
}