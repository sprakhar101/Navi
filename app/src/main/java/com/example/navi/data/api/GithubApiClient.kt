package com.example.navi.data.api

import com.example.navi.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GithubApiClient {
    private var retrofitClient: GithubServiceApi? = null

    fun getClientInstace(): GithubServiceApi {
        if(retrofitClient == null) {
            retrofitClient = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.SERVER_BASE_URL)
                .build()
                .create(GithubServiceApi::class.java)
        }
        return retrofitClient!!
    }
}