package com.example.navi.data.api

import com.example.navi.data.model.PullRequest

class GithubApiHelper {
    private val githubServiceApi = GithubApiClient.getClientInstace()

    suspend fun getOpenPullRequests(owner: String, repo: String): List<PullRequest> {
        return githubServiceApi.getPullResquest(owner, repo, "open")
    }

    suspend fun getClosedPullRequests(owner: String, repo: String): List<PullRequest> {
        return githubServiceApi.getPullResquest(owner, repo, "closed")
    }

    suspend fun getAllPullRequests(owner: String, repo: String): List<PullRequest> {
        return githubServiceApi.getPullResquest(owner, repo, "all")
    }
}