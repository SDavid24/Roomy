package com.example.roomy.ui.mainoperations.messaging

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class ChatHeaderAdapter : RecyclerView.Adapter<ChatHeaderAdapter.HeaderViewHolder>() {

    private var headerView: View? = null

    fun setHeaderView(view: View) {
        headerView = view
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        return HeaderViewHolder(headerView!!)
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        // Nothing to bind here as the header is already set in the constructor
    }

    override fun getItemCount(): Int {
        // Return 1 if the header view is not null, 0 otherwise
        return if (headerView != null) 1 else 0
    }
}

