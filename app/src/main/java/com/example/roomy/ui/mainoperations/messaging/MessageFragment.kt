package com.example.roomy.ui.mainoperations.messaging

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.roomy.databinding.FragmentMessageBinding

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

    }


}