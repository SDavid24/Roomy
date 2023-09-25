package com.example.roomy.ui.mainoperations.messaging

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnLayout
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomy.databinding.ActivityChatBinding
import com.example.roomy.dataobject.Chat
import com.example.roomy.viewmodel.ChatViewModel
import com.google.firebase.database.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.properties.Delegates

@RequiresApi(Build.VERSION_CODES.Q)
class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private val TAG = "ChatActivity"
    private lateinit var userNames: ArrayList<String>
    private var friendsProfileImage by Delegates.notNull<Int>()
    private lateinit var chatViewModel: ChatViewModel
    var receiverRoom : String? = null
    var senderRoom : String? = null
    private lateinit var adapter: ChatAdapter
    private lateinit var mDBRef: DatabaseReference
    private lateinit var chatList: ArrayList<Chat>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        chatViewModel = ViewModelProvider(this)[ChatViewModel::class.java]

        //val name = intent.getStringExtra("name")
        val name = "Jadesola Williams"
        supportActionBar?.title = name

        getCurrentTimeAndDay()

        configureChatDatabaseAndTextFunction()


        setupRecyclerView()

    }

    fun NestedScrollView.scrollToBottom() {
        this.doOnLayout {
            this.fullScroll(View.FOCUS_DOWN)
        }
    }

    private fun setupRecyclerView() {
        // Initialize the RecyclerView and adapter
        friendsProfileImage = intent.getIntExtra("friendsProfileImage", 0)
        chatList = ArrayList()
        adapter = ChatAdapter(chatList, this, friendsProfileImage)

        // Create an instance of ChatHeaderAdapter with an empty header view initially
        val headerAdapter = ChatHeaderAdapter()

        // Combine the ChatAdapter and HeaderAdapter using ConcatAdapter
        val concatAdapter = ConcatAdapter(headerAdapter, adapter)

        // Set the combined adapter to the RecyclerView
        binding.chatRecyclerview.adapter = concatAdapter
        binding.chatRecyclerview.layoutManager = LinearLayoutManager(this)

        chatViewModel.getMessages(senderRoom!!)


        // Observe the LiveData and update the adapter
        chatViewModel.myChatList.observe(this) { chatListFromDb ->
            if (chatListFromDb != null) {
                // Update your adapter with chatListFromDb
                chatList.addAll(chatListFromDb)
                adapter.notifyDataSetChanged()            } else {
                // Handle the case when chatListFromDb is null (e.g., an error occurred)
            }
        }

        // Attach the ValueEventListener to the specific chat room's "messages" node
        val messagesRef = mDBRef.child(senderRoom!!).child("messages")
        val query = messagesRef.orderByChild("timestamp")

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                chatList.clear()
                for (messageSnapshot in snapshot.children) {
                    val message = messageSnapshot.getValue(Chat::class.java)
                    message?.let {
                        chatList.add(it)
                    }
                }


               /* // Get a reference to the header view (Replace R.layout.activity_chat with your actual header layout)
                val headerView = layoutInflater.inflate(R.layout.activity_chat, binding.chatRecyclerview, false)

                // Set the header view in the adapter
                //headerAdapter.setHeaderView(headerView)*/

                // Notify the adapter about the data change
                adapter.notifyDataSetChanged()
                Log.d(TAG, "smoothScrollToPosition() called")

                // Scroll to the last item in the RecyclerView when the data changes
                binding.chatRecyclerview.smoothScrollToPosition(chatList.size - 1)

                // Scroll the NestedScrollView to the bottom before the layout is drawn
                scrollNestedScrollViewToBottom()

            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error
            }
        })
    }


    private fun configureChatDatabaseAndTextFunction() {
        mDBRef = FirebaseDatabase.getInstance().reference
        //val receiverUid = intent.getStringExtra("uid")
        val receiverUid = "NnPXkB9e3DQyiDgijh3xtoihuHE3"
        //val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        val senderUid = "rWX1jkm98jNR2yBZhKPoayixx5y1"
        val participantsList = listOf(senderUid, receiverUid) // Replace this with your list of participants

        senderRoom = receiverUid + senderUid
        receiverRoom =  senderUid + receiverUid


        // Create unique chatId by sorting and concatenating UID2s
        val sortedUids = listOf(senderUid, receiverUid).sorted()
        val chatId = sortedUids.joinToString("_")
        senderRoom = "chats/$senderUid/$receiverUid"
        receiverRoom = "chats/$receiverUid/$senderUid"

        binding.sendButton.setOnClickListener {
            Log.d(TAG, "Send button is clicked")

            val message = binding.etInputMessage.text.toString().trim()

            if (message.isEmpty()) {
                Toast.makeText(this, "Input a text", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Get the current timestamp as the message key
            val messageKey = mDBRef.child("$senderRoom/messages").push().key ?: ""
            val messageKey2 = mDBRef.child("$receiverRoom/messages").push().key ?: ""

            // Create the message object with the necessary data
            val messageObject = Chat(0, message, senderUid, senderRoom, messageKey, System.currentTimeMillis())

            chatViewModel.saveNewMessage(messageObject) //Save to local db
            Log.d(TAG, "initial chatList list size is: ${chatList.size}")



            chatList.add(messageObject)
            setupRecyclerView()
            Log.d(TAG, "initial chatList list size is: ${chatList.size}")

            scrollNestedScrollViewToBottom()

            // Add the new message to both sender and receiver rooms
            val messageData = HashMap<String, Any>()
            messageData["$senderRoom/messages/$messageKey"] = messageObject
            messageData["$receiverRoom/messages/$messageKey2"] = messageObject
            Log.d(TAG, "senderRoom: $senderRoom")
            Log.d(TAG, "messageKey: $messageKey")


            mDBRef.updateChildren(messageData)
                .addOnSuccessListener {
                    // New message added successfully, now add the participants to the chat
                    val chatParticipantsData = HashMap<String, Any>()
                    for (participantId in participantsList) {
                        chatParticipantsData["$senderRoom/participants/$participantId"] = true
                        chatParticipantsData["$receiverRoom/participants/$participantId"] = true
                    }

                    mDBRef.updateChildren(chatParticipantsData)
                        .addOnSuccessListener {
                            binding.etInputMessage.text?.clear()
                            Log.d(TAG, "etInputMessage should clear")

                            // Scroll the NestedScrollView to the bottom before the layout is drawn
                            scrollNestedScrollViewToBottom()
                        }
                        .addOnFailureListener { exception ->
                            // Handle failure to add participants
                            Toast.makeText(this, "Failed to add participants: ${exception.message}", Toast.LENGTH_SHORT).show()
                        }
                }
                .addOnFailureListener { exception ->
                    // Handle failure to add the message
                    Toast.makeText(this, "Failed to send message: ${exception.message}", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "Failed to send message: ${exception.message}")
                    binding.etInputMessage.text?.clear()
                }

        }

        binding.etInputMessage.text?.clear()
        Log.d(TAG, "etInputMessage 22 should clear")
    }

    private fun scrollNestedScrollViewToBottom() {
        binding.scrollview.post {
            binding.scrollview.fullScroll(View.FOCUS_DOWN)
        }
    }

    fun getCurrentTimeAndDay() {
        val currentDateTime = LocalDateTime.now()
        val dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH) // Format for date ("jul 12, 2023")
        val timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH)     // Format for time ("2:30 pm")
        val dayFormatter = DateTimeFormatter.ofPattern("EEEE", Locale.ENGLISH)         // Format for day (full weekday name)
        val dateDayFormatter = DateTimeFormatter.ofPattern("E, MMM dd", Locale.ENGLISH) // Format for date ("Mon, Jul 21")

        val currentDate = currentDateTime.format(dateFormatter)
        val currentTime = currentDateTime.format(timeFormatter)
        val currentDay = currentDateTime.format(dayFormatter)
        val currentDayDate = currentDateTime.format(dateDayFormatter)

        println("Current Date: $currentDate")
        println("Current Time: $currentTime")
        println("Current Day: $currentDay")
        println("Current Day Date: $currentDayDate")

    }

    override fun onResume() {
        super.onResume()
        setupRecyclerView()
    }

}