package com.example.navi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.navi.data.model.PullRequestState
import com.example.navi.data.model.RepoSearchResponse
import com.example.navi.data.repository.GithubServiceRepository
import com.example.navi.utils.ResponseResource
import kotlinx.coroutines.Dispatchers

class GithubViewModel(private val githubServiceRepository: GithubServiceRepository): ViewModel() {

    fun getPullResquest(owner: String, repo: String, state: PullRequestState) = liveData(Dispatchers.IO) {
        emit(ResponseResource.loading(data = null))
        try{
            emit(ResponseResource.success(data = githubServiceRepository.getPullRequests(owner, repo, state)))
        } catch (exception: Exception){
            emit(ResponseResource.error(data = null, message = exception.message ?: "Error!"))
        }
    }

    fun queryRepoList(query: String) = liveData(Dispatchers.IO) {
        emit(ResponseResource.loading(data = null))
        try{
            emit(ResponseResource.success(data = githubServiceRepository.queryRepoList(query)))
        } catch (exception: Exception){
            emit(ResponseResource.error(data = null, message = exception.message ?: "Error!"))
        }
    }
}