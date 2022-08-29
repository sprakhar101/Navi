package com.example.navi.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.navi.R
import com.example.navi.data.api.GithubApiHelper
import com.example.navi.data.repository.GithubServicePagingRepository
import com.example.navi.ui.viewmodel.GithubViewModel
import com.example.navi.ui.viewmodel.GithubViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var cardsViewModel: GithubViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModel()

        val pullRequestFragment = PullRequestFragment()
        supportFragmentManager.beginTransaction().add(R.id.fragment_container, pullRequestFragment).commit()
    }

    private fun initViewModel() {
        val viewModelFactory = GithubViewModelFactory(GithubServicePagingRepository(GithubApiHelper()))
        cardsViewModel = ViewModelProvider(this, viewModelFactory)[GithubViewModel::class.java]
    }
}