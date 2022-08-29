package com.example.navi.ui.adapter

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.navi.R
import com.example.navi.data.model.PullRequest
import com.example.navi.utils.CustomTabHelper
import com.example.navi.utils.DateTimeUtils

class PullRequestPagingAdapter: PagingDataAdapter<PullRequest, PullRequestPagingAdapter.PullRequestViewHolder>(DiffUtilCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PullRequestViewHolder {
        return PullRequestViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_item_pull_request_list, parent, false))
    }

    override fun onBindViewHolder(holder: PullRequestViewHolder, position: Int) {
        getItem(position)?.let { holder.bindData(it, position) }
    }

    inner class PullRequestViewHolder(item: View): RecyclerView.ViewHolder(item) {
        val title: TextView = item.findViewById(R.id.pr_title)
        val userName: TextView = item.findViewById(R.id.user_name)
        val created: TextView = item.findViewById(R.id.date_created)
        val closed: TextView = item.findViewById(R.id.date_closed)
        val updated: TextView = item.findViewById(R.id.date_updated)
        val status: TextView = item.findViewById(R.id.status)
        val avatar: ImageView = item.findViewById(R.id.user_avatar)

        fun bindData(item: PullRequest, position: Int) {
            title.text = item.title
            userName.text = item.user.login
            created.text = "created:${DateTimeUtils.getData(item.created_at)}"
            updated.text = "last updated:${DateTimeUtils.getData(item.updated_at)}"
            if(!TextUtils.isEmpty(item.closed_at)) closed.text = "closed:${DateTimeUtils.getData(item.closed_at)}"
            status.text = "State: ${item.state}"
            Glide.with(itemView.context).load(item.user.avatar_url).into(avatar)
            itemView.setOnClickListener {
                CustomTabHelper.launchUrl(itemView.context, item.html_url)
            }
        }
    }

    object DiffUtilCallBack : DiffUtil.ItemCallback<PullRequest>() {
        override fun areItemsTheSame(oldItem: PullRequest, newItem: PullRequest): Boolean {
            return oldItem.html_url == newItem.html_url
        }

        override fun areContentsTheSame(oldItem: PullRequest, newItem: PullRequest): Boolean {
            return oldItem == newItem
        }
    }
}