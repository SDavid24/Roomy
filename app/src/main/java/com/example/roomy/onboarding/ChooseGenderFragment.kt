package com.example.roomy.onboarding

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.roomy.utils.PrefManager
import com.example.roomy.R
import com.example.roomy.databinding.FragmentChooseGenderBinding

class ChooseGenderFragment : OnboardBaseFragment() {

    private lateinit var chooseGenderBinding: FragmentChooseGenderBinding
    private val inputEmailFragment = InputEmailFragment()
    private var gender = 0
    private val prefManager = PrefManager()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        chooseGenderBinding = FragmentChooseGenderBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return chooseGenderBinding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupActionBar(chooseGenderBinding.chooseGenderToolbar)
        configureTextFonts()
        onGenderPickListener()

    }

    private fun onGenderPickListener(){
        //male click listeners
        chooseGenderBinding.tvMale.setOnClickListener {
            gender = 1

            chooseGenderBinding.tvMale.background = ContextCompat.getDrawable(requireContext(),
                R.drawable.stroke_green_white_bg_btn
            )
            chooseGenderBinding.tvFemale.background = ContextCompat.getDrawable(requireContext(),
                R.drawable.bg_grey
            )

            chooseGenderBinding.tvFemale.setTextColor(ContextCompat.getColor(requireContext(),
                R.color.hint_text_grey
            ))
            chooseGenderBinding.tvMale.setTextColor(ContextCompat.getColor(requireContext(),
                R.color.custom_green
            ))

            prefManager.setGender("Male", requireContext())
            resetContinueBtnToClickable(chooseGenderBinding.tvContinue, inputEmailFragment)
        }

        //female click listener
        chooseGenderBinding.tvFemale.setOnClickListener {
            gender = 2

            chooseGenderBinding.tvFemale.background = ContextCompat.getDrawable(requireContext(),
                R.drawable.stroke_green_white_bg_btn
            )
            chooseGenderBinding.tvMale.background = ContextCompat.getDrawable(requireContext(),
                R.drawable.bg_grey
            )

            chooseGenderBinding.tvMale.setTextColor(ContextCompat.getColor(requireContext(),
                R.color.hint_text_grey
            ))
            chooseGenderBinding.tvFemale.setTextColor(ContextCompat.getColor(requireContext(),
                R.color.custom_green
            ))

            prefManager.setGender("Female", requireContext())
            resetContinueBtnToClickable(chooseGenderBinding.tvContinue, inputEmailFragment)
        }
    }

    private fun configureTextFonts(){

        //resetting the font of the text to the appropriate one
        chooseGenderBinding.tvContinue.typeface = initializeDMSansTypeFace(requireContext())
        chooseGenderBinding.tvMale.typeface = initializeDMSansTypeFace(requireContext())
        chooseGenderBinding.tvFemale.typeface = initializeDMSansTypeFace(requireContext())
    }

}