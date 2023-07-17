package com.example.roomy.ui.mainoperations.home.apartment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomy.R
import com.example.roomy.databinding.FragmentApartmentRVBinding
import com.example.roomy.dataobject.Apartment

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

        val apartImage1 = ArrayList<Int>()
        apartImage1.add(R.drawable.apartment6)
        val apartImage2 = ArrayList<Int>()
        apartImage2.add(R.drawable.apartment5)
        val apartImage3 = ArrayList<Int>()
        apartImage3.add(R.drawable.apartment4)

        list.add(
            Apartment(1,apartImage1, "Two bedroom office space",  "Semi-furnished","Ketu",
            "Lagos state", "₦500,000")
        )
        list.add(
            Apartment(2,apartImage2, "One bedroom studio apartment ",  "Semi-furnished","Lekki",
            "Lagos state", "₦1,200,000")
        )
        list.add(
            Apartment(3, apartImage3, "Three bedroom apartment",  "Fully-furnished","Bodija",
            "Oyo state", "₦800,000")
        )
        list.add(
            Apartment(4, apartImage1, "Duplex apartment building",  "Not-furnished","Mowe",
            "Ogun state", "₦300,000")
        )
        list.add(
            Apartment(5, apartImage2, "Self-contain",  "Semi-furnished","Jericho",
            "Oyo state", "₦250,000")
        )


        val adapter = ApartmentListAdapter(list, requireContext())
        apartmentRVBinding.rvPeople.adapter = adapter
        apartmentRVBinding.rvPeople.layoutManager = LinearLayoutManager(requireContext())
    }

}