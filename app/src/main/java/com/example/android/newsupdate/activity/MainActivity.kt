package com.example.android.newsupdate.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android.newsupdate.R
import kotlinx.android.synthetic.main.botton_menu.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_sport.setOnClickListener {
            startActivity(Intent(this,sports_activity::class.java))
        }
    }
}