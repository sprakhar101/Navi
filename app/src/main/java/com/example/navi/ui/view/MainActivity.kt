package com.example.navi.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.navi.R
import com.example.navi.data.api.GithubApiHelper
import com.example.navi.data.repository.GithubServiceRepository
import com.example.navi.ui.viewmodel.GithubViewModel
import com.example.navi.ui.viewmodel.GithubViewModelFactory
import com.example.navi.utils.ResponseStatus

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
        val viewModelFactory = GithubViewModelFactory(GithubServiceRepository(GithubApiHelper()))
        cardsViewModel = ViewModelProvider(this, viewModelFactory)[GithubViewModel::class.java]
    }
}