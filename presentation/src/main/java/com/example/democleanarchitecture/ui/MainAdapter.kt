package com.example.democleanarchitecture.ui

import android.util.Log
import android.view.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.democleanarchitecture.R
import com.example.democleanarchitecture.model.RepoItem
import com.example.democleanarchitecture.util.ItemCLickListener
import com.example.democleanarchitecture.util.ItemMenuClickListener
import kotlinx.android.synthetic.main.adapter_user.view.*

class MainAdapter(
    private val itemCLickListener: ItemCLickListener,
    private val itemMenuClickListener: ItemMenuClickListener
) : ListAdapter<RepoItem, MainAdapter.UserViewHolder>(MainAdapter.UserDiffCallBack()) {
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
        return UserViewHolder(view, itemMenuClickListener)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position), itemCLickListener)
    }

    class UserViewHolder(itemView: View, val itemMenuClickListener: ItemMenuClickListener) :
        RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener {
        private lateinit var user: RepoItem

        init {
            itemView.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(menu: ContextMenu, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            val save: MenuItem = menu.add(Menu.NONE, 1, 1, "Save")
            val cancel: MenuItem = menu.add(Menu.NONE, 2, 2, "Cancel")
            save.setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    Log.d("CONTEXT_MENU", "save")
                    itemMenuClickListener.onItemMenuClick(user)
                    return true
                }
            })
            cancel.setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    Log.d("CONTEXT_MENU", "cancel")
                    return true
                }
            })
        }

        fun bind(repoItem: RepoItem, itemCLickListener: ItemCLickListener) {
            user = repoItem
            itemView.textViewUserName.text = repoItem.name
            Glide.with(itemView.imageViewAvatar.context).load(repoItem.avatar)
                .apply(RequestOptions().circleCrop().placeholder(R.drawable.ic_launcher_foreground))
                .into(itemView.imageViewAvatar)

            itemView.setOnClickListener { itemCLickListener.onItemClicked(repoItem) }
        }
    }
}
