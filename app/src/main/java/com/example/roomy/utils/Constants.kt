package com.example.roomy.utils

import android.app.Activity
import android.net.Uri
import android.webkit.MimeTypeMap

object Constants {
    const val USERS: String = "users"

    const val IMAGE_DIRECTORY = "roomy"
    const val USER_IMAGE_DIRECTORY = "/roomy/user/image"
    const val CAMERA_CODE = 100
    const val GALLERY_REQUEST_CODE = 200

    //User details
    const val EMAIL = "email"
    const val FIRSTNAME = "firstName"
    const val LASTNAME = "lastName"
    const val GENDER = "gender"
    const val BIRTHDAY = "birthday"
    const val PHONENO = "phoneNumber"
    const val INTERESTSLIST = "interestsList"
    const val PASSWORD = "password"
    const val ENCRYPTED_PASSWORD = "encryptedPassword"
    const val ENCRYPTED_SHARED_PREF = "RoomyEncrypted"

    const val DISPLAY_IMAGE = "DISPLAY_IMAGE"
    const val COVER_IMAGE = "COVER_IMAGE"
    //const val COVER_IMAGE_URI_STRING = "COVER_IMAGE_URI_STRING"

    /**A function to get the extension of selected image.*/
    fun getFileExtension(activity: Activity, uri: Uri) : String?{
        /*
         * MimeTypeMap: Two-way map that maps MIME-types to file extensions and vice versa.
         *
         * getSingleton(): Get the singleton instance of MimeTypeMap.
         *
         * getExtensionFromMimeType: Return the registered extension for the given MIME type.
         *
         * contentResolver.getType: Return the MIME type of the given content URL.
         */
        return  MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(activity.contentResolver.getType(uri))
    }
}