package com.example.roomy.ui.mainoperations.profile

import android.os.Bundle
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.roomy.R
import com.example.roomy.databinding.ActivityProfileBinding
import com.example.roomy.databinding.ItemInterestChipBinding
import com.example.roomy.databinding.NavActivityProfileBinding
import com.example.roomy.dataobject.Apartment
import com.example.roomy.ui.mainoperations.RoomyBaseActivity

class ProfileActivity : RoomyBaseActivity() {
    private lateinit var profileBinding: ActivityProfileBinding
    private lateinit var itemInterestChipBinding: ItemInterestChipBinding
    lateinit var navProfileBinding: NavActivityProfileBinding
    private val interests = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        profileBinding = ActivityProfileBinding.inflate(layoutInflater)
        navProfileBinding = NavActivityProfileBinding.inflate(layoutInflater)
        itemInterestChipBinding = ItemInterestChipBinding.inflate(layoutInflater)
        setContentView(navProfileBinding.root)

        //hide activity status bar
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        navDrawerConfiguration(navProfileBinding.navView, navProfileBinding.navDrawerLayout, this)
        bottomNavClickListener(7, navProfileBinding.activityProfileInNav.bottomNavigation, this)

        setInterestChips(navProfileBinding.activityProfileInNav.interestChipGroup)

        setupHousePostedRecyclerView()
    }



    private fun setupHousePostedRecyclerView() {
        val list = ArrayList<Apartment>()

        val apartImage1 = ArrayList<Int>()
        apartImage1.add(R.drawable.apartment6)
        val apartImage2 = ArrayList<Int>()
        apartImage2.add(R.drawable.apartment5)
        val apartImage3 = ArrayList<Int>()
        apartImage3.add(R.drawable.apartment4)


        list.add(Apartment(1,
            apartImage1, "One bedroom studio apartment ",
            "Semi-furnished", "Lekki",
            "Lagos state", "₦1,200,000"
        ))

        list.add(
            Apartment(1, apartImage2, "Three bedroom apartment",
                "Fully-furnished", "Bodija",
                "Oyo state", "₦800,000"
            )
        )

        list.add(
            Apartment(3, apartImage3, "Two bedroom office space",
                "Not-furnished", "Ketu",
                "Lagos state", "₦500,000"
            )
        )

        val housePostedRVAdapter = HousePostedRVAdapter(this, list)
        navProfileBinding.activityProfileInNav.rvHousePosted.adapter = housePostedRVAdapter

        //Ensures only one card display in the screen snapping out which ever is less positioned in it
        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(navProfileBinding.activityProfileInNav.rvHousePosted)

        navProfileBinding.activityProfileInNav.rvHousePosted.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        housePostedRVAdapter.notifyDataSetChanged()

    }
}
