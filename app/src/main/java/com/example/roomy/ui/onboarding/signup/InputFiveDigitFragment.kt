package com.example.roomy.ui.onboarding.signup

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.roomy.ui.mainoperations.MainActivity
import com.example.roomy.R
import com.example.roomy.databinding.FragmentInputFiveDigitBinding
import com.example.roomy.dataobject.User
import com.example.roomy.ui.onboarding.OnboardBaseFragment
import com.example.roomy.utils.PrefManager
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class InputFiveDigitFragment : OnboardBaseFragment() {

    private val TAG = "PhoneNo Verification"
    private lateinit var inputFiveBinding: FragmentInputFiveDigitBinding
    private  lateinit var mAuth : FirebaseAuth
    private  var storedVerificationId : String? = null
    private  lateinit var otp : String
    private var text1 = ""
    private var text2 = ""
    private var text3 = ""
    private var text4 = ""
    private var text5 = ""
    private var text6 = ""
    private  lateinit var savedPhoneNumber : String
    private val prefManager = PrefManager()
    private var firstname = ""
    private var lastname = ""
    private var email = ""
    private var mobile  = ""
    private var birthday = ""
    private var password = ""
    private lateinit var interests : MutableList<String>
    private var displayImage = ""
    private var coverImage = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        inputFiveBinding = FragmentInputFiveDigitBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return inputFiveBinding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedPhoneNumber = prefManager.getPhoneNo(requireContext()).toString()

        mAuth = FirebaseAuth.getInstance()
        setupActionBar(inputFiveBinding.inputFiveDigitToolbar)
        configureTextFonts()

        inputFiveBinding.pleaseEnter.text = "${requireContext()
            .getString(R.string.pleaseEnterOtp)} $savedPhoneNumber"

        textListener()

        inputFiveBinding.sendCodeAgain.setOnClickListener {
            sendVerificationCode(savedPhoneNumber)
        }

        inputFiveBinding.btnVerify.setOnClickListener {
            Log.d("otp check is :", otp)

            verifyCode(otp)
        }
    }

    private fun verifyCode(code: String) {

        if(storedVerificationId != null) {
            val credential = PhoneAuthProvider.getCredential(storedVerificationId!!, code)
            signInWithPhoneAuthCredential(credential)
        }else{
            toastMessage("Couldn't pick up verification Id. \nEnsure you are signing with the phone that has the saved number", requireContext())
            Log.d("phone verification", "User isn't using he phone that has the saved number to sign up")
        }

    }

    //Sign in and also upload user account data
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        getUserDataObjectFromSharedPref()
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener {task ->
                if (task.isSuccessful){

                    val firebaseUser: FirebaseUser = task.result!!.user!! //How to access the user
                    val registeredPhoneNumber = firebaseUser.phoneNumber!!

                    val user = User(0,
                        firebaseUser.uid, firstname,
                        lastname, email,
                        registeredPhoneNumber.toLong(),
                        birthday,
                        interests,
                        displayImage,
                        coverImage
                        )

                        //FirestoreClass().registerUser(user, requireContext(), this)

                        toastMessage("Sign up successful", requireContext())
                        val intent = Intent(requireActivity(), MainActivity::class.java)
                        startActivity(intent)


                } else {
                    toastMessage("Registration failed", requireContext())
                }

            }
    }

    private fun sendVerificationCode(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {

            Log.d("Phone No Verification", "onVerificationCompleted:$credential")
            val code = credential.smsCode
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            toastMessage("Verification process failed.\n Please check your internet and try again", requireContext())
            Log.d(TAG, "onVerificationFailed", e)

            if (e is FirebaseAuthInvalidCredentialsException) {
                toastMessage("Verification process failed.\n Please check your phone number and try again", requireContext())

                Log.d(TAG, "onVerificationFailed; Invalid request", e)

                // Invalid request
            } else if (e is FirebaseTooManyRequestsException) {
                toastMessage("The SMS quota for the project has been exceeded", requireContext())
                Log.d(TAG, "onVerificationFailed; The SMS quota for the project has been exceeded", e)

            }

          }

        //A listener to automatically pick up the code sent and carry out the action if it sent to the same phone that's been used for registration
        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken) {

            super.onCodeSent(verificationId, token)
            Log.d(TAG, "onCodeSent:$verificationId")

            //Save verification ID  so we can use them later
            storedVerificationId = verificationId
        }
    }

    private fun configureTextFonts(){

        //resetting the font of the text to the appropriate one
        inputFiveBinding.etDigit1.typeface = initializeDMSansTypeFace(requireActivity())
        inputFiveBinding.etDigit2.typeface = initializeDMSansTypeFace(requireActivity())
        inputFiveBinding.etDigit3.typeface = initializeDMSansTypeFace(requireActivity())
        inputFiveBinding.etDigit4.typeface = initializeDMSansTypeFace(requireActivity())
        inputFiveBinding.etDigit5.typeface = initializeDMSansTypeFace(requireActivity())
        inputFiveBinding.fiveDigitCode.typeface = initializeDMSansTypeFace(requireActivity())
        inputFiveBinding.btnVerify.typeface = initializeDMSansTypeFace(requireActivity())
        inputFiveBinding.pleaseEnter.typeface = initializeDMSansTypeFace(requireActivity())
        inputFiveBinding.sendCodeAgain.typeface = initializeDMSansTypeFace(requireActivity())
        inputFiveBinding.confirmByMail.typeface = initializeDMSansTypeFace(requireActivity())
    }

    //WHen a text in typed in a text view, it should automatically take the user to the next one
    private fun textListener(){
        inputFiveBinding.etDigit1.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                if(inputFiveBinding.etDigit1.text.isNotEmpty()){
                    text1 = editable.toString()
                    otpStringBuilder()
                    inputFiveBinding.etDigit2.requestFocus()
                }
            }
        })
        inputFiveBinding.etDigit2.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                if(inputFiveBinding.etDigit1.text.isNotEmpty()){
                    text2 = editable.toString()
                    otpStringBuilder()
                    inputFiveBinding.etDigit3.requestFocus()
                }
            }
        })
        inputFiveBinding.etDigit3.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                if(inputFiveBinding.etDigit1.text.isNotEmpty()){
                    otpStringBuilder()
                    text3 = editable.toString()
                    inputFiveBinding.etDigit4.requestFocus()
                }
            }
        })
        inputFiveBinding.etDigit4.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                if(inputFiveBinding.etDigit1.text.isNotEmpty()){
                    text4 = editable.toString()
                    otpStringBuilder()
                    inputFiveBinding.etDigit5.requestFocus()
                }
            }
        })
        inputFiveBinding.etDigit5.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                if(inputFiveBinding.etDigit1.text.isNotEmpty()){
                    text5 = editable.toString()
                    otpStringBuilder()
                    inputFiveBinding.etDigit6.requestFocus()
                }
            }
        })

        inputFiveBinding.etDigit6.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                if(inputFiveBinding.etDigit1.text.isNotEmpty()){
                    text6 = editable.toString()
                    otpStringBuilder()
                }
            }
        })
    }

    //Initialise the otp that is typed in the different edittext views as one word
    private fun otpStringBuilder(){
        if (text1.isNotEmpty() && text2.isNotEmpty() && text3.isNotEmpty()
            && text4.isNotEmpty() && text5.isNotEmpty() && text6.isNotEmpty() ){
            otp = text1 + text2 + text3 + text4 + text5 + text6
            Log.d("otp is :", otp)
            resetBtnContinueToClickable(inputFiveBinding.btnVerify)
        }
    }

    private fun getUserDataObjectFromSharedPref(){
        firstname = prefManager.getFirstName(requireContext())!!
        lastname = prefManager.getLastName(requireContext())!!
        email = prefManager.getEmail(requireContext())!!
        birthday= prefManager.getBirthday(requireContext())!!
        interests = prefManager.getInterestList(requireContext())
        password = prefManager.getPassword(requireContext())!!
        mobile = prefManager.getPhoneNo(requireContext())!!
    }

}