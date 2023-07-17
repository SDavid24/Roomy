package com.example.roomy.ui.mainoperations.home.apartment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.roomy.R
import com.example.roomy.databinding.ItemApartmentImagesBinding

class ApartmentImagesAdapter(private var apartmentImages: ArrayList<Int>,
                             private val context: Context, private val id: Int )
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var currentIndex = 0

    inner class ApartmentImagesViewHolder(binding: ItemApartmentImagesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var image = binding.apartmentImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemApartmentImagesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ApartmentImagesViewHolder(binding)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = apartmentImages[position]

        if (holder is ApartmentImagesViewHolder) {
            Glide
                .with(context)
                .load(model)
                .centerCrop()
                .placeholder(R.drawable.alone_time)
                .into(holder.image)

            //Switch to the next image in line if it is tapped on
            holder.image.setOnClickListener {
                showNextImage(holder.image)
            }
        }

    }

    override fun getItemCount(): Int {
        return apartmentImages.size
    }

    //Method that shows the next image in line if it is tapped on
    private fun showNextImage(imageView: ImageView) {
        currentIndex++
        if (currentIndex >= apartmentImages.size) {

            Toast.makeText(context, "This is the last image of list size ${apartmentImages.size}", Toast.LENGTH_SHORT).show()

            currentIndex = 0 // Wrap around to the first image if reached the end of the list
        }//else
        val nextImageResource = apartmentImages[currentIndex]
        imageView.setImageResource(nextImageResource)
    }

    fun moveItem(fromPosition: Int, toPosition: Int) {
        val item = apartmentImages.removeAt(fromPosition)
        apartmentImages.add(toPosition, item)
        notifyItemMoved(fromPosition, toPosition)
    }

    fun currentDisplayed(){


    }
}
