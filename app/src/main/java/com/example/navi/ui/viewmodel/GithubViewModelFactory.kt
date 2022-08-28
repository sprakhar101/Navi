package com.example.navi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.navi.data.repository.GithubServiceRepository

class GithubViewModelFactory(private val githubServiceRepository: GithubServiceRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GithubViewModel(githubServiceRepository) as T
    }
}