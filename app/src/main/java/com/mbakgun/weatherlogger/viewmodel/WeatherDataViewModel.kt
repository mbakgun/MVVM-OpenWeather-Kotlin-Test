package com.mbakgun.weatherlogger.viewmodel

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import com.mbakgun.weatherlogger.model.WeatherData
import com.mbakgun.weatherlogger.model.WeatherDataProcessor
import com.mbakgun.weatherlogger.utils.DBHelper

class WeatherDataViewModel @JvmOverloads constructor(app: Application, val weatherDataProcessor: WeatherDataProcessor = WeatherDataProcessor()) : ObservableViewModel(app), WeatherDataProcessor.OnWeatherDataListener {
    var lastWeatherData = WeatherData()
    var weatherDataListener: WeatherDataProcessor.OnWeatherDataListener? = null
    val db by lazy { DBHelper(app) }

    init {
        weatherDataListener = this
        weatherDataProcessor.getWeatherData(this)
    }

    override fun onSuccess(weatherData: WeatherData) {
        weatherDataProcessor.saveWeatherData(weatherData)
        updateOutputs(weatherData)
        db.insertData(weatherData)
    }

    fun updateOutputs(wd: WeatherData) {
        lastWeatherData = wd
        notifyChange()
    }

    fun loadSavedWeatherDataSummaries(): LiveData<List<WeatherDataSummaryItem>> {
        weatherDataProcessor.mergeLocalDataList(db.readData())
        return Transformations.map(weatherDataProcessor.loadWeatherData()) { weatherDataObjects ->
            weatherDataObjects.map {
                WeatherDataSummaryItem(it.temp,
                        it.dateCreated)
            }
        }
    }
}