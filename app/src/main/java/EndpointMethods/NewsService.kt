package com.example.android.newsupdate.activity

import dataclass.News
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//https://newsapi.org/v2/top-headlines?country=in&apiKey=f19d9c29e1d34d64a77ed4c5ecf40a7f
//https://newsapi.org/v2/top-headlines?sources=techcrunch&apiKey=f19d9c29e1d34d64a77ed4c5ecf40a7f


const val BASE_URL = "https://newsapi.org/"
const val API_KEY = "f19d9c29e1d34d64a77ed4c5ecf40a7f"


interface NewsInterface{

    @GET("v2/top-headlines?apiKey=f19d9c29e1d34d64a77ed4c5ecf40a7f")
    fun getHeadlines(@Query("country")country: String , @Query("page")page: Int): Call<News>

    // http://newsapi.org/v2/top-headlines?apiKey=f19d9c29e1d34d64a77ed4c5ecf40a7f&country=in&page=1
}

object NewsService {
    val newsInstance: NewsInterface
    init{    //Creating Object of Retrofit
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        newsInstance = retrofit.create(NewsInterface::class.java)

    }
}