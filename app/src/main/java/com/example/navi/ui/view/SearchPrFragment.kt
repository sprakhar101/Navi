package com.example.navi.ui.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.navi.R
import com.example.navi.data.model.PullRequest
import com.example.navi.data.model.RepoItem
import com.example.navi.ui.adapter.SearchRepoAdapter
import com.example.navi.ui.viewmodel.GithubViewModel
import com.example.navi.utils.ResponseStatus

class SearchPrFragment: Fragment(), SearchRepoAdapter.ItemClickListner {

    private lateinit var cardsViewModel: GithubViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchRepoAdapter
    private lateinit var retryButton: Button
    private lateinit var searchView: EditText
    private lateinit var stateSpinner: Spinner
    private lateinit var progressBar: ProgressBar

    private val handler = Handler(Looper.getMainLooper())
    private val searchRunnable = Runnable {
        val query = searchView.text.toString()
        if(!TextUtils.isEmpty(query)) querySearch(query)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.layout_search_pr_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.repo_list_view)
        retryButton = view.findViewById(R.id.retry_button)
        progressBar = view.findViewById(R.id.progress_bar)
        searchView = view.findViewById(R.id.search_view)
        stateSpinner = view.findViewById(R.id.state_choice_spinner)
        retryButton.setOnClickListener {
            if(!TextUtils.isEmpty(searchView.text)) {
                handler.removeCallbacks(searchRunnable)
                handler.postDelayed(searchRunnable, 500)
            }
            else updatePrList(emptyList())
        }

        initRecyclerView()
        initViewModel()
        initSearchView()
        initSpinner()
    }

    override fun onRepoItemClicked(repo: String, owner: String) {
        popBackStack(repo, owner)
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity)
        adapter = SearchRepoAdapter(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    private fun initViewModel() {
        cardsViewModel = ViewModelProvider(requireActivity())[GithubViewModel::class.java]
    }

    private fun initSearchView() {
        searchView.addTextChangedListener {
            if(!TextUtils.isEmpty(searchView.text)) {
                handler.removeCallbacks(searchRunnable)
                handler.postDelayed(searchRunnable, 500)
            }
            else updatePrList(emptyList())
        }
    }

    private fun querySearch(query: String) {
        Log.d("NaviAssgn", "querySearch: $query")
        cardsViewModel.queryRepoList(query).observe(viewLifecycleOwner, Observer {
            when(it.status) {
                ResponseStatus.LOADING -> {
                    handleLoading()
                }
                ResponseStatus.SUCCESS -> {
                    handlSuccess()
                    it.data?.let { response ->
                        updatePrList(response.items)
                    }
                }
                ResponseStatus.FAILURE -> {
                    handleError()
                    Toast.makeText(activity, "Error! Connect to Internet", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun initSpinner() {
        ArrayAdapter.createFromResource(requireContext(), R.array.pull_request_states, R.layout.layout_spinner).also {
            it.setDropDownViewResource(R.layout.layout_spinner_item)
            stateSpinner.adapter = it
        }
    }

    private fun popBackStack(repo: String, owner: String) {
        val currState = stateSpinner.selectedItem as String
        val fragmentManager = parentFragmentManager
        val bundle = Bundle()
        bundle.putString("owner", owner)
        bundle.putString("repo", repo)
        bundle.putString("state", currState)
        fragmentManager.setFragmentResult("searchForRepo", bundle)
        fragmentManager.popBackStack()
    }

    private fun updatePrList(repoList: List<RepoItem>) { //TODO
        adapter.clearAndUpdate(repoList)
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
}