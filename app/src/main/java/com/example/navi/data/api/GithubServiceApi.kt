package com.example.navi.data.api

import com.example.navi.data.model.PullRequest
import com.example.navi.data.model.RepoSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubServiceApi {
    @GET("repos/{owner}/{repo}/pulls")
    suspend fun getPullResquest(@Path("owner") owner: String, @Path("repo") repo: String, @Query("state") state: String = "open"): List<PullRequest>

    @GET("search/repositories")
    suspend fun queryRepoList(@Query("q") query: String): RepoSearchResponse
}