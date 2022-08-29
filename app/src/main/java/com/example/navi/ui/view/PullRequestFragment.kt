package com.example.navi.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.navi.R
import com.example.navi.data.model.PullRequest
import com.example.navi.data.model.PullRequestState
import com.example.navi.ui.adapter.PullRequestAdapter
import com.example.navi.ui.viewmodel.GithubViewModel
import com.example.navi.utils.ResponseStatus

class PullRequestFragment: Fragment() {

    private lateinit var cardsViewModel: GithubViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PullRequestAdapter
    private lateinit var retryButton: Button
    private lateinit var searchView: View
    private lateinit var titleView: TextView
    private lateinit var progressBar: ProgressBar

    private var currState = PullRequestState.CLOSED
    private var currentOwner = "google"
    private var currentRepo = "ExoPlayer"
    private val prList = arrayListOf<PullRequest>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFragmentResultListner()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.layout_pull_request_fragment, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.pull_request_list_view)
        retryButton = view.findViewById(R.id.retry_button)
        progressBar = view.findViewById(R.id.progress_bar)
        searchView = view.findViewById(R.id.search_view)
        titleView = view.findViewById(R.id.title_view)
        updateTitle()
        searchView.setOnClickListener {
            showSearchFragment()
        }

        retryButton.setOnClickListener {
            initViewModel()
        }
        initFragment()
    }

    private fun showSearchFragment() {
        val searchPrFragment = SearchPrFragment()
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container, searchPrFragment).addToBackStack(null).commit()
    }

    private fun initFragment() {
        initRecylcerView()
        initViewModel()
    }

    private fun initRecylcerView() {
        val layoutManager = LinearLayoutManager(activity)
        adapter = PullRequestAdapter(prList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    private fun initViewModel() {
        cardsViewModel = ViewModelProvider(requireActivity())[GithubViewModel::class.java]
        if(prList.isNullOrEmpty()) queryPullRequest()
    }

    private fun updatePrList(cards: List<PullRequest>) { //TODO
        prList.clear()
        prList.addAll(cards)
        adapter.clearAndUpdate(cards)
        adapter.notifyDataSetChanged()
    }

    private fun handleLoading() {
        recyclerView.visibility = View.GONE
        retryButton.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    private fun handlSuccess() {
        recyclerView.visibility = View.VISIBLE
        retryButton.visibility = View.GONE
        progressBar.visibility = View.GONE
    }

    private fun handleError() {
        recyclerView.visibility = View.GONE
        retryButton.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    private fun initFragmentResultListner() {
        val fragmentManager = parentFragmentManager
        fragmentManager.setFragmentResultListener("searchForRepo", this, FragmentResultListener { requestKey, result ->
            Log.d("NaviAssgn", "initFragmentResultListner: Result revceived in pr fragment${result.getString("owner")} ${result.getString("repo")} ${result.getString("state")}")
            onSelectedNewQuery(result)
//            Toast.makeText(activity, "Result ${result.getString("owner")} ${result.getString("repo")} ${result.getString("state")}", Toast.LENGTH_SHORT).show()
        })
    }

    private fun updateTitle() {
        titleView.text = "Showing ${currState.state} pull request for $currentOwner/$currentRepo"
    }

    private fun queryPullRequest() {
        cardsViewModel.getPullResquest(currentOwner, currentRepo, currState).observe(viewLifecycleOwner, Observer {
            when(it.status) {
                ResponseStatus.LOADING -> {
                    handleLoading()
                }
                ResponseStatus.SUCCESS -> {
                    handlSuccess()
                    it.data?.let { prs ->
                        updatePrList(prs)
                    }
                }
                ResponseStatus.FAILURE -> {
                    handleError()
                    Log.d("NaviAssgn", "queryPullRequest: ${it.message}")
                    Toast.makeText(activity, "Error! Connect to Internet", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun onSelectedNewQuery(bundle: Bundle) {
        val newOwner = bundle.getString("owner", "")
        val newRepo = bundle.getString("repo", "")
        val newState = PullRequestState.valueOf(bundle.getString("state", "").uppercase())
        if(newState != currState || newRepo != currentRepo && newOwner != currentOwner) {
            currentRepo = newRepo
            currentOwner = newOwner
            currState = newState
            updatePrList(emptyList())
            updateTitle()
            queryPullRequest()
        }

    }
}