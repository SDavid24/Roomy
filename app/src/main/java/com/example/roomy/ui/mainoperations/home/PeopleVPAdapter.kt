package com.example.roomy.ui.mainoperations.home

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter


class PeopleVPAdapterVPAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0->{
                Log.d("fragment", "peopleRVFragment")
                PeopleRVFragment()

            }
            1->{
                Log.d("fragment", "apartmentRVFragment")
                ApartmentRVFragment()

            }
            else ->{
                Log.d("fragment21", "null fragment")
                Fragment()
            }
        }
    }


}