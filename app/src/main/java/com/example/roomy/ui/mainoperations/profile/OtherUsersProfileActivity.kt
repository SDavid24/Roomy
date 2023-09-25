package com.example.roomy.ui.mainoperations.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.roomy.R
import com.example.roomy.databinding.ActivityOtherUsersProfileBinding
import com.example.roomy.ui.mainoperations.RoomyBaseActivity

class OtherUsersProfileActivity : RoomyBaseActivity() {
    private lateinit var otherUsersBinding: ActivityOtherUsersProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        otherUsersBinding = ActivityOtherUsersProfileBinding.inflate(layoutInflater)
        setContentView(otherUsersBinding.root)

        //setInterestChips(otherUsersBinding.interestChipGroup!!)

    }
}