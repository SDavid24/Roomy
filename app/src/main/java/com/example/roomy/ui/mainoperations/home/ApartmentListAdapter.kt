package com.example.roomy.ui.mainoperations.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.roomy.R
import com.example.roomy.databinding.ItemApartmentListBinding
import com.example.roomy.dataobject.Apartment

class ApartmentListAdapter(private val apartmentList: ArrayList<Apartment>, private val context: Context)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ApartmentViewHolder(binding: ItemApartmentListBinding) : RecyclerView.ViewHolder(binding.root) {
        var image = binding.ivApartment
        var rentPrice = binding.tvRentPrice
        var location = binding.tvLocation
        var description = binding.tvDescription
        var furnishStatus = binding.tvFurnishStatus
        var btnContact = binding.btnContact
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
        : ApartmentViewHolder {
            // val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_pager, parent, false)

            val binding = ItemApartmentListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            return ApartmentViewHolder(binding)
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = apartmentList[position]

        if (holder is ApartmentViewHolder) {

            Glide
                .with(context)
                .load(model.image)
                .centerCrop()
                .placeholder(R.drawable.alone_time)
                .into(holder.image)

            holder.location.text = "${model.area}, ${model.state}"
            holder.description.text = model.description
            holder.furnishStatus.text = model.furnishStatus
            holder.rentPrice.text = model.rentPrice

            holder.btnContact.setOnClickListener {
                Toast.makeText(context, "Contact button clicked", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return apartmentList.size
    }
}