package com.farukkaradeniz.yildizyemekhanem.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Faruk Karadeniz on 22.07.2018.
 * Twitter: twitter.com/Omeerfk
 * Github: github.com/FarukKaradeniz
 * LinkedIn: linkedin.com/in/FarukKaradeniz
 * Website: farukkaradeniz.com
 */
interface YemekhaneService {
    @GET("yemekmenu/{month}/{year}")
    fun ogrenciMenu(@Path("month") month: Int, @Path("year") year: Int): Call<ResponseBody>

    @GET("alakartmenu/{month}/{year}")
    fun alakartMenu(@Path("month") month: Int, @Path("year") year: Int): Call<ResponseBody>
}

class YemekhaneAPI {
    companion object {
        private val baseURL: String = "http://www.sks.yildiz.edu.tr/"
        private val yemekhaneService: YemekhaneService = Retrofit.Builder()
                .baseUrl(baseURL)
                .build()
                .create(YemekhaneService::class.java)

        fun getYemekhaneService() = yemekhaneService
    }
}
