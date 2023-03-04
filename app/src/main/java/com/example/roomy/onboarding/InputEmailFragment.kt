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
import com.example.roomy.databinding.FragmentInputEmailBinding

class InputEmailFragment : OnboardBaseFragment() {

    private lateinit var inputEmailBinding: FragmentInputEmailBinding
    private val inputPhoneNoFragment = InputPhoneNoFragment()
    private lateinit var email : TextView
/*
    private var clicked : Boolean = false
*/


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inputEmailBinding = FragmentInputEmailBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return inputEmailBinding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        email = inputEmailBinding.etEmail

        setupActionBar(inputEmailBinding.inputEmailToolbar)
        configureTextFonts()
        clickListeners()
    }

    private fun configureTextFonts(){
        //resetting the font of the text to the appropriate one
        inputEmailBinding.tvEmailQuestion.typeface = initializeDMSansTypeFace(requireActivity())
        inputEmailBinding.etEmail.typeface = initializeDMSansTypeFace(requireActivity())
        inputEmailBinding.tvAgreement.typeface = initializeDMSansTypeFace(requireActivity())
        inputEmailBinding.btnContinue.typeface = initializeDMSansTypeFace(requireActivity())
    }

    private fun clickListeners(){
        inputEmailBinding.etEmail.doOnTextChanged { text, start, before, count ->
            if(email.text.toString().contains("@")
                && email.text.toString().contains(".")
                && email.text.toString().length > 4){
                    toastMessage("email is valid", requireContext())
                resetContinueBtnToClickable(inputEmailBinding.btnContinue, inputPhoneNoFragment )

                PrefManager().setEmail(email.text.toString(), requireContext())

                //toastMessage("button is CLICKED!", requireContext())

            }else{
                revertContinueBtnToUnclickable(inputEmailBinding.btnContinue)
            }
        }
    }
}