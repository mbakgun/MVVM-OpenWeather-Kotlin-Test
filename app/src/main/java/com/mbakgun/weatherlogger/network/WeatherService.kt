package com.mbakgun.weatherlogger.network

import com.mbakgun.weatherlogger.network.Response.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by burakakgun on 14.08.2018.
 */
interface WeatherService {

    @GET("/data/2.5/weather")
    fun getWeather(@Query("id") id: String, @Query("appid") appId: String): Call<WeatherResponse>
}