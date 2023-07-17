package com.example.roomy.ui.mainoperations.messaging

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.roomy.R
import com.example.roomy.databinding.ItemMessagesListBinding
import com.example.roomy.dataobject.Message

class MessagingListAdapter(private val messagesList: ArrayList<Message>, private val context: Context)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class MessagesViewHolder(binding: ItemMessagesListBinding) : RecyclerView.ViewHolder(binding.root) {
        var image = binding.ivImage
        var tvname = binding.tvName
        var lastMessage = binding.tvLastMessage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): MessagesViewHolder {
        // val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_pager, parent, false)

        val binding = ItemMessagesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MessagesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return messagesList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = messagesList[position]

        if (holder is MessagesViewHolder) {

            Glide
                .with(context)
                .load(model.image)
                .centerCrop()
                .placeholder(R.drawable.alone_time)
                .into(holder.image)

            holder.tvname.text = "${model.firstName} ${model.lastName}"
            //holder.lastMessage.text = model.lastMessage

            /**Method that adds the 3 dots to the message if it longer than the text view*/
            // Set the ellipsis mode to truncate the text

            val maxLineCount = 1 // Maximum number of lines for the TextView
            val ellipsis = "..." // Ellipsis to indicate truncated text

            holder.lastMessage.maxLines = maxLineCount
            holder.lastMessage.text = model.lastMessage

            // Check if the text needs truncation
            holder.lastMessage.post {
                val lineCount = holder.lastMessage.lineCount

                if (lineCount > maxLineCount) {
                    val lastLineIndex = maxLineCount - 1
                    val lastLineStartOffset = holder.lastMessage.layout.getLineStart(lastLineIndex)
                    val lastLineEndOffset = holder.lastMessage.layout.getLineEnd(lastLineIndex)
                    val lastLineVisibleWidth = holder.lastMessage.width - holder.lastMessage.paddingLeft - holder.lastMessage.paddingRight

                    // Check if the last line can accommodate the ellipsis
                    if (holder.lastMessage.paint.measureText(model.lastMessage.substring(lastLineStartOffset, lastLineEndOffset)) > lastLineVisibleWidth) {
                        val ellipsizedText = TextUtils.ellipsize(
                            model.lastMessage.substring(lastLineStartOffset),
                            holder.lastMessage.paint,
                            lastLineVisibleWidth.toFloat(),
                            TextUtils.TruncateAt.END
                        )
                        val truncatedText = model.lastMessage.substring(0, lastLineStartOffset) + ellipsis + ellipsizedText

                        holder.lastMessage.text = truncatedText
                    }
                }
            }

        }
    }

}
