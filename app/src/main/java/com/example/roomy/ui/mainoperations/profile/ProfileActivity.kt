package com.example.roomy.ui.mainoperations.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.roomy.R
import com.example.roomy.databinding.ActivityMainBinding
import com.example.roomy.databinding.ActivityProfileBinding
import com.example.roomy.databinding.ItemInterestChipBinding
import com.example.roomy.databinding.NavActivityMainBinding
import com.example.roomy.databinding.NavActivityProfileBinding
import com.example.roomy.dataobject.Apartment
import com.example.roomy.ui.mainoperations.RoomyBaseActivity
import com.google.android.material.chip.Chip

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

        setInterestChips()

        setupHousePostedRecyclerView()
    }


    //Configure the behaviour of picking the interests, adding it to an arrraylist
    private fun setInterestChips() {
        interests.add("Books")
        interests.add("Football")
        interests.add("Travel")
        interests.add("Cartoons")
        interests.add("Karaoke")
        interests.add("Messi")

        for (interest in interests) {
            val chip =
                LayoutInflater.from(this).inflate(R.layout.item_interest_chip, null) as Chip

            chip.text = interest
            chip.setTextColor(resources.getColor(R.color.custom_red))

            chip.isClickable = false
            navProfileBinding.activityProfileInNav.interestChipGroup.addView(chip)

        }
    }

    private fun setupHousePostedRecyclerView() {
        val list = ArrayList<Apartment>()

        list.add(Apartment(
            R.drawable.apartment5, "One bedroom studio apartment ", "Semi-furnished", "Lekki",
            "Lagos state", "₦1,200,000"
        ))

        list.add(
            Apartment(
                R.drawable.apartment4, "Three bedroom apartment", "Fully-furnished", "Bodija",
                "Oyo state", "₦800,000"
            )
        )

        list.add(
            Apartment(
                R.drawable.apartment6, "Two bedroom office space", "Not-furnished", "Ketu",
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
