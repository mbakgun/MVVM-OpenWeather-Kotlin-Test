package com.mbakgun.weatherlogger.model

import java.util.*

data class WeatherData(val name: String = "",
                       val country: String = "",
                       val description: String = "",
                       val temp: Double = 0.0,
                       val dt: String = "",
                       val dateCreated: Date = Date())