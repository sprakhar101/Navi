package com.example.navi.data.model

import com.google.gson.annotations.SerializedName

data class Milestone (
	@SerializedName("url") val url : String,
	@SerializedName("html_url") val html_url : String,
	@SerializedName("labels_url") val labels_url : String,
	@SerializedName("id") val id : Int,
	@SerializedName("node_id") val node_id : String,
	@SerializedName("number") val number : Int,
	@SerializedName("state") val state : String,
	@SerializedName("title") val title : String,
	@SerializedName("description") val description : String,
	@SerializedName("creator") val creator : Creator,
	@SerializedName("open_issues") val open_issues : Int,
	@SerializedName("closed_issues") val closed_issues : Int,
	@SerializedName("created_at") val created_at : String,
	@SerializedName("updated_at") val updated_at : String,
	@SerializedName("closed_at") val closed_at : String,
	@SerializedName("due_on") val due_on : String
)