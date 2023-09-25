package com.example.roomy.ui.mainoperations.profile

import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.bumptech.glide.Glide
import com.example.roomy.R
import com.example.roomy.databinding.ItemInterestChipBinding
import com.example.roomy.databinding.NavActivityProfileBinding
import com.example.roomy.dataobject.Apartment
import com.example.roomy.ui.mainoperations.RoomyBaseActivity
import kotlinx.coroutines.launch

class ProfileActivity : RoomyBaseActivity() {
    private lateinit var itemInterestChipBinding: ItemInterestChipBinding
    lateinit var navProfileBinding: NavActivityProfileBinding
    private var interests = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        setupHousePostedRecyclerView()

        lifecycleScope.launch {
            configureUserPage()
        }

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

    //Get the user's details and customise his page par his details
    private suspend fun configureUserPage(){
        user = userViewModel.getUser()!!

        navProfileBinding.activityProfileInNav.tvStudiedAt.text = "Studied at University of Chicago"

        interests = user.interests as ArrayList<String>

        //Set user's profile and cover images
        try {
            Glide.with(this)
                .load(Uri.parse(user.displayImage))
                .into(navProfileBinding.activityProfileInNav.userProfileImage)

            Glide.with(this)
                .load(user.coverImage)
                .into(navProfileBinding.activityProfileInNav.userCoverImage)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        //Setting the user interests par what he chose when signing up or during editing
        setInterestChips(navProfileBinding.activityProfileInNav.interestChipGroup, interests)

    }

}
