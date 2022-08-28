package com.example.navi.data.repository

import com.example.navi.data.api.GithubApiHelper
import com.example.navi.data.model.PullRequest

class GithubServiceRepository(private val apiHelper: GithubApiHelper) {

    suspend fun getOpenPullRequests(owner: String, repo: String): List<PullRequest> {
        return apiHelper.getOpenPullRequests(owner, repo)
    }

    suspend fun getClosedPullRequests(owner: String, repo: String): List<PullRequest> {
        return apiHelper.getClosedPullRequests(owner, repo)
    }

    suspend fun getAllPullRequests(owner: String, repo: String): List<PullRequest> {
        return apiHelper.getAllPullRequests(owner, repo)
    }

}