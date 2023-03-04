package com.example.roomy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.roomy.onboarding.InputEmailFragment

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        val secondSplashFragment = SecondSplashFragment()
        val inputEmailFragment = InputEmailFragment()

        /*supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, secondSplashFragment)
            commit()
        }*/

    }
}