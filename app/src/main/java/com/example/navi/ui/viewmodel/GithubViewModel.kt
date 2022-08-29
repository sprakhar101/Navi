package com.example.navi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.navi.data.model.PullRequestState
import com.example.navi.data.repository.GithubServicePagingRepository

class GithubViewModel(private val githubServiceRepository: GithubServicePagingRepository): ViewModel() {

    fun getPullResquest(owner: String, repo: String, state: PullRequestState) = githubServiceRepository.getPullRequest(owner, repo, state)
        .cachedIn(viewModelScope)

    fun queryRepoList(query: String) = githubServiceRepository.queryRepoList(query).cachedIn(viewModelScope)
}