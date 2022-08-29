package com.example.navi.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.navi.data.api.GithubApiHelper
import com.example.navi.data.model.PullRequest
import com.example.navi.data.model.PullRequestState
import com.example.navi.data.model.RepoItem
import com.example.navi.data.paging.PullRequestPagingSource
import com.example.navi.data.paging.PullRequestPagingSource.Companion.NETWORK_PAGE_SIZE
import com.example.navi.data.paging.SearchRepoPagingSource

class GithubServicePagingRepository(private val githubApiHelper: GithubApiHelper) {

    fun getPullRequest(owner: String, repo: String, state: PullRequestState): LiveData<PagingData<PullRequest>> {
        return Pager(
            PagingConfig(NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = {
                PullRequestPagingSource(githubApiHelper, owner, repo, state)
            }).liveData
    }

    fun queryRepoList(query: String): LiveData<PagingData<RepoItem>> {
        return Pager(
            PagingConfig(NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = {
                SearchRepoPagingSource(githubApiHelper, query)
            }).liveData
    }
}