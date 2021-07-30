package com.example.android.newsupdate.activity

import FirestoreClass
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.android.newsupdate.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        Handler().postDelayed({

            var currentUserId = FirestoreClass().getCurrentUserId()

            if(currentUserId.isNotEmpty()){
                startActivity(Intent(this, MainActivity::class.java))
            }else {
                startActivity(Intent(this, IntroActivity::class.java))
            }
                              },2500)
    }
}