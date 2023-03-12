package com.example.roomy

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.roomy.databinding.FragmentSecondSplashBinding
import com.example.roomy.onboarding.InputNamesFragment
import com.example.roomy.onboarding.SignInFragment


class SecondSplashFragment : Fragment() {

    private lateinit var secondSplashBinding: FragmentSecondSplashBinding
    private var image1isDisplayed = false
    var imageArray = intArrayOf(R.drawable.alone_time, R.drawable.alone_time_two)
    private val inputNamesFragment = InputNamesFragment()
    private val signInFragment = SignInFragment()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        secondSplashBinding = FragmentSecondSplashBinding.inflate(inflater, container, false)

        return secondSplashBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tv = TextView(requireContext())
        val toolBarTypeface: Typeface = Typeface.createFromAsset(requireActivity().assets, "snapitc.ttf")

        tv.typeface = toolBarTypeface
        tv.text = "ROOMY"

        tv.textSize = 24F;
        tv.setTextColor(Color.WHITE);

        (activity as AppCompatActivity?)!!.supportActionBar?.elevation = 0f //code to remove shadow beneath the action bar
        (activity as AppCompatActivity?)!!.supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        (activity as AppCompatActivity?)!!.supportActionBar?.customView = tv

        Log.e("SecondSplashFragment", "Fragment is loaded")
        //resetting the font of the text to the appropriate one
        val typeface: Typeface = Typeface.createFromAsset(requireActivity().assets, "DMSans.ttf")
        secondSplashBinding.text1.typeface = typeface
        secondSplashBinding.text2.typeface = typeface
        secondSplashBinding.text3.typeface = typeface
        secondSplashBinding.getStarted.typeface = typeface
        secondSplashBinding.signIn.typeface = typeface

        btnClickListeners()

    }

    //Both methods above handle the switching of images in an animated manner
    private fun switchToSplashImage1() {

        Handler().postDelayed(
            {
                if (image1isDisplayed) {
                    secondSplashBinding.displayImage.setImageResource(R.drawable.alone_time_two)
                    image1isDisplayed = false
                }
            }, 1500
        )
        switchToSplashImages2()
    }

    private fun switchToSplashImages2() {

        Handler().postDelayed(
            {
                if (!image1isDisplayed) {
                    secondSplashBinding.displayImage.setImageResource(R.drawable.alone_time)
                    image1isDisplayed = true
                }
            }, 2500
        )
        Log.e("SecondSplashFragment", "switchToSplashImages2 is loaded")

        switchToSplashImage1()
    }

    private fun btnClickListeners(){
        secondSplashBinding.getStarted.setOnClickListener{
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, inputNamesFragment)
                commit()
            }
        }

        secondSplashBinding.signIn.setOnClickListener{
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, signInFragment)
                commit()
            }
        }

    }

}


