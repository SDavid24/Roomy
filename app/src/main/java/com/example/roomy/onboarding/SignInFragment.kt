package com.example.roomy.onboarding

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.roomy.MainActivity
import com.example.roomy.R
import com.example.roomy.databinding.FragmentSignInBinding
import com.example.roomy.dataobject.User
import com.example.roomy.firebase.FirestoreClass
import com.google.firebase.auth.FirebaseAuth


class SignInFragment : BaseFragment() {
    private lateinit var signInBinding : FragmentSignInBinding
    val auth = FirebaseAuth.getInstance()
    private val signupOptionsFragment = SignupOptionsFragment()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        signInBinding = FragmentSignInBinding.inflate(inflater, container, false)
        return (signInBinding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnClickListener()
    }

    /**Method that gets the form filled from the validateForm() and uses the form to sign up
     *  a user using Firebase*/
    fun signInUser(){
        //trim function is to delete any empty space if the user mistakenly leave one after he fills his details
        val email : String = signInBinding.etEmail.text.toString().trim{it <= ' '}
        val password : String = signInBinding.etPassword.text.toString().trim{it <= ' '}

        if(validateSignInForm(email, password)){
            showProgressDialog(resources.getString(R.string.please_wait))   //First shows loading sign
            //How to sign in the user account using email and password
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                        task ->
                    //hideProgressDialog()

                    if (task.isSuccessful) {

                        FirestoreClass().loadUserData(this)

                    }else{
                        Toast.makeText(requireContext(),
                            "Failed to login account", Toast.LENGTH_LONG ).show()
                        hideProgressDialog()
                    }
                }
        }

    }

    /**Method that handles the immediate afterwards of the sign in process*/
    fun signInUserSuccess(user: User) {
        Toast.makeText(requireContext(), "Welcome!", Toast.LENGTH_LONG).show()
        hideProgressDialog()

        Log.e("Location", "It reaches here")
        startActivity(Intent(requireContext(), MainActivity::class.java))

        requireActivity().finish()
    }

    /**Method to get the details from the sign in form and also a conditional to check if
     *  none is blank*/
    fun validateSignInForm(email: String, password: String) : Boolean{
        return when {

            TextUtils.isEmpty(email) ->{
                showSnackBar("Please fill in your Email Id")
                false
            }
            TextUtils.isEmpty(password) ->{
                showSnackBar("Please fill in your password")
                false
            }

            else -> {
                true
            }
        }

    }

    fun btnClickListener() {
        signInBinding.btnCreateAcct.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, signupOptionsFragment)
                addToBackStack(null)
                commit()
            }
        }

        signInBinding.btnLogin.setOnClickListener {
            signInUser()
        }
    }
}