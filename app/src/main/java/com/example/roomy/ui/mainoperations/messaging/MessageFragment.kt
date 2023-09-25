package com.example.roomy.ui.mainoperations.messaging

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomy.R
import com.example.roomy.databinding.FragmentMessageBinding
import com.example.roomy.dataobject.Message
import com.example.roomy.dataobject.User
import com.example.roomy.dataobject.UserTest
import com.example.roomy.ui.mainoperations.home.people.PeopleListAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MessageFragment : Fragment() {
    lateinit var messageBinding: FragmentMessageBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDBRef: DatabaseReference
    private lateinit var adapter: MessagingListAdapter
    private lateinit var userList: ArrayList<User>
    private lateinit var userNames: ArrayList<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        messageBinding = FragmentMessageBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return messageBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("NotificationsFragment", "Checking for error")
        mDBRef = FirebaseDatabase.getInstance().reference
        mAuth = FirebaseAuth.getInstance()

        setupRecyclerView()
    }


   /* private fun setupRecyclerView(){
        userList = ArrayList()
        userNames = ArrayList()
        adapter = MessagingListAdapter(userList, requireContext())
        messageBinding.rvMessages.adapter = adapter
        messageBinding.rvMessages.layoutManager = LinearLayoutManager(requireContext())


        mDBRef.child("user").addValueEventListener(object: ValueEventListener {//To get list of users

            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()

                for(postSnapshot in snapshot.children){
                    val currentUser = postSnapshot.getValue(User::class.java)
                    if(mAuth.currentUser!!.uid != currentUser!!.id) {
                        userList.add(currentUser)
                    }
                }
                Log.d(TAG, "List of users: $userNames")
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
*/
  /*  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(com.google.firebase.database.R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == com.google.firebase.database.R.id.logout){
            mAuth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        return true
    }

*/


    private fun setupRecyclerView(){
        val list = ArrayList<Message>()

        list.add(
            Message("David", "Solomon",  R.drawable.alone_time, "You won't believe the house I saw")
        )

        list.add(
            Message("Musa", "Sadiq", R.drawable.alone_time, "You won't believe the house I saw You won't believe the house I saw")
        )

        list.add(
            Message("Ify",  "Ononye", R.drawable.alone_time_two,  "You won't believe the house I saw You won't believe the house I saw You won't believe the house")
        )

        list.add(
            Message("Chukwuemeka", "Dan-Philips", R.drawable.alone_time, "thyr  hfhf uusus ehhdd fjffj")
        )

        list.add(
            Message("Kaosarat","Sheriff",  R.drawable.alone_time_two, "thyr  hfhf uusus ehhdd fjffj")
        )

        list.add(
            Message("Ali-Modu",  "Sheriff",  R.drawable.alone_time, "thyr hfhf uusus ehhdd fjffj")
        )

        list.add(
            Message("Tunde",  "Kelani", R.drawable.alone_time_two, "thyr hfhf uusus ehhdd fjffj")
        )

        list.add(
            Message("John", "Okafor", R.drawable.alone_time, "thyr hfhf uusus ehhdd fjffj")
        )

        list.add(
            Message("John", "Okafor", R.drawable.alone_time, "thyr hfhf uusus ehhdd fjffj")
        )

        val adapter = MessagingListAdapter(list, requireContext())
        messageBinding.rvMessages.adapter = adapter
        messageBinding.rvMessages.layoutManager = LinearLayoutManager(requireContext())
    }



}