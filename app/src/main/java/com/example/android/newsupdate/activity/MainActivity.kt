package com.example.android.newsupdate.activity

import Adapter.NewsAdapter
import FireStoreClass
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.android.newsupdate.R
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.nav_header_main.*
import dataclass.News
import kotlinx.android.synthetic.main.content_main.*
import model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity() , NavigationView.OnNavigationItemSelectedListener{



    companion object{
        const val MY_PROFILE_REQUEST_CODE: Int = 11
        const val CREATE_BOARD_REQUEST_CODE: Int = 12
    }
    private lateinit var  mUserName: String
    lateinit var adapter: NewsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBar()

        nav_view.setNavigationItemSelectedListener(this)


          FireStoreClass().loadUserData(this)

         getNews() //getting headlines data from server

    }//onCreate

    private fun getNews() {
        val news = NewsService.newsInstance.getHeadlines("in" , 1)
        news.enqueue(object: Callback<News>{
            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("Prince","Error in fetching News" , t)
            }

            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                if(news != null){
                    Log.d("Prince" , news.toString())
                   adapter = NewsAdapter(this@MainActivity , news.articles)
                   rv_boards_list.adapter = adapter
                    rv_boards_list.layoutManager = LinearLayoutManager(this@MainActivity)
                }
            }

        })


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
                 startActivityForResult(Intent(this,MyProfileActivity::class.java),
                     MY_PROFILE_REQUEST_CODE)
            }
            R.id.nav_sign_out ->{
                FirebaseAuth.getInstance().signOut()

                startActivity(Intent(this,IntroActivity::class.java))
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                finish()
            }
            R.id.nav_share ->{
                val intent= Intent()
                    intent.action=Intent.ACTION_SEND
                    intent.putExtra(Intent.EXTRA_TEXT,"Hey Check out this Great app:\n" +
                            "\"https://firebasestorage.googleapis.com/v0/b/projemanage-8c206.appspot.com/o/BOARD_IMAGE1625987130197.jpg?alt=media&token=76bd81a9-0ea7-43db-b5d9-9d8a73088e47\"")
                    intent.type="text/plain"
                    startActivity(Intent.createChooser(intent,"Share To:"))
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
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK  && requestCode == MY_PROFILE_REQUEST_CODE){
            FireStoreClass().loadUserData(this)
        }else{
            Log.e("Cancelled","Cancelled")
        }
    }

}//Main