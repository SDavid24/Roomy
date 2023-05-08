package com.example.roomy.ui.onboarding.signup

import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.roomy.utils.PrefManager
import com.example.roomy.R
import com.example.roomy.databinding.FragmentInputPasswordBinding
import com.example.roomy.ui.onboarding.OnboardBaseFragment
import java.util.regex.Pattern
import java.util.*
import kotlin.properties.Delegates


class InputPasswordFragment : OnboardBaseFragment() {

    private lateinit var inputPasswordBinding: FragmentInputPasswordBinding
    private val photosFragment = UploadFirstPhotosFragment()
    private var hasSpecialCharOrNumeric  by Delegates.notNull<Boolean>()
    private var hasLength8 by Delegates.notNull<Boolean>()
    private var hasUpperCase by Delegates.notNull<Boolean>()
    private var hasMatchingTexts by Delegates.notNull<Boolean>()
    private val prefManager = PrefManager()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inputPasswordBinding = FragmentInputPasswordBinding.inflate(inflater, container, false)
        return inputPasswordBinding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionBar(inputPasswordBinding.inputPasswordToolbar)

        configureTextFonts()
        activateButton()
    }

    //resetting the font of the text to the appropriate one
    private fun configureTextFonts(){
        val typeface: Typeface = Typeface.createFromAsset(requireActivity().assets, "DMSans.ttf")
        inputPasswordBinding.createPassword.typeface = typeface
        //inputPasswordBinding.password.typeface = typeface
        inputPasswordBinding.etPassword.typeface = typeface
        //inputPasswordBinding.confirmPassword.typeface = typeface
        inputPasswordBinding.etConfirmPassword.typeface = typeface
        inputPasswordBinding.aimForOne.typeface = typeface
        inputPasswordBinding.eightChar.typeface = typeface
        inputPasswordBinding.oneUpperCase.typeface = typeface
        inputPasswordBinding.oneSymbol.typeface = typeface
        inputPasswordBinding.toGuess.typeface = typeface
        inputPasswordBinding.tvContinue.typeface = typeface
    }

    private fun activateButton(){
        //Listener for edit password
        inputPasswordBinding.etPassword.addTextChangedListener(object: TextWatcher{

            override fun afterTextChanged(editable: Editable?) {
                passwordValidity(editable!!)
                btnClickListener(editable)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })

        //Listener for edit confirm password
        inputPasswordBinding.etConfirmPassword.addTextChangedListener(object: TextWatcher{

            override fun afterTextChanged(editable: Editable?) {
                passwordValidity(editable!!)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    private fun hasUpperCase(data: CharSequence): Boolean {
        val password = data.toString()
        return password != password.lowercase(Locale.getDefault())
    }

    private fun hasSymbolOrNo(data: CharSequence): Boolean {
            val password = data.toString()
            val regex: Pattern = Pattern.compile("[A-Za-z ]*");
            return !password.matches(regex.toRegex())
        }

    private fun hasLength(data: CharSequence): Boolean {
        return data.toString().length >= 8
    }

    //checks if the string in confirm password matches that of the one in password
    private fun bothEditTextStringsMatches() : Boolean{
        hasMatchingTexts = (inputPasswordBinding.etPassword.text.toString()
                == inputPasswordBinding.etConfirmPassword.text.toString())
        return hasMatchingTexts
    }

    //runs all the if/else for the validity of the password any time a text is added
    private fun passwordValidity(editable: Editable){
        if(hasUpperCase(editable.toString())){
            inputPasswordBinding.llOneUpperCase.visibility = GONE
            hasUpperCase = true
        }else{
            inputPasswordBinding.llOneUpperCase.visibility = VISIBLE
            hasUpperCase = true
        }

        if(hasSymbolOrNo(editable.toString())){
            hasSpecialCharOrNumeric = true
            inputPasswordBinding.llOneSymbol.visibility = GONE
        } else {
            inputPasswordBinding.llOneSymbol.visibility = VISIBLE
            hasSpecialCharOrNumeric = false
        }

        if(hasLength(editable.toString())){
            inputPasswordBinding.llEightChar.visibility = GONE

            hasLength8 = true
        }else{
            inputPasswordBinding.llEightChar.visibility = VISIBLE
            hasLength8 = false
        }

        bothEditTextStringsMatches()

        if(hasLength8 && hasUpperCase && hasSpecialCharOrNumeric && hasMatchingTexts){
            resetBtnContinueToClickable(inputPasswordBinding.tvContinue)


        }else{
            revertContinueBtnToUnclickable(inputPasswordBinding.tvContinue)
        }
    }

    fun btnClickListener(editable: Editable){
        inputPasswordBinding.tvContinue.setOnClickListener {

            //save password to encrypted sharedPref
            prefManager.setPassword(editable.toString(), requireContext(), )

            parentFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, photosFragment)
                addToBackStack(null)
                commit()
            }
        }
    }
/*
    fun isNumeric(str: String): Boolean {
        return str.matches(Regex.\\\.("-?\\d+(.\\d+)?"))
    }*/


}