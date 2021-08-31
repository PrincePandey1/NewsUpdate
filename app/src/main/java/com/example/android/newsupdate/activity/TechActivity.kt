package com.example.android.newsupdate.activity

import Adapter.HealthAdapter
import Adapter.TechAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.newsupdate.R
import dataclass.News
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_tech.*
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TechActivity : AppCompatActivity() {

    lateinit var adapter: TechAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tech)


        setUpActionBar()
        getTechNews()


        btn_health.setOnClickListener {
            startActivity(Intent(this,HealthActivity::class.java))
        }

        btn_sport.setOnClickListener {
            startActivity(Intent(this,SportActivity::class.java))
        }

        btn_business.setOnClickListener {
            startActivity(Intent(this,BusinessActivity::class.java))
        }

        btn_media.setOnClickListener {
            startActivity(Intent(this,MediaActivity::class.java))
        }
        btn_foreign.setOnClickListener {
            startActivity(Intent(this,ForeignActivity::class.java))
        }

    }

    private fun setUpActionBar(){

        setSupportActionBar(toolbar_tech_activity)

        val actionBar = supportActionBar

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_white)
        }
        toolbar_tech_activity.setNavigationOnClickListener {
            onBackPressed()
        }
    }


    private fun getTechNews(){

        val news = NewsService.newsInstance.getTechHeadlines("in","technology",1)
        news.enqueue(object: Callback<News>{
            override fun onResponse(call: Call<News>, response: Response<News>) {

                var news = response.body()
                Log.d("Success",news.toString())

                if(news != null){

                    adapter = TechAdapter(this@TechActivity , news.articles )
                    rv_boards_list.adapter = adapter
                    rv_boards_list.layoutManager = LinearLayoutManager(this@TechActivity)
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("Failure","No Response From Server" , t)
            }

        })

    }



}