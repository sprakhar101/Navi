package com.example.navi.data.model

import com.google.gson.annotations.SerializedName

data class Labels (
	@SerializedName("id") val id : String,
	@SerializedName("node_id") val node_id : String,
	@SerializedName("url") val url : String,
	@SerializedName("name") val name : String,
	@SerializedName("description") val description : String,
	@SerializedName("color") val color : String,
	@SerializedName("default") val default : Boolean
)