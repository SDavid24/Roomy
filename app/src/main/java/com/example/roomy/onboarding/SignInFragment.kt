package com.example.roomy.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.roomy.R
import com.example.roomy.databinding.FragmentSignInBinding
import com.example.roomy.databinding.FragmentSignupOptionsBinding


class SignInFragment : Fragment() {
    private lateinit var signInBinding : FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        signInBinding = FragmentSignInBinding.inflate(inflater, container, false)
        return (signInBinding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}