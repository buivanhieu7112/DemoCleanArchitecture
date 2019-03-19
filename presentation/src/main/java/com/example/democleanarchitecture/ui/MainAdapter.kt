package com.example.democleanarchitecture.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.democleanarchitecture.R
import com.example.democleanarchitecture.model.RepoItem
import kotlinx.android.synthetic.main.adapter_user.view.*

class MainAdapter : ListAdapter<RepoItem, MainAdapter.UserViewHolder>(MainAdapter.UserDiffCallBack()) {
    class UserDiffCallBack : DiffUtil.ItemCallback<RepoItem>() {
        override fun areItemsTheSame(oldItem: RepoItem, newItem: RepoItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RepoItem, newItem: RepoItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.adapter_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(repoItem: RepoItem) {
            itemView.textViewUserName.text = repoItem.name
            Glide.with(itemView.imageViewAvatar.context).load(repoItem.avatar)
                .apply(RequestOptions().circleCrop().placeholder(R.drawable.ic_launcher_foreground))
                .into(itemView.imageViewAvatar)
        }
    }
}
