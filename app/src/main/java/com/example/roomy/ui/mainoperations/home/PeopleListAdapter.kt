package com.example.roomy.ui.mainoperations.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.roomy.R
import com.example.roomy.databinding.ItemPeopleRecyclerviewBinding
import com.example.roomy.dataobject.User
import com.example.roomy.dataobject.UserTest

class PeopleListAdapter(private val peopleList: ArrayList<UserTest>, private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class PeopleViewHolder(binding: ItemPeopleRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        var image = binding.ivImage
        var tvname = binding.tvNameAge
        var location = binding.tvLocation
        var occupation = binding.tvOccupation
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): PeopleViewHolder {
        // val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_pager, parent, false)

        val binding = ItemPeopleRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PeopleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return peopleList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = peopleList[position]

        if (holder is PeopleViewHolder) {

            Glide
                .with(context)
                .load(model.image)
                .centerCrop()
                .placeholder(R.drawable.alone_time)
                .into(holder.image)

            holder.tvname.text = "${model.name}, ${model.age}"
            holder.location.text = "lives in ${model.area}, ${model.state}"
            holder.occupation.text = model.occupation

        }
    }

}
