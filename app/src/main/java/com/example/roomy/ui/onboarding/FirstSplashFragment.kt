package com.example.roomy.ui.onboarding

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.roomy.ui.onboarding.splashscreens.SecondSplashFragment
import com.example.roomy.databinding.FragmentFirstSplashBinding

class FirstSplashFragment : OnboardBaseFragment() {

    private lateinit var firstSplashBinding : FragmentFirstSplashBinding
    //private val firstSplashFragment = FirstSplashFragment()
    private val secondSplashFragment = SecondSplashFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        firstSplashBinding = FragmentFirstSplashBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return firstSplashBinding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        if (actionBar != null){
            actionBar.hide()
        }

        //hide activity status bar
        (activity as AppCompatActivity?)!!.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        //This sets the font of the texts in this activity
        val typeface : Typeface = Typeface.createFromAsset( (activity as AppCompatActivity?)!!.assets, "snapitc.ttf")
         firstSplashBinding.roomy.typeface = typeface

        //splashLoad()
    }

    //This method makes the Splash activity move to the Intro or main activity after the stipulated time
   /* private fun splashLoad(){

        Handler().postDelayed({

            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, loginFragment)
                commit()

                hideAppBar()  //Makes the bottom nav bar invisible in the splash activity
            }

            //val currentUserId = FireStoreClass().getCurrentUserId()

            *//*  if (currentUserId.isNotEmpty()){
                  startActivity(Intent(this, MainActivity::class.java))
              }else{
                  startActivity(Intent(this, IntroActivity::class.java))
              }*//*
        }, 2500
        )

    }*/







}