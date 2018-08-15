package com.mbakgun.weatherlogger.model

import android.arch.lifecycle.LiveData
import android.util.Log
import com.mbakgun.weatherlogger.network.NetworkRepository
import com.mbakgun.weatherlogger.network.Response.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class WeatherDataProcessor(val repository: WeatherDataRepository = WeatherDataRepository(), val networkRepository: NetworkRepository = NetworkRepository()) {

    interface OnWeatherDataListener {
        fun onSuccess(weatherData: WeatherData)
    }

    fun saveWeatherData(tc: WeatherData) {
        repository.saveWeatherData(tc)
    }

    fun clearWeatherData() {
        repository.clearWeatherData()
    }

    fun mergeLocalDataList(dataList: List<WeatherData>) {
        repository.mergeLocalDataList(dataList)
    }

    fun loadWeatherDataByDt(createDate: Date): WeatherData? {
        return repository.loadWeatherDataByDt(createDate)
    }

    fun loadWeatherData(): LiveData<List<WeatherData>> {
        return repository.loadWeatherData()
    }

    fun getWeatherData(onWeatherDataListener: OnWeatherDataListener) {
        val call = networkRepository.weatherService.getWeather(networkRepository.id, networkRepository.appId)
        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>?, response: Response<WeatherResponse>?) {
                if (response != null && response.isSuccessful) {
                    val weatherItem = response.body()
                    onWeatherDataListener.onSuccess(WeatherData(weatherItem?.name!!, weatherItem.sys?.country!!, weatherItem.weather?.get(0)?.description!!, weatherItem.main?.temp!!, weatherItem.dt.toString()))
                } else {
                    onFailure(call, Throwable("Bad Response"))
                }
            }

            override fun onFailure(call: Call<WeatherResponse>?, t: Throwable?) {
                Log.e("Response Failure", t?.message, t)
            }
        })
    }
}