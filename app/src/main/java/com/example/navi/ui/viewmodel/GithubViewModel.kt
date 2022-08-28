package com.example.navi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.navi.data.repository.GithubServiceRepository
import com.example.navi.utils.ResponseResource
import kotlinx.coroutines.Dispatchers

class GithubViewModel(private val githubServiceRepository: GithubServiceRepository): ViewModel() {

    fun getOpenPullResquest(owner: String, repo: String) =
        liveData(Dispatchers.IO) {

        emit(ResponseResource.loading(data = null))
        try{
            emit(ResponseResource.success(data = githubServiceRepository.getOpenPullRequests(owner, repo)))
        } catch (exception: Exception){
            emit(ResponseResource.error(data = null, message = exception.message ?: "Error!"))
        }
    }

    fun getClosedPullResquest(owner: String, repo: String) =
        liveData(Dispatchers.IO) {

            emit(ResponseResource.loading(data = null))
            try{
                emit(ResponseResource.success(data = githubServiceRepository.getClosedPullRequests(owner, repo)))
            } catch (exception: Exception){
                emit(ResponseResource.error(data = null, message = exception.message ?: "Error!"))
            }
        }

    fun getAllPullResquest(owner: String, repo: String) =
        liveData(Dispatchers.IO) {

            emit(ResponseResource.loading(data = null))
            try{
                emit(ResponseResource.success(data = githubServiceRepository.getAllPullRequests(owner, repo)))
            } catch (exception: Exception){
                emit(ResponseResource.error(data = null, message = exception.message ?: "Error!"))
            }
        }
}