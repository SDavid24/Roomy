package com.example.roomy.ui.mainoperations

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import com.example.roomy.R
import com.example.roomy.databinding.ActivityMainBinding
import com.example.roomy.databinding.NavActivityMainBinding
import com.example.roomy.ui.mainoperations.home.HomeFragment
import com.example.roomy.ui.mainoperations.messaging.MessageFragment
import com.example.roomy.ui.mainoperations.notifications.NotificationsFragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.roomy.databinding.NavHeaderDrawerBinding
import kotlinx.coroutines.launch


class MainActivity : RoomyBaseActivity() {
    private val TAG: String = MainActivity::class.java.simpleName
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var mainBinding: ActivityMainBinding
    lateinit var navMainBinding: NavActivityMainBinding
    private val homeFragment = HomeFragment()
    private val notificationsFragment = NotificationsFragment()
    private val messageFragment = MessageFragment()

    //private val iconArray = arrayOf(R.drawable.nav_home2, R.drawable.nav_notification2, R.drawable.nav_message2)
   // private val iconArray2 = arrayOf(R.drawable.nav_home, R.drawable.nav_notification, R.drawable.nav_message)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        navMainBinding = NavActivityMainBinding.inflate(layoutInflater)
        navHeaderDrawerBinding = NavHeaderDrawerBinding.inflate(layoutInflater)
        navHeaderDrawerBinding = NavHeaderDrawerBinding.bind(navMainBinding.navView.getHeaderView(0))

        setContentView(navMainBinding.root)
        Log.d(TAG, "gets here for testing")

        //setContentView(R.layout.nav_activity_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        headerImg = navMainBinding.activityMainInNav.headerImg
        frameLayout = navMainBinding.activityMainInNav.flFragment
        appBarLayout = navMainBinding.activityMainInNav.appBarContainer

        navDrawerConfiguration(navMainBinding.navView, navMainBinding.navDrawerLayout, this)


        mainBinding.appBarContainer.elevation = 0f
        //setToolBar()
        //setupActionBar()

        //Setting fragment to display on load according to intent String
        val fragmentType = intent.getStringExtra("fragmentType")

        if (fragmentType != null) {
            when (fragmentType) {
                "homeFragment" -> loadFragment(homeFragment)
                "notificationsFragment" -> loadFragment(notificationsFragment)
                "messageFragment" -> loadFragment(messageFragment)
                // Handle other fragment types if needed
            }
        } else {
            loadFragment(homeFragment)
        }

        lifecycleScope.launch {
            configureUserPage()
        }

        bottomNavClickListener(8, navMainBinding.activityMainInNav.bottomNavigation, this)
    }

    //Get the user's details and customise his page par his details
    private suspend fun configureUserPage(){
        user = userViewModel.getUser()!!

        Glide.with(this)
            .load(user.displayImage) // Provide the file path
            .into(headerImg)

        Glide.with(this)
            .load(user.displayImage) // Provide the file path
            .into(navHeaderDrawerBinding.acctImage)

        navHeaderDrawerBinding.acctEmail.text = user.email
        navHeaderDrawerBinding.acctName.text = "${user.firstName} ${user.lastName}"
    }

    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.flFragment,fragment)
        transaction.commit()

        // Reset the app bar state
        appBarLayout.setExpanded(true, true)
    }

}