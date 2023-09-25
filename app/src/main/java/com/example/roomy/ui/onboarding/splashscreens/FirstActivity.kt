package com.example.roomy.ui.onboarding.splashscreens

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.example.roomy.R
import com.example.roomy.database.RoomDB
import com.example.roomy.databinding.ActivityFirstBinding
import com.example.roomy.ui.firebase.FirestoreClass
import com.example.roomy.ui.mainoperations.MainActivity
import com.example.roomy.ui.onboarding.OnBoardingActivity

class FirstActivity : AppCompatActivity() {
   // private val TAG = FirstActivity().javaClass.simpleName

    private lateinit var firstBinding: ActivityFirstBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        //Log.d(TAG, "got to stage 0")

        firstBinding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(firstBinding.root)

        //hide activity toolbar
        supportActionBar?.hide()

        //hide activity status bar
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        //This sets the font of the texts in this activity
        val typeface : Typeface = Typeface.createFromAsset(assets, "snapitc.ttf")
        firstBinding.roomy.typeface = typeface
        splashLoad()
    }

    //This method makes the Splash activity move to the Intro or main activity after the stipulated time
    private fun splashLoad() {
        Handler().postDelayed({
            //startActivity(Intent(this, OnBoardingActivity::class.java))
            //Log.d(TAG, "got to stage 1")
            val currentUserId = FirestoreClass().getCurrentUserId()

            if (currentUserId.isNotEmpty()){
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                startActivity(Intent(this, OnBoardingActivity::class.java))
            }

            finish()

        }, 2500)
    }





}