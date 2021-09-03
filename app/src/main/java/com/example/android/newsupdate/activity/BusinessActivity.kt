package com.example.android.newsupdate.activity

import Adapter.BusinessAdapter
import Adapter.HealthAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.newsupdate.R
import dataclass.News
import kotlinx.android.synthetic.main.activity_business.*
import kotlinx.android.synthetic.main.activity_health.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.progress_dialog.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BusinessActivity : BaseActivity() {

    lateinit var adapter: BusinessAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business)

        getBusinessNews()
        setUpActionBar()

        btn_tech.setOnClickListener {
            startActivity(Intent(this,TechActivity::class.java))
        }

        btn_sport.setOnClickListener {
            startActivity(Intent(this,SportActivity::class.java))
        }

        btn_health.setOnClickListener {
            startActivity(Intent(this,HealthActivity::class.java))
        }

        btn_media.setOnClickListener {
            startActivity(Intent(this,MediaActivity::class.java))
        }
        btn_foreign.setOnClickListener {
            startActivity(Intent(this,ForeignActivity::class.java))
        }
    }

    private fun setUpActionBar(){

        setSupportActionBar(toolbar_business_activity)

        val actionbar = supportActionBar

       if(actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true)
           actionbar.setHomeAsUpIndicator(R.drawable.ic_back_white)
        }
        toolbar_business_activity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun getBusinessNews(){

        val news = NewsService.newsInstance.getTechHeadlines("in","business",1)

        showProgressDialog("Please wait..")
        news.enqueue(object: Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {

                hideProgressDialog()

                var news = response.body()
                Log.d("Success",news.toString())

                if(news != null){

                    adapter = BusinessAdapter(this@BusinessActivity , news.articles )
                    rv_boards_list.adapter = adapter
                    rv_boards_list.layoutManager = LinearLayoutManager(this@BusinessActivity)
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {

                hideProgressDialog()

                Log.d("Failure","No Response From Server" , t)
            }

        })

    }
}