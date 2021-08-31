package EndpointMethods

import dataclass.MyData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET

const val BASE_URL = "https://data.covid19india.org/"


interface CoronaInterface{
    @GET("data.json")
    fun getData(): Call<MyData>
}

object Interface {

    val instance: CoronaInterface

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        instance = retrofit.create(CoronaInterface::class.java)
    }
}

