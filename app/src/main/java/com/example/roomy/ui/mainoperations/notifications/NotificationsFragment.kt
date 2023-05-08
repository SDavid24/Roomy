package com.example.roomy.ui.mainoperations.notifications

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.roomy.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    lateinit var mBinding : FragmentNotificationsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        mBinding = FragmentNotificationsBinding.inflate(inflater, container, false )
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("NotificationsFragment", "Checking for error")

    }
}