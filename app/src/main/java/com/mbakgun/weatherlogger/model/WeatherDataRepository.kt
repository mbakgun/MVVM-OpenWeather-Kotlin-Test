package com.mbakgun.weatherlogger.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import java.util.*

class WeatherDataRepository {

    private val savedWeatherData = mutableMapOf<Date, WeatherData>()
    val liveData = MutableLiveData<List<WeatherData>>()

    fun saveWeatherData(tc: WeatherData) {
        savedWeatherData[tc.dateCreated] = tc
        liveData.value = savedWeatherData.values.toList()
    }

    fun clearWeatherData() {
        savedWeatherData.clear()
        liveData.value = savedWeatherData.values.toList()
    }

    fun loadWeatherDataByDt(createDate: Date): WeatherData? {
        return savedWeatherData[createDate]
    }

    fun loadWeatherData(): LiveData<List<WeatherData>> {
        liveData.value = savedWeatherData.values.toList()
        return liveData
    }

    fun mergeLocalDataList(dataList: List<WeatherData>) {
        dataList.forEach { weatherData ->
            savedWeatherData[weatherData.dateCreated] = weatherData
        }
        liveData.value = savedWeatherData.values.toList()
    }
}