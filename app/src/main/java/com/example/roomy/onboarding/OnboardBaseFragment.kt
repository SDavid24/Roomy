package com.example.roomy.onboarding

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.roomy.MainActivity
import com.example.roomy.utils.PrefManager
import com.example.roomy.R
import com.example.roomy.databinding.FragmentOnboardBaseBinding

open class OnboardBaseFragment : Fragment() {
    private lateinit var onboardBaseBinding: FragmentOnboardBaseBinding
    //var clicked: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        onboardBaseBinding = FragmentOnboardBaseBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return onboardBaseBinding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun setupActionBar(toolbar: Toolbar){
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        if (actionBar != null){

            actionBar.elevation = 0F
            actionBar.title = null
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back)

            Log.d("ActionBar: ", "It is NOT null")

        }

        //Making the "back" able to take the activity back to the previous activity
        toolbar.setNavigationOnClickListener {
            //onBackPressed()
        }

        ////  set status bar color to white
        (activity as AppCompatActivity?)!!.window.statusBarColor = resources.getColor(R.color.white);

        (activity as AppCompatActivity?)!!.window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) //  set status bar texts to a dark contrast

    }

    //Resetting the Continue button
    fun resetBtnContinueToClickable(textView: TextView,) {
        textView.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_green_btn, null)
        textView.setTextColor(
            ResourcesCompat.getColor(
                resources,
                R.color.white, null
            )
        )
        textView.isClickable = true
    }

    fun resetContinueBtnToClickable(textView: TextView, fragment: Fragment,) {
        textView.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_green_btn, null)


        textView.setTextColor(
            ResourcesCompat.getColor(
                resources,
                R.color.white, null
            )
        )

        textView.isClickable = true

        textView.setOnClickListener {
            //clicked = true
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, fragment)
                addToBackStack(null)
                commit()
            }
        }
    }

    fun resetContinueBtnToClickable2(textView: TextView, fragment: Fragment, prefManager: PrefManager, interestList: ArrayList<String>, context: Context) {
        textView.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_green_btn, null)

        textView.setTextColor(
            ResourcesCompat.getColor(
                resources,
                R.color.white, null
            )
        )

        textView.isClickable = true

        textView.setOnClickListener {
            prefManager.setInterestList(interestList, context)
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, fragment)
                addToBackStack(null)
                commit()
            }
        }
    }

    //Resetting the Continue button
    fun revertContinueBtnToUnclickable(textView: TextView,  clicked: Boolean = false) {
        textView.background = ResourcesCompat.getDrawable(resources,
            R.drawable.bg_faded_green_btn, null)

        textView.setTextColor(
            ResourcesCompat.getColor(
                resources,
                R.color.edit_text_bg_grey, null
            )
        )
        textView.isClickable = false
    }

    fun initializeDMSansTypeFace(context: Context) : Typeface{
        return Typeface.createFromAsset(context.assets, "DMSans.ttf")
    }

    fun toastMessage(message : String, context: Context){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


    /**Method that handles the immediate afterwards of the sign up process*/
    fun userRegisteredSuccess(){
        toastMessage("You have successfully registered", requireContext())

        //hideProgressDialog()
        //FirebaseAuth.getInstance().signOut()
        //finish()
    }




   /* fun signInSuccess(user: User) {
        hideProgressDialog()

        startActivity(Intent(this, MainActivity::class.java))
        finish() //Finishes the activity

    }*/


    /**Method to show the circling progress dialog when something is loading*/
    /*fun showProgressDialog(text: String){

        *//*Set the screen content from a layout resource
        The resource will be inflated, adding all top-level views to the screen
         *//*
        mProgressDialog = Dialog(this)
        mProgressDialog.setContentView(R.layout.dialog_progress)  //Setting the circling progress icon
        mProgressDialog.tv_progress_text.text = text   //Setting the text

        //Starts the dialog and displays is on the screen
        mProgressDialog.show()
    }*/

    /**Method to dismiss dialog*/
    fun hideProgressDialog(){
       // mProgressDialog.dismiss()
    }

}