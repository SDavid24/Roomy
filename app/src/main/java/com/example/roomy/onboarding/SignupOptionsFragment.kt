package com.example.roomy.onboarding

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.roomy.databinding.FragmentSignupOptionsBinding

class SignupOptionsFragment : Fragment() {

    private lateinit var signupOptionsBinding : FragmentSignupOptionsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        signupOptionsBinding = FragmentSignupOptionsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return (signupOptionsBinding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tv = TextView(requireContext())
        val toolBarTypeface: Typeface =
            Typeface.createFromAsset(requireActivity().assets, "snapitc.ttf")

        tv.typeface = toolBarTypeface
        tv.text = "ROOMY"

        tv.textSize = 24F;
        tv.setTextColor(Color.WHITE);

        (activity as AppCompatActivity?)!!.supportActionBar?.elevation =
            0f //code to remove shadow beneath the action bar
        (activity as AppCompatActivity?)!!.supportActionBar?.displayOptions =
            ActionBar.DISPLAY_SHOW_CUSTOM
        (activity as AppCompatActivity?)!!.supportActionBar?.customView = tv

        //resetting the font of the text to the appropriate one
        val typeface: Typeface = Typeface.createFromAsset(requireActivity().assets, "DMSans.ttf")
        signupOptionsBinding.tvJoinNow.typeface = typeface
        signupOptionsBinding.tvJoinWithApple.typeface = typeface
        signupOptionsBinding.tvJoinWithGoogle.typeface = typeface
    }


}