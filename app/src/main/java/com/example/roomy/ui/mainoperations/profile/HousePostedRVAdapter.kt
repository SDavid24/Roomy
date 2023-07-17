package com.example.roomy.ui.mainoperations.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.roomy.R
import com.example.roomy.databinding.ItemHousePostedBinding
import com.example.roomy.dataobject.Apartment

class HousePostedRVAdapter(private val context: Context,
                           private val housePostedList : ArrayList<Apartment>)
    : RecyclerView.Adapter<HousePostedRVAdapter.HousePostedViewHolder>() {

    class HousePostedViewHolder(binding: ItemHousePostedBinding) : RecyclerView.ViewHolder(binding.root) {
        var image = binding.apartmentImg
        var rentPrice = binding.tvRentPrice
        var location = binding.tvLocation
        var description = binding.tvDescription
        var furnishStatus = binding.tvFurnishStatus
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HousePostedViewHolder {
        val binding = ItemHousePostedBinding.inflate(
            LayoutInflater.from(parent.context), parent,
            false
        )

        return HousePostedViewHolder(binding)    }

    override fun onBindViewHolder(holder: HousePostedViewHolder, position: Int) {
        val model = housePostedList[position]

        Glide
            .with(context)
            .load(model.image)
            .centerCrop()
            .placeholder(R.drawable.apartment5)
            .into(holder.image)

        holder.rentPrice.text = "${"$" + model.rentPrice}"
        holder.location.text = "${model.area}, ${model.state}"
        holder.furnishStatus.text = model.furnishStatus
        holder.description.text = model.description

    }


    override fun getItemCount(): Int {
        return housePostedList.size
    }
}