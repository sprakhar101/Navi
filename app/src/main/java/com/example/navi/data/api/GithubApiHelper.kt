package com.example.navi.data.api

import com.example.navi.data.model.PullRequest
import com.example.navi.data.model.PullRequestState
import com.example.navi.data.model.RepoSearchResponse

class GithubApiHelper {
    private val githubServiceApi = GithubApiClient.getClientInstace()

    suspend fun getPullRequests(owner: String, repo: String, state: PullRequestState): List<PullRequest> {
        return githubServiceApi.getPullResquest(owner, repo, state.state)
    }

    suspend fun queryRepoList(query: String): RepoSearchResponse {
        return githubServiceApi.queryRepoList(query)
    }
}