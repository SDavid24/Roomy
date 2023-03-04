package com.example.roomy.onboarding

import android.content.Context
import android.util.Log
import com.example.roomy.dataobject.User
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
