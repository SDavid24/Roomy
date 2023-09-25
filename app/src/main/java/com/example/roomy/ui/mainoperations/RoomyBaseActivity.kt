package com.example.roomy.ui.mainoperations

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.roomy.R
import com.example.roomy.viewmodel.UserViewModel
import com.example.roomy.databinding.NavHeaderDrawerBinding
import com.example.roomy.dataobject.User
import com.example.roomy.ui.mainoperations.home.HomeFragment
import com.example.roomy.ui.mainoperations.messaging.MessageFragment
import com.example.roomy.ui.mainoperations.notifications.NotificationsFragment
import com.example.roomy.ui.mainoperations.profile.ProfileActivity
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.navigation.NavigationView

open class RoomyBaseActivity : AppCompatActivity() {
    lateinit var userViewModel: UserViewModel
    private val homeFragment = HomeFragment()
    private val notificationsFragment = NotificationsFragment()
    private val messageFragment = MessageFragment()
    lateinit var navHeaderDrawerBinding: NavHeaderDrawerBinding
    lateinit var headerImg: de.hdodenhof.circleimageview.CircleImageView
    lateinit var frameLayout: FrameLayout
    lateinit var appBarLayout: AppBarLayout
    lateinit var user: User



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_roomy_base)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        navHeaderDrawerBinding = NavHeaderDrawerBinding.inflate(layoutInflater)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

    }

    fun bottomNavClickListener(notifications : Int, bottomNavigationView: BottomNavigationView, activity: Activity){
        //shows the red small icon of number notifications
        val badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.notificationView)
        badgeDrawable.isVisible = true
        badgeDrawable.number = notifications

        when (activity){
             is ProfileActivity ->{
                 bottomNavigationView.setOnNavigationItemSelectedListener { item ->
                     when (item.itemId) {
                         R.id.homeView -> {
                             Toast.makeText(
                                 applicationContext,
                                 "Clicked Home", Toast.LENGTH_SHORT
                             ).show()
                             // Change the icon of the first navigation item
                             item.setIcon(R.drawable.nav_home2)
                             startMainActivity("homeFragment")

                         }
                         R.id.notificationView -> {
                             //appBarLayout.
                             // Change the icon of the second navigation item
                             item.setIcon(R.drawable.nav_notification2)
                             startMainActivity("notificationsFragment")

                         }
                         R.id.messagesView -> {
                             // Change the icon of the third navigation item
                             item.setIcon(R.drawable.nav_message2)
                             startMainActivity("messageFragment")
                         }
                     }
                     true
                 }

             }

            is MainActivity ->{
                bottomNavigationView.setOnNavigationItemSelectedListener { item ->
                    when (item.itemId) {
                        R.id.homeView -> {
                            Toast.makeText(
                                applicationContext,
                                "Clicked Home", Toast.LENGTH_SHORT
                            ).show()
                            // Change the icon of the first navigation item
                            item.setIcon(R.drawable.nav_home2)
                            loadFragment(homeFragment, frameLayout)

                        }
                        R.id.notificationView -> {
                            // Change the icon of the second navigation item
                            item.setIcon(R.drawable.nav_notification2)
                            loadFragment(notificationsFragment, frameLayout)
                        }
                        R.id.messagesView -> {
                            // Change the icon of the third navigation item
                            item.setIcon(R.drawable.nav_message2)
                            loadFragment(messageFragment, frameLayout)
                        }
                    }
                    true
                }

            }
        }


    }

    /**Functionality to configure the drawer layout and Navigation view*/
    fun navDrawerConfiguration(navView: NavigationView, drawerLayout: DrawerLayout,
                                       activity: Activity){


        val toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)

        toggle.syncState()

        when(activity){
            is MainActivity -> {
                headerImg.setOnClickListener {
                    drawerLayout.open()
                }
            }
        }

        /**OnclickListener for the hamburger menu*/
        when(activity){
            is MainActivity -> {
                navView.setNavigationItemSelectedListener {

                    when (it.itemId) {
                        R.id.nav_home_drawer
                        -> {

                            navView.visibility = View.VISIBLE
                            appBarLayout.visibility = View.VISIBLE

                            drawerLayout.close()
                            loadFragment(homeFragment, frameLayout)
                        }

                        R.id.nav_profile_drawer -> {
                            startProfileActivity()
                        }

                        R.id.nav_messaging_drawer -> {
                            navView.visibility = View.VISIBLE
                            appBarLayout.visibility = View.VISIBLE

                            drawerLayout.close()

                            loadFragment(messageFragment, frameLayout)
                        }

                        R.id.nav_notifications_drawer -> {

                            navView.visibility = View.VISIBLE
                            appBarLayout.visibility = View.VISIBLE

                            drawerLayout.close()
                            loadFragment(notificationsFragment, frameLayout)
                        }
                    }

                    true
                }

            }

            is ProfileActivity -> {
                navView.setNavigationItemSelectedListener {

                    when (it.itemId) {
                        R.id.nav_home_drawer
                        -> {
                            startMainActivity("homeFragment")
                        }

                        R.id.nav_profile_drawer -> {
                            startProfileActivity()
                        }

                        R.id.nav_messaging_drawer -> {
                            startMainActivity("messageFragment")
                        }

                        R.id.nav_notifications_drawer -> {
                            startMainActivity("notificationsFragment")
                        }
                    }

                    true
                }

            }
        }

    }


    private fun loadFragment(fragment: Fragment, layout: FrameLayout){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(layout.id, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

        // Reset the app bar state
        appBarLayout.setExpanded(true, true)

    }

    private fun startMainActivity(fragment: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("fragmentType", fragment)
        startActivity(intent)
    }
    private fun startProfileActivity() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }


    //Configure the behaviour of picking the interests, adding it to an arrraylist
     fun setInterestChips(chipGroup: ChipGroup, interests : ArrayList<String>) {

        /*interests.add("Books")
        interests.add("Football")
        interests.add("Travel")
        interests.add("Cartoons")
        interests.add("Karaoke")
        interests.add("Messi")*/

        for (interest in interests) {
            val chip =
                LayoutInflater.from(this).inflate(R.layout.item_interest_chip, null) as Chip

            chip.text = interest
            chip.setTextColor(resources.getColor(R.color.custom_red))

            chip.isClickable = false
            chipGroup.addView(chip)

        }
        Log.d("ProfileActivity", "user loaded interest is: $interests")

    }


}