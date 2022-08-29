package com.example.navi.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.navi.data.api.GithubApiHelper
import com.example.navi.data.model.PullRequest
import com.example.navi.data.model.PullRequestState

class PullRequestPagingSource(private val githubApiHelper: GithubApiHelper,
                              private val owner: String,
                              private val repo: String,
                              private val state: PullRequestState):
    PagingSource<Int, PullRequest>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
        const val NETWORK_PAGE_SIZE = 30
    }

    override fun getRefreshKey(state: PagingState<Int, PullRequest>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?:
            state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PullRequest> {
        val pageIndex = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = githubApiHelper.getPullRequests(owner, repo, state, pageIndex)
            val nextKey = if(response.isNullOrEmpty()) {
                null
            } else {
                pageIndex + 1
            }
            Log.d("NaviAssgn", "loadPaging: $pageIndex, size=${response.size}, loadSize=${params.loadSize}, nextkey=$nextKey")
            LoadResult.Page(response, null, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}