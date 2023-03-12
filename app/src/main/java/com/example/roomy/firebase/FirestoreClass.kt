package com.example.roomy.firebase

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.roomy.dataobject.User
import com.example.roomy.onboarding.SignInFragment
import com.example.roomy.onboarding.UploadFirstPhotosFragment
import com.example.roomy.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirestoreClass {
    private val mFirestore = FirebaseFirestore.getInstance()

    /**Method that handles adding user info into the database during signup using the generated UID*/
    fun registerUser(userInfo: User, context: Context, fragment: UploadFirstPhotosFragment){
        mFirestore.collection(Constants.USERS)
            .document(getCurrentUserId())   //Creating a database document for each user that's created by using their UIDs which is gotten from the authentication side
            .set(userInfo, SetOptions.merge()) //This sets & merges all the user Info that's passed
            .addOnSuccessListener {
                fragment.signInUserSuccess(context)
                Log.d("User account upload","Success!")

            }.addOnFailureListener {
                Log.d("User account upload","failed!")
            }
    }

    /**Method that handles the calling of all the data of a user that's in the database
     *  when he signs in or updates his profile*/
    fun loadUserData(fragment: Fragment, readBoardsList: Boolean = false){
        mFirestore.collection(Constants.USERS)
            .document(getCurrentUserId())//gets the database document of the user that's signed in by using their UIDs which is gotten from the authentication side
            .get()  //gets the code
            .addOnSuccessListener {document ->//This runs a code of our wish if the login is successful
                val loggedInUser = document.toObject(User::class.java)
                if(loggedInUser != null){
                    when(fragment){
                        is SignInFragment ->
                            fragment.signInUserSuccess(loggedInUser)

                       /* is SignUpActivity ->
                            activity.signInUserSuccess(loggedInUser)

                        is MainActivity ->
                            activity.updateNavigationUserData(loggedInUser)

                        is ProfileActivity ->
                            activity.setDataIntoUI(loggedInUser)

                        is BoardsListActivity ->
                            activity.getUserFirstNameAndBoardList(loggedInUser, readBoardsList)*/

                    }
                }
            }.addOnFailureListener{ //This runs a code of our wish if the login fails
                    e ->
                Log.e("SignInUser", "Error writing document", e)
            }
    }


    /**Function to to get the user current  Id
     * AUTOLOGIN PROCESS
     * This also checks if there is an already set UID so it can go straight to the main activity page. And if there isn't, it just goes to the Intro activity for either sign in or sign up*/
    fun getCurrentUserId(): String {

        val currentUser =  FirebaseAuth.getInstance().currentUser
        var currentUserId = ""
        if (currentUser != null){
            currentUserId = currentUser.uid
        }
        return currentUserId

    }
}
