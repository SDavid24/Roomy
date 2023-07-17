package com.example.roomy.utils

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.roomy.ui.mainoperations.home.apartment.ApartmentImagesAdapter

open class SwipeToNextItemCallback(private val adapter: ApartmentImagesAdapter) : ItemTouchHelper.Callback() {

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        // Enable swipe in both directions (start and end)
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(0, swipeFlags)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        // No action needed for move gestures
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        // Handle the swipe gesture here
        val currentPosition = viewHolder.adapterPosition
        val nextPosition = currentPosition + 1

        // Check if the next position is within the item count
        if (nextPosition < adapter.itemCount) {
            // Move the item to the next position
            adapter.moveItem(currentPosition, nextPosition)
        }
    }
}