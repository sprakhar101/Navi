package com.example.navi.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.navi.data.api.GithubApiHelper
import com.example.navi.data.model.RepoItem

class SearchRepoPagingSource(private val githubApiHelper: GithubApiHelper, private val query: String): PagingSource<Int, RepoItem>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
        const val NETWORK_PAGE_SIZE = 30
    }

    override fun getRefreshKey(state: PagingState<Int, RepoItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?:
            state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepoItem> {
        val pageIndex = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = githubApiHelper.queryRepoList(query, pageIndex)
            val repoList = response.items
            val nextKey = if(repoList.isNullOrEmpty()) {
                null
            } else {
                pageIndex + 1
            }
            Log.d("NaviAssgn", "loadPaging: $pageIndex, size=${repoList.size}, loadSize=${params.loadSize}, nextkey=$nextKey")
            LoadResult.Page(repoList, null, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}