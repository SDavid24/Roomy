package com.example.roomy.ui.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.roomy.R
import com.example.roomy.ui.onboarding.splashscreens.SecondSplashFragment
import com.example.roomy.ui.onboarding.splashscreens.SignInFragment
import com.example.roomy.ui.onboarding.splashscreens.SignupOptionsFragment
import com.example.roomy.ui.onboarding.signup.*

class OnBoardingActivity : AppCompatActivity() {
    private val  TAG = "OnBoardingActivity"
    private val firstSplashFragment = FirstSplashFragment()
    private val secondSplashFragment = SecondSplashFragment()
    private val signupOptionsFragment = SignupOptionsFragment()
    private val inputEmailFragment = InputEmailFragment()
    private val inputNamesFragment = InputNamesFragment()
    private val chooseGenderFragment = ChooseGenderFragment()
    private val inputPasswordFragment = InputPasswordFragment()
    private val inputPhoneNoFragment = InputPhoneNoFragment()
    private val inputFiveDigitFragment = InputFiveDigitFragment()
    private val inputBirthdayFragment = InputBirthdayFragment()
    private val uploadFirstPhotosFragment = UploadFirstPhotosFragment()
    private val onboardBaseFragment = OnboardBaseFragment()
    private val addInterestFragment = AddInterestFragment()
    private val signInFragment = SignInFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        Log.d(TAG, "got to stage 1")
       /* supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, uploadFirstPhotosFragment)
            commit()
        }*/

        splashLoad()

    }


    private fun splashLoad(){

        Handler().postDelayed({

            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, secondSplashFragment)
                commit()

            }

        /*

          val currentUserId = FireStoreClass().getCurrentUserId()


  if (currentUserId.isNotEmpty()){
                  startActivity(Intent(this, MainActivity::class.java))
              }else{
                  startActivity(Intent(this, IntroActivity::class.java))
              }*/

        }, 2500
        )

    }

}