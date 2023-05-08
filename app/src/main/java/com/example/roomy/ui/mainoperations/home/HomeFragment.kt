package com.example.roomy.ui.mainoperations.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.roomy.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {
    private val TAG: String = HomeFragment::class.java.simpleName

    lateinit var homeBinding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewPager()
    }

    private fun setUpViewPager(){
        Log.d(TAG, "vp Fragment gets here ")

        val adapter = PeopleVPAdapterVPAdapter(childFragmentManager, lifecycle )

        homeBinding.viewPager.adapter = adapter
        //fpBinding.tabLayout.tabGravity = TabLayout.GRAVITY_CENTER

        TabLayoutMediator(homeBinding.tabLayout, homeBinding.viewPager){tab, position ->
            when(position){
                0 -> {
                    tab.text = "People"
                }
                1 -> {
                    tab.text = "Apartment"
                    Log.d("fragment", "Status fragment")
                }

                else -> {
                    Log.d("fragment", "position is out of index")
                }
            }
        }.attach()
    }

}