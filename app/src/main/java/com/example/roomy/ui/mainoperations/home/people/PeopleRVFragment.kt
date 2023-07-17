package com.example.roomy.ui.mainoperations.home.people

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomy.R
import com.example.roomy.databinding.FragmentPeopleRVBinding
import com.example.roomy.dataobject.UserTest


class PeopleRVFragment : Fragment() {

    private lateinit var peopleRVBinding: FragmentPeopleRVBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        peopleRVBinding = FragmentPeopleRVBinding.inflate(inflater, container,false)
        // Inflate the layout for this fragment
        return peopleRVBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView(){
        val list = ArrayList<UserTest>()

        list.add(UserTest("David Solomon", 24, "Jumofak",
            "Lagos state", "Android developer", R.drawable.alone_time))


        list.add(UserTest("Tole Adekoya", 30, "Ketu",
            "Ogun state", "Photographer developer", R.drawable.alone_time_two))


        list.add(UserTest("Musa Sadiq", 29, "Oke-Bola",
            "Ibadan", "Civil Engineer", R.drawable.alone_time))


        list.add(UserTest("Ify Ononye", 22, "Akute",
            "Ogun state", "Music Producer", R.drawable.alone_time_two))


        list.add(UserTest("Chukwuemeka Dan-Philips", 40, "Festac",
            "Lagos state", "Trader developer", R.drawable.alone_time))


        list.add(UserTest("Kaosarat Sheriff", 24, "Sabo-Kebbi",
            "Kebbi state", "Teacher", R.drawable.alone_time_two))


        list.add(UserTest("Ali-Modu Sheriff", 24, "Gwagwalada",
            "Abuja", "Politician", R.drawable.alone_time))


        list.add(UserTest("Tunde Kelani", 62, "Abeokuta",
            "Ogun state", "Film maker", R.drawable.alone_time_two))


        list.add(UserTest("John Okafor", 55, "Asaba",
            "Delta state", "Actor", R.drawable.alone_time))

        list.add(UserTest("John OkaforJohn OkaforJohn OkaforJohn Okafor", 24, "Asaba",
            "Delta state", "Actor", R.drawable.alone_time))


        val adapter = PeopleListAdapter(list, requireContext())
        peopleRVBinding.rvPeople.adapter = adapter
        peopleRVBinding.rvPeople.layoutManager = LinearLayoutManager(requireContext())
    }

}