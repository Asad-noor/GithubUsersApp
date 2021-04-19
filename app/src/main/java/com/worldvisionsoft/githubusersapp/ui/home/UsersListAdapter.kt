package com.worldvisionsoft.githubusersapp.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.worldvisionsoft.githubusersapp.R
import com.worldvisionsoft.githubusersapp.data.model.User

class UsersListAdapter(
    private val dataSet: List<User>,
    private val onItemClicked: (User) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM_VIEW_TYPE = 1
    private val LOADER_VIEW_TYPE = 2
    private lateinit var context: Context

    /**
     * Provide a reference to the type of views that you are using
     */
    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvUserName: TextView
        val tvUserType: TextView
        val tvSiteAdmin: TextView
        val acibNote: AppCompatImageButton
        val acivProfilePic: AppCompatImageView

        init {
            // Define click listener for the ViewHolder's View.
            tvUserName = view.findViewById(R.id.tvUserName)
            tvUserType = view.findViewById(R.id.tvUserType)
            tvSiteAdmin = view.findViewById(R.id.tvSiteAdmin)
            acivProfilePic = view.findViewById(R.id.acivProfilePic)
            acibNote = view.findViewById(R.id.acibNote)
        }
    }

    class LoaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        if (viewType == ITEM_VIEW_TYPE)
            return ItemViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_users_list, parent, false)
            )
        else
            return LoaderViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list_loader, parent, false)
            )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            val item = dataSet.get(position)
            holder.tvUserName.text = item.login
            holder.tvUserType.text = context.resources.getString(R.string.string_user_type, item.type)

            val siteAdmin = if (item.siteAdmin ?: false) "Yes" else "No"
            holder.tvSiteAdmin.text = context.resources.getString(R.string.string_site_admin, siteAdmin)

            if (item.note != null) {
                holder.acibNote.visibility = View.VISIBLE
            } else {
                holder.acibNote.visibility = View.GONE
            }

            Glide.with(context)
                .load(item.avatarUrl)
                .into(holder.acivProfilePic)

            holder.itemView.setOnClickListener {
                onItemClicked(item)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == dataSet.size)
            return LOADER_VIEW_TYPE
        else
            return ITEM_VIEW_TYPE
    }

    override fun getItemCount(): Int {
        return dataSet.size + 1
    }
}