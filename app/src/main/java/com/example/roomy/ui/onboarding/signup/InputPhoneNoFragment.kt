package com.example.roomy.ui.onboarding.signup

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.text.isDigitsOnly
import com.example.roomy.utils.PrefManager
import com.example.roomy.databinding.FragmentInputPhoneNoBinding
import com.example.roomy.ui.onboarding.OnboardBaseFragment


class InputPhoneNoFragment : OnboardBaseFragment() {

    private lateinit var inputPhoneNoBinding: FragmentInputPhoneNoBinding
    private lateinit var etPhone : AppCompatEditText
    private lateinit var btnContinue : AppCompatButton
    private var stringBuilder = StringBuilder()
    private var newNumber = ""
    private val prefManager = PrefManager()

    private val birthdayFragment = InputBirthdayFragment()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inputPhoneNoBinding = FragmentInputPhoneNoBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return inputPhoneNoBinding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etPhone = inputPhoneNoBinding.etPhoneNo
        btnContinue = inputPhoneNoBinding.btnContinue

        setupActionBar(inputPhoneNoBinding.inputPhoneNoToolbar)
        configureTextFonts()
        inputListener()
    }

    private fun configureTextFonts(){
        //resetting the font of the text to the appropriate one
        inputPhoneNoBinding.tvPhoneNoQuestion.typeface = initializeDMSansTypeFace(requireContext())
        etPhone.typeface = initializeDMSansTypeFace(requireContext())
        inputPhoneNoBinding.tvAgreement.typeface = initializeDMSansTypeFace(requireContext())
        btnContinue.typeface = initializeDMSansTypeFace(requireContext())
    }

    private fun inputListener(){
        inputPhoneNoBinding.etPhoneNo.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                //formatPhoneNumber(editable.toString())
                //setPhoneFormat(editable.toString())
                if (editable.toString().isEmpty()) {
                    Log.d("Text field ", "is empty")
                }
                else {
                    validatePhoneNumber(editable.toString())
                    Log.d("phoneNo final is ", getPhoneNumber(editable.toString()))
                }
            }
        })
    }

/*
    var textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            var phoneNumber = getText().toString()
            phoneNumber = phoneNumber.replace(" ".toRegex(), "")
            if (!validatePhoneNumber(phoneNumber)) {
                setTextColor(resources.getColor(R.color.red))
            } else {
                setTextColor(resources.getColor(R.color.green_darker))
            }
            if (isFormattedPhone(s.toString())) {
                return
            }
            val formatted = formatPhoneNumber(s.toString())
            setText(formatted)
            setSelection(formatted.length)
        }

        override fun afterTextChanged(s: Editable) {}
    }
*/

    private fun validatePhoneNumber(phoneNumber: String): Boolean {
        val isValid: Boolean
        if(phoneNumber.length in 10..11  && phoneNumber.isDigitsOnly()) {
            isValid = true
            resetContinueBtnToClickable(btnContinue, birthdayFragment )

            prefManager.setPhoneNo(getPhoneNumber(phoneNumber), requireContext())
        }else {
            isValid = false
            revertContinueBtnToUnclickable(btnContinue)
        }
        return isValid
    }

    private fun setPhoneFormat(phoneNumber: String ){
        if (phoneNumber.isEmpty() || phoneNumber.length < 4 || phoneNumber.length > 11) {
            Log.d("phoneNumber", "not parsable")
        }else if(phoneNumber.length == 5){
            newNumber = stringBuilder.append(phoneNumber.substring(0, 4))
                .append(" ")
                .append(phoneNumber.substring(4))
                .toString()
            Log.d("phoneNumber", newNumber)

            etPhone.setText(newNumber)

            etPhone.setSelection(newNumber.length-1)

            newNumber = stringBuilder.clear().toString()
        }

    /*else{
            newNumber = null.toString()
            newNumber = stringBuilder.append(phoneNumber.substring(0, 4))
                .append(" ")
                .append(phoneNumber.substring(4, 7))
                .append(" ")
                .append(phoneNumber.substring(7))
                .toString()
            Log.d("phoneNumber", newNumber)
            etPhone.text = newNumber
            newNumber = stringBuilder.clear().toString()

        }*/

/*
        return new StringBuilder("(").append(number.substring(0, 3))
            .append(") ").append(number.substring(3, 6))
            .append("-").append(number.substring(6))
            .toString();*/
     /*  if(phoneNumber.length == 10) {
           //val regex: Pattern = Pattern.compile("""\d{4}-\d{3}-\d{3}-""")


         *//*  phoneNumber.phoneFormatter()
           toastMessage(phoneNumber.phoneFormatter(), requireContext())

           val str =  phoneNumber.phoneFormatter()

           inputPhoneNoBinding.etPhoneNo.setText(str)
           *//*

           val regex =  Regex(pattern = """\d{4}-\d{3}-\d{3}-""").find(input = phoneNumber)?.value
           inputPhoneNoBinding.etPhoneNo.setText(regex)

       }

       if(phoneNumber.length == 11) {
           val regex: Pattern = Pattern.compile("""\d{4}-\d{3}-\d{4}-""");
           //val regex =  Regex(pattern = """\d{4}-\d{3}-\d{3}-""")
       }
*/

    }

    fun String.phoneFormatter(): String { return this.replace("\\B(?=(\\d{4})+(?!\\d))".toRegex(), "-") }
    /**
     * 0123456789xxx -> 0123 456 789xxx
     *
     * @param rawPhone
     * @return
     */
    private fun formatPhoneNumber(rawPhone: String): String {
        var rawPhone = rawPhone
        rawPhone = rawPhone.replace(" ".toRegex(), "")
        var phoneFormat = ""
        if (rawPhone.length > 4) {
            phoneFormat += rawPhone.substring(0, 4)
            rawPhone = rawPhone.substring(4)
        } else {
            return rawPhone
        }
        phoneFormat += " "
        if (rawPhone.length > 3) {
            phoneFormat += rawPhone.substring(0, 3)
            rawPhone = rawPhone.substring(3)
        } else {
            phoneFormat += rawPhone
            return phoneFormat
        }
        phoneFormat += " $rawPhone"
        return phoneFormat
    }


    fun getPhoneNumber(phoneNumber: String): String {

        return if(phoneNumber.substring(0, 1) == "0"){
            toastMessage("phone starts with 0", requireContext())
            val delZeroPhoneNumber = phoneNumber.substring( 1)

            inputPhoneNoBinding.ccPicker.selectedCountryCodeWithPlus.toString() + delZeroPhoneNumber
                .trim { it <= ' ' }.replace(" ".toRegex(), "")
        } else {
            inputPhoneNoBinding.ccPicker.selectedCountryCodeWithPlus.toString() + phoneNumber
                .trim { it <= ' ' }.replace(" ".toRegex(), "")
        }
    }
}