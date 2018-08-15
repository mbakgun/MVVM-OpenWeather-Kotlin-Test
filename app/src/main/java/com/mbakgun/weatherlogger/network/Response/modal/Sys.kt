package com.mbakgun.weatherlogger.network.Response.modal

import com.google.gson.annotations.SerializedName


data class Sys(

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("sunrise")
	val sunrise: Int? = null,

	@field:SerializedName("sunset")
	val sunset: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("type")
	val type: Int? = null,

	@field:SerializedName("message")
	val message: Double? = null
)