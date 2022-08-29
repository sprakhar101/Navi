package com.example.navi.data.model

import com.google.gson.annotations.SerializedName

data class RequestedTeams (
	@SerializedName("id") val id : String,
	@SerializedName("node_id") val node_id : String,
	@SerializedName("url") val url : String,
	@SerializedName("html_url") val html_url : String,
	@SerializedName("name") val name : String,
	@SerializedName("slug") val slug : String,
	@SerializedName("description") val description : String,
	@SerializedName("privacy") val privacy : String,
	@SerializedName("permission") val permission : String,
	@SerializedName("members_url") val members_url : String,
	@SerializedName("repositories_url") val repositories_url : String,
	@SerializedName("parent") val parent : String
)