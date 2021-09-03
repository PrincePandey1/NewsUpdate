package com.example.android.newsupdate.activity

import Adapter.CovidAdapter
import EndpointMethods.Interface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.newsupdate.R
import dataclass.MyData
import kotlinx.android.synthetic.main.activity_corona.*
import kotlinx.android.synthetic.main.activity_health.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CoronaActivity : BaseActivity() {

    lateinit var adapter: CovidAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_corona)

        getresult()
        setUpActionBar()
    }

    private fun setUpActionBar(){

        setSupportActionBar(toolbar_Corona_activity)

        val actionBar = supportActionBar

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_white)
        }
        toolbar_Corona_activity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun getresult() {
        val data = Interface.instance.getData()

        showProgressDialog("Please wait..")

        data.enqueue(object : Callback<MyData?> {
            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                hideProgressDialog()
                val response = response.body()
                Log.d("Success" , response.toString())

                if(response != null){
                    adapter = CovidAdapter(this@CoronaActivity , response.statewise)
                    Covid_recyclerView.adapter = adapter
                    Covid_recyclerView.layoutManager = LinearLayoutManager(this@CoronaActivity)

                }
            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
                hideProgressDialog()
                Log.d("Error" , "Something Is Wrong")
            }
        })


}
}