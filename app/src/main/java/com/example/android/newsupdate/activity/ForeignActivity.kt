package com.example.android.newsupdate.activity


import Adapter.ForeignAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.newsupdate.R
import dataclass.News
import kotlinx.android.synthetic.main.activity_business.*
import kotlinx.android.synthetic.main.activity_foreign.*
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForeignActivity : AppCompatActivity() {

    lateinit var adapter:ForeignAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foreign)

        getForeignNews()
        setUpActionBar()


        btn_tech.setOnClickListener {
            startActivity(Intent(this,TechActivity::class.java))
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
        btn_health.setOnClickListener {
            startActivity(Intent(this,HealthActivity::class.java))
        }
    }

    private fun setUpActionBar(){

        setSupportActionBar(toolbar_foreign_activity)

        val actionbar = supportActionBar

        if(actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.setHomeAsUpIndicator(R.drawable.ic_back_white)
        }
        toolbar_foreign_activity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun getForeignNews(){

        val news = NewsService.newsInstance.getTechHeadlines("in","business",1)
        news.enqueue(object: Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {

                var news = response.body()
                Log.d("Success",news.toString())

                if(news != null){

                    adapter = ForeignAdapter(this@ForeignActivity, news.articles )
                    rv_boards_list.adapter = adapter
                    rv_boards_list.layoutManager = LinearLayoutManager(this@ForeignActivity)
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("Failure","No Response From Server" , t)
            }

        })

    }
}