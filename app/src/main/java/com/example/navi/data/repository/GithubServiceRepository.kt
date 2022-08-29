package com.example.navi.data.repository

import com.example.navi.data.api.GithubApiHelper
import com.example.navi.data.model.PullRequest
import com.example.navi.data.model.PullRequestState
import com.example.navi.data.model.RepoSearchResponse

class GithubServiceRepository(private val apiHelper: GithubApiHelper) {

    suspend fun getPullRequests(owner: String, repo: String, state: PullRequestState): List<PullRequest> {
        return apiHelper.getPullRequests(owner, repo, state)
    }

    suspend fun queryRepoList(query: String): RepoSearchResponse {
        return apiHelper.queryRepoList(query)
    }

}