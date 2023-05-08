package com.example.roomy.ui.mainoperations.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomy.R
import com.example.roomy.databinding.FragmentApartmentRVBinding
import com.example.roomy.dataobject.Apartment
import com.example.roomy.dataobject.UserTest

class ApartmentRVFragment : Fragment() {

    private lateinit var apartmentRVBinding: FragmentApartmentRVBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        apartmentRVBinding = FragmentApartmentRVBinding.inflate(inflater, container,false)
        // Inflate the layout for this fragment
        return apartmentRVBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView(){
        val list = ArrayList<Apartment>()

        list.add(
            Apartment( R.drawable.apartment6, "Two bedroom office space",  "Semi-furnished","Ketu",
            "Lagos state", "₦500,000")
        )
        list.add(
            Apartment( R.drawable.apartment5, "One bedroom studio apartment ",  "Semi-furnished","Lekki",
            "Lagos state", "₦1,200,000")
        )
        list.add(
            Apartment( R.drawable.apartment4, "Three bedroom apartment",  "Fully-furnished","Bodija",
            "Oyo state", "₦800,000")
        )
        list.add(
            Apartment( R.drawable.apartment6, "Duplex apartment building",  "Not-furnished","Mowe",
            "Ogun state", "₦300,000")
        )
        list.add(
            Apartment( R.drawable.apartment5, "Self-contain",  "Semi-furnished","Jericho",
            "Oyo state", "₦250,000")
        )


        val adapter = ApartmentListAdapter(list, requireContext())
        apartmentRVBinding.rvPeople.adapter = adapter
        apartmentRVBinding.rvPeople.layoutManager = LinearLayoutManager(requireContext())
    }

}