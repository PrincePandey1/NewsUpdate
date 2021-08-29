package com.example.android.newsupdate.activity

import Adapter.SportAdapter
import Adapter.TechAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.newsupdate.R
import dataclass.News
import kotlinx.android.synthetic.main.activity_sport.*
import kotlinx.android.synthetic.main.activity_tech.*
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SportActivity : AppCompatActivity() {

    lateinit var adapter: SportAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sport)

       setUpActionBar()
        getTechNews()

        btn_health.setOnClickListener {
            startActivity(Intent(this,HealthActivity::class.java))
        }


    }


    private fun setUpActionBar(){

        setSupportActionBar(toolbar_sport_activity)

        val actionBar = supportActionBar

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_white)
        }
        toolbar_sport_activity.setNavigationOnClickListener {
            onBackPressed()
        }
    }



    private fun getTechNews(){

        val news = NewsService.newsInstance.getTechHeadlines("in","sports",1)
        news.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                Log.d("Success" , news.toString())

                if (news != null) {
                    adapter = SportAdapter(this@SportActivity , news.articles)
                    rv_boards_list.adapter = adapter
                    rv_boards_list.layoutManager = LinearLayoutManager(this@SportActivity)
                }

            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("Failure","Something went Wrong" , t)
            }

        })
    }
}