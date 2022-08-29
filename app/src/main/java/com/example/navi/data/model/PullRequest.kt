package com.example.navi.data.model

import com.google.gson.annotations.SerializedName

data class PullRequest (
	@SerializedName("url") val url : String,
	@SerializedName("id") val id : String,
	@SerializedName("node_id") val node_id : String,
	@SerializedName("html_url") val html_url : String,
	@SerializedName("diff_url") val diff_url : String,
	@SerializedName("patch_url") val patch_url : String,
	@SerializedName("issue_url") val issue_url : String,
	@SerializedName("commits_url") val commits_url : String,
	@SerializedName("review_comments_url") val review_comments_url : String,
	@SerializedName("review_comment_url") val review_comment_url : String,
	@SerializedName("comments_url") val comments_url : String,
	@SerializedName("statuses_url") val statuses_url : String,
	@SerializedName("number") val number : Int,
	@SerializedName("state") val state : String,
	@SerializedName("locked") val locked : Boolean,
	@SerializedName("title") val title : String,
	@SerializedName("user") val user : User,
	@SerializedName("body") val body : String,
	@SerializedName("labels") val labels : List<Labels>,
	@SerializedName("milestone") val milestone : Milestone,
	@SerializedName("active_lock_reason") val active_lock_reason : String,
	@SerializedName("created_at") val created_at : String,
	@SerializedName("updated_at") val updated_at : String,
	@SerializedName("closed_at") val closed_at : String,
	@SerializedName("merged_at") val merged_at : String,
	@SerializedName("merge_commit_sha") val merge_commit_sha : String,
	@SerializedName("assignee") val assignee : Assignee,
	@SerializedName("assignees") val assignees : List<Assignees>,
	@SerializedName("requested_reviewers") val requested_reviewers : List<RequestedReviewers>,
	@SerializedName("requested_teams") val requested_teams : List<RequestedTeams>,
	@SerializedName("head") val head : Head,
	@SerializedName("base") val base : Base,
	@SerializedName("_links") val _links : _links,
	@SerializedName("author_association") val author_association : String,
	@SerializedName("auto_merge") val auto_merge : String,
	@SerializedName("draft") val draft : Boolean
)