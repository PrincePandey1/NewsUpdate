package com.example.android.newsupdate.activity

import Adapter.HealthAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.newsupdate.R
import dataclass.News
import kotlinx.android.synthetic.main.activity_health.*
import kotlinx.android.synthetic.main.activity_sport.*
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HealthActivity : BaseActivity() {

    lateinit var adapter: HealthAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health)

        getHealthNews()
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
        btn_foreign.setOnClickListener {
            startActivity(Intent(this,ForeignActivity::class.java))
        }
    }

    private fun setUpActionBar(){

        setSupportActionBar(toolbar_health_activity)

        val actionBar = supportActionBar

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_white)
        }
        toolbar_health_activity.setNavigationOnClickListener {
            onBackPressed()
        }
    }



    private fun getHealthNews(){

        showProgressDialog("Please wait..")
        val news = NewsService.newsInstance.getTechHeadlines("in","health",1)
        news.enqueue(object: Callback<News>{
            override fun onResponse(call: Call<News>, response: Response<News>) {

                hideProgressDialog()
                var news = response.body()
                Log.d("Success",news.toString())

                if(news != null){

                    adapter = HealthAdapter(this@HealthActivity , news.articles )
                    rv_boards_list.adapter = adapter
                    rv_boards_list.layoutManager = LinearLayoutManager(this@HealthActivity)
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {

                hideProgressDialog()
               Log.d("Failure","No Response From Server" , t)
            }

        })

    }
}