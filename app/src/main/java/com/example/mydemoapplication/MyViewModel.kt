package com.example.mydemoapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class MyViewModel:ViewModel() {
    private var apiService: ApiService? = null
    private val _data = MutableLiveData<List<MyPost>>()
    val data: LiveData<List<MyPost>> = _data

    init {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Set the logging level
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    fun fetchDataPost(){
        try{
            apiService!!.getPosts().enqueue(object : Callback<List<MyPost>>{
                override fun onResponse(call: Call<List<MyPost>>, response: Response<List<MyPost>>) {
                    if (response.isSuccessful){
                        _data.value = response.body()
                    }
                }

                override fun onFailure(call: Call<List<MyPost>>, t: Throwable) {
                    Log.e("", t.localizedMessage)
                }

            })
        }catch (e: Exception){
            Log.e("", e.localizedMessage)
        }
    }
}