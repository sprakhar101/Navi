package com.example.navi.ui.adapter

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.navi.R
import com.example.navi.data.model.PullRequest
import com.example.navi.data.model.RepoItem
import com.example.navi.utils.CustomTabHelper
import com.example.navi.utils.DateTimeUtils

class SearchRepoAdapter(private val itemClickListener: ItemClickListner, private val repoList: ArrayList<RepoItem> = ArrayList()): RecyclerView.Adapter<SearchRepoAdapter.SearchRepoViewHolder>() {

    interface ItemClickListner {
        fun onRepoItemClicked(repo: String, owner: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchRepoViewHolder {
        return SearchRepoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_search_repo_list_item, parent, false), itemClickListener)
    }

    override fun onBindViewHolder(holder: SearchRepoViewHolder, position: Int) {
        holder.bindData(repoList[position], position)
    }

    override fun getItemCount() = repoList.size

    fun add(list: List<RepoItem>) {
        repoList.addAll(list)
    }

    fun clear() {
        repoList.clear()
    }

    fun clearAndUpdate(list: List<RepoItem>) {
        repoList.clear()
        repoList.addAll(list)
    }

    class SearchRepoViewHolder(item: View, private val itemClickListener: ItemClickListner): RecyclerView.ViewHolder(item) {
        val title: TextView = item.findViewById(R.id.repo_title)
        val description: TextView = item.findViewById(R.id.repo_description)
        val userName: TextView = item.findViewById(R.id.user_name)
        val created: TextView = item.findViewById(R.id.date_created)
        val updated: TextView = item.findViewById(R.id.date_updated)
        val starred: TextView = item.findViewById(R.id.starred)
        val forked: TextView = item.findViewById(R.id.forked)
        val avatar: ImageView = item.findViewById(R.id.user_avatar)

        fun bindData(item: RepoItem, position: Int) {
            title.text = item.full_name
            if(!TextUtils.isEmpty(item.description)) description.text = item.description
            else description.visibility = View.GONE
            userName.text = item.owner.login
            created.text = "created:${DateTimeUtils.getData(item.created_at)}"
            updated.text = "last updated:${DateTimeUtils.getData(item.updated_at)}"
            starred.text = "${item.stargazers_count}"
            forked.text = "${item.forks_count}"
            Glide.with(itemView.context).load(item.owner.avatar_url).into(avatar)
            itemView.setOnClickListener {
                itemClickListener.onRepoItemClicked(item.name, item.owner.login)
            }
        }
    }
}