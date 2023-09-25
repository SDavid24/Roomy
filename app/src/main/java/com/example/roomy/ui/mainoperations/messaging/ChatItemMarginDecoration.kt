package com.example.roomy.ui.mainoperations.messaging

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ChatItemMarginDecoration(
    private val receivedMessageMarginTop: Int,
    private val sentMessageMarginTopWithReceived: Int,
    private val sentMessageMarginTopWithSent: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State) {

        val itemPosition = parent.getChildAdapterPosition(view)

        if (itemPosition == RecyclerView.NO_POSITION) {
            return
        }

        // Check the item type of the previous view
        val previousItemType = if (itemPosition > 0) {
            parent.adapter?.getItemViewType(itemPosition - 1) ?: -1
        } else {
            -1
        }

        // Set margin top based on the previous item's type
        val marginTop: Int = when (val currentItemViewType = parent.adapter?.getItemViewType(itemPosition)) {
            MessageType.SENT -> if (previousItemType == MessageType.RECEIVED) {
                sentMessageMarginTopWithReceived
            } else {
                sentMessageMarginTopWithSent
            }
            MessageType.RECEIVED -> receivedMessageMarginTop
            else -> 0
        }

        outRect.top = marginTop
    }
}


object MessageType {
    const val RECEIVED = 0
    const val SENT = 1
    // Add more message types if needed
}