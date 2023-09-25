package com.example.roomy.ui.mainoperations.messaging

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.roomy.R
import com.example.roomy.databinding.ItemReceivedMessageBinding
import com.example.roomy.databinding.ItemSentMessageBinding
import com.example.roomy.dataobject.Chat

@RequiresApi(Build.VERSION_CODES.Q)
class ChatAdapter (private val chatList: MutableList<Chat>, private val context: Context, private  val friendsProfileImage: Int )
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val MESSAGE_SENT = 1
    private val MESSAGE_RECEIVED = 2
    private var data: List<Chat> = emptyList()

    inner class SentViewHolder(binding: ItemSentMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        var sentMessage = binding.sentText

        init {
            // Set background drawable with desired corner radius for Sent messages
            //sentMessage.background = setSentDrawableWithCornerRadius(floatArrayOfSentBackgroundRadius())
            sentMessage.background = setDrawableWithCornerRadius(R.drawable.bg_sent_text)
        }

    }

    inner class ReceivedViewHolder(binding: ItemReceivedMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        var receivedMessage = binding.receivedText
        var receivedImage = binding.receivedImage
        init {
            // Set background drawable with desired corner radius for Sent messages
            receivedMessage.background = setDrawableWithCornerRadius(R.drawable.bg_received_text)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == 1){
            val binding = ItemSentMessageBinding.inflate(LayoutInflater.from(context), parent, false)

            SentViewHolder(binding)

        }else{
            val binding = ItemReceivedMessageBinding.inflate(LayoutInflater.from(context), parent, false)
            ReceivedViewHolder(binding)

        }
    }


    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = chatList[position]
        val layoutParams = holder.itemView.layoutParams as RecyclerView.LayoutParams

        val sentText = holder.itemView.findViewById<TextView>(R.id.sent_text)
        val receivedText = holder.itemView.findViewById<TextView>(R.id.received_text)

        //Get what type the next message on the list is
        val nextMessageType : Int = if(position < chatList.size -1){
            getItemViewType(position + 1)
        }else{
            getItemViewType(chatList.size -1 )
        }


        val previousMessageType = if (position > 0) getItemViewType(position - 1) else -1

        val marginTop =
            if (previousMessageType == MESSAGE_SENT && holder is SentViewHolder) {
                context.resources.getDimensionPixelSize(R.dimen.sent_message_margin_top_with_sent)
            }

            else if (previousMessageType == MESSAGE_RECEIVED && holder is ReceivedViewHolder) {
                context.resources.getDimensionPixelSize(R.dimen.sent_message_margin_top_with_sent)
            }
            else {
                context.resources.getDimensionPixelSize(R.dimen.received_message_margin_top_with_sent)
            }

        layoutParams.topMargin = marginTop //Set the margin
        holder.itemView.layoutParams = layoutParams  //reinitialise the layout parameters

        when (holder.javaClass) {
            SentViewHolder::class.java -> {
                var  viewHolder = holder as SentViewHolder
                holder.sentMessage.text = currentMessage.message

                // Set background drawable with desired corner radius for Sent messages
                if (nextMessageType != MESSAGE_SENT || position == chatList.size -1) {
                    sentText.background = setDrawableWithCornerRadius(R.drawable.bg_sent_text)
                } else {
                    sentText.background = setDrawableWithCornerRadius(R.drawable.bg_sent_text_round)
                }

            }
            ReceivedViewHolder::class.java -> {
                var  viewHolder = holder as ReceivedViewHolder

                holder.receivedMessage.text = currentMessage.message

                Glide
                    .with(context)
                    .load(friendsProfileImage)
                    .centerCrop()
                    .placeholder(R.drawable.alone_time)
                    .into(holder.receivedImage)

                // Set background drawable with desired corner radius for Received messages
                if (nextMessageType != MESSAGE_RECEIVED || position == chatList.size -1) {
                    receivedText.background = setDrawableWithCornerRadius(R.drawable.bg_received_text)
                } else {
                    receivedText.background = setDrawableWithCornerRadius(R.drawable.bg_received_text_round)
                }
            }
        }
    }


    //Check if the sender of the message is the user himself or another
    override fun getItemViewType(position: Int): Int {
        val currentMessage = chatList[position]

        //return if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
        return if("rWX1jkm98jNR2yBZhKPoayixx5y1" == currentMessage.senderId){
            MESSAGE_SENT
        }else{
            MESSAGE_RECEIVED
        }
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    fun updateData(newData: List<Chat>) {
        data = newData
        notifyDataSetChanged()
    }


    private fun setDrawableWithCornerRadius(drawable: Int): Drawable? {
        return ContextCompat.getDrawable(context, drawable)
    }

}