package com.example.navi.data.api

import com.example.navi.data.model.PullRequest
import com.example.navi.data.model.PullRequestState
import com.example.navi.data.model.RepoSearchResponse

class GithubApiHelper {
    private val githubServiceApi = GithubApiClient.getClientInstace()

    suspend fun getPullRequests(owner: String, repo: String, state: PullRequestState, pageIndex: Int = 0): List<PullRequest> {
        return githubServiceApi.getPullResquest(owner, repo, state.state, pageIndex)
    }

    suspend fun queryRepoList(query: String, padeIndex: Int = 0): RepoSearchResponse {
        return githubServiceApi.queryRepoList(query, padeIndex)
    }
}