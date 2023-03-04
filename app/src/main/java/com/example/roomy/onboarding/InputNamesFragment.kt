package com.example.roomy.onboarding

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.widget.doOnTextChanged
import com.example.roomy.utils.PrefManager
import com.example.roomy.databinding.FragmentInputNamesBinding

class InputNamesFragment : OnboardBaseFragment() {
    private lateinit var inputNamesBinding: FragmentInputNamesBinding
    private val chooseGenderFragment = ChooseGenderFragment()
    private val prefManager = PrefManager()
    private lateinit var firstName : TextView
    private lateinit var lastName : TextView
   /// private var clicked : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        inputNamesBinding = FragmentInputNamesBinding.inflate(inflater, container, false)
        return inputNamesBinding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firstName = inputNamesBinding.etFirstname
        lastName = inputNamesBinding.etLastname
        //clicked = false

        setupActionBar(inputNamesBinding.inputNamesToolbar)
        configureTextFontsAndCLickListeners()
    }

    private fun configureTextFontsAndCLickListeners(){
        //resetting the font of the text to the appropriate one
        inputNamesBinding.tvFirstName.typeface = initializeDMSansTypeFace(requireActivity())
        inputNamesBinding.tvContinue.typeface = initializeDMSansTypeFace(requireActivity())
        inputNamesBinding.tvAgreement.typeface = initializeDMSansTypeFace(requireActivity())
        inputNamesBinding.tvLastname.typeface = initializeDMSansTypeFace(requireActivity())
        inputNamesBinding.etFirstname.typeface = initializeDMSansTypeFace(requireActivity())
        inputNamesBinding.etLastname.typeface = initializeDMSansTypeFace(requireActivity())
        inputNamesBinding.signUpText.typeface = initializeDMSansTypeFace(requireActivity())
        inputNamesBinding.signUpText.typeface = initializeDMSansTypeFace(requireActivity())
        inputNamesBinding.signInText.typeface = initializeDMSansTypeFace(requireActivity())

        //Monitors the edit text view when it content changes
        inputNamesBinding.etFirstname.doOnTextChanged { text, start, before, count ->
            nameInputConfig()
        }
        inputNamesBinding.etLastname.doOnTextChanged { text, start, before, count ->
            nameInputConfig()
        }
    }

    //Checks if the strings are more than zero before activating the string button
    private fun nameInputConfig(){
        if(inputNamesBinding.etFirstname.text?.toString()?.length != 0
            && inputNamesBinding.etLastname.text?.toString()?.length != 0
        ){
            resetContinueBtnToClickable(inputNamesBinding.tvContinue, chooseGenderFragment)
            PrefManager().setFirstname(firstName.text.toString() , requireContext())
            PrefManager().setLastname(lastName.text.toString() , requireContext())

        }else{
            revertContinueBtnToUnclickable(inputNamesBinding.tvContinue)
        }
    }

}