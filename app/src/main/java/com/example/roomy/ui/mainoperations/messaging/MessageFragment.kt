package com.example.roomy.ui.mainoperations.messaging

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomy.R
import com.example.roomy.databinding.FragmentMessageBinding
import com.example.roomy.dataobject.Message
import com.example.roomy.dataobject.UserTest
import com.example.roomy.ui.mainoperations.home.people.PeopleListAdapter

class MessageFragment : Fragment() {
    lateinit var messageBinding: FragmentMessageBinding

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

        setupRecyclerView()
    }

    private fun setupRecyclerView(){
        val list = ArrayList<Message>()

        list.add(
            Message("David", "Solomon",  R.drawable.alone_time, "You won't believe the house I saw")
        )

        list.add(
            Message("Musa", "Sadiq", R.drawable.alone_time, "You won't believe the house I saw You won't believe the house I saw")
        )

        list.add(
            Message("Ify",  "Ononye", R.drawable.alone_time_two,  "You won't believe the house I saw You won't believe the house I saw You won't believe the hous")
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
            Message("John", "Okafo", R.drawable.alone_time, "thyr hfhf uusus ehhdd fjffj")
        )

        val adapter = MessagingListAdapter(list, requireContext())
        messageBinding.rvMessages.adapter = adapter
        messageBinding.rvMessages.layoutManager = LinearLayoutManager(requireContext())
    }


}