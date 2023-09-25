package com.example.roomy.ui.onboarding.signup

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.*
import android.graphics.Bitmap
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.roomy.ui.mainoperations.MainActivity
import com.example.roomy.R
import com.example.roomy.viewmodel.UserViewModel
import com.example.roomy.utils.Constants.CAMERA_CODE
import com.example.roomy.utils.Constants.GALLERY_REQUEST_CODE
import com.example.roomy.utils.Constants.IMAGE_DIRECTORY
import com.example.roomy.databinding.FragmentUploadFirstPhotosBinding
import com.example.roomy.dataobject.User
import com.example.roomy.ui.firebase.FirestoreClass
import com.example.roomy.ui.onboarding.OnBoardingActivity
import com.example.roomy.ui.onboarding.OnboardBaseFragment
import com.example.roomy.utils.Constants
import com.example.roomy.utils.PrefManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

class UploadFirstPhotosFragment : OnboardBaseFragment() {

    private lateinit var uploadFirstPhotosBinding: FragmentUploadFirstPhotosBinding
    private lateinit var userViewModel: UserViewModel
    private var TAG = "UploadFirstPhotosFragment"
    private var contentURI : Uri? = null
    private var saveImageToInternalStorageUri : Uri? = null
    private var saveDisplayImageToInternalStorageUri : Uri? = null
    private var saveCoverImageToInternalStorageUri : Uri? = null
    private var position : Int  = 0
    private val prefManager = PrefManager()
    private var firstname = ""
    private var lastname = ""
    private var email = ""
    private var mobile = ""
    private var birthday = ""
    private lateinit var interests : MutableList<String>
    private var password = ""
    private var displayImage = ""
    private var coverImage = ""
    private var displayImageUploaded = false
    private var coverImageUploaded = false
    private var isCoverImageAdded: Boolean? = null
    private var isDisplayImageAdded : Boolean? = null


    var mSelectedImageFileUri : Uri? = null
    var mProfileImageFileUri : String = ""   //Initializing the downloadable URI

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        uploadFirstPhotosBinding =
            FragmentUploadFirstPhotosBinding.inflate(inflater, container, false)
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        // Inflate the layout for this fragment
        return uploadFirstPhotosBinding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionBar(uploadFirstPhotosBinding.uploadFirstPhotosToolbar)

        //Add the fb logo
        Glide.with(requireContext()).load(R.drawable.fblogo).into(uploadFirstPhotosBinding.fbLogo)

        loadImageOnCreate()
        configureTextFonts()
        onClickListeners()
    }

    private fun configureTextFonts() {
        //resetting the font of the text to the appropriate one
        val sfProTypeface: Typeface =
            Typeface.createFromAsset(requireActivity().assets, "SFProText.ttf")

        uploadFirstPhotosBinding.almostThere.typeface = initializeDMSansTypeFace(requireActivity())
        uploadFirstPhotosBinding.uploadMessage.typeface = sfProTypeface
        uploadFirstPhotosBinding.addFromFB.typeface = initializeDMSansTypeFace(requireActivity())
        uploadFirstPhotosBinding.btnSignUp.typeface = initializeDMSansTypeFace(requireActivity())

    }

    private fun imageDialogPicker() {
        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Update profile picture")
        builder.setItems(
            options
        ) { dialog: DialogInterface, item: Int ->
            when (options[item].toString()) {
                "Take Photo" -> takePhotoFromCamera()
                "Choose from Gallery" -> chooseFromPhotoGallery()
                else -> dialog.dismiss()
            }
        }
        builder.show()

    }

    /** A method is used for image selection from GALLERY / PHOTOS of phone storage.*/
    private fun chooseFromPhotoGallery() {
        // Asking the permissions of Storage using DEXTER Library which we have added in gradle file.
        Dexter.withContext(requireContext())
            .withPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    // Here after all the permission are granted launch the gallery to select and image.
                    if (report!!.areAllPermissionsGranted()) {
                        val galleryIntent = Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        )
                        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest?>?,
                    token: PermissionToken?
                ) {
                    showRationalDialogForPermissions()
                }
            }).onSameThread().check()
    }


    // This function is created to show the alert dialog when the permissions are denied and need to allow it from settings app info.)
    private fun showRationalDialogForPermissions() {
        android.app.AlertDialog.Builder(requireContext()).setMessage(
            "It looks like you have turned off " +
                    "permissions required for this feature. " +
                    "It can be enabled under the Application settings"
        )
            .setPositiveButton("GO TO SETTINGS") { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", (activity as MainActivity).packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }


    /**A method used for asking the permission for camera and storage and image capturing and selection from Camera. */
    private fun takePhotoFromCamera() {
        // Asking the permissions of Storage using DEXTER Library which we have added in gradle file.
        Dexter.withContext(requireContext())
            .withPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    // Here after all the permission are granted launch the gallery to select and image.
                    if (report!!.areAllPermissionsGranted()) {
                        val galleryIntent = Intent(
                            MediaStore.ACTION_IMAGE_CAPTURE
                        )
                        startActivityForResult(galleryIntent, CAMERA_CODE)
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest?>?,
                    token: PermissionToken?
                ) {
                    showRationalDialogForPermissions()
                }
            }).onSameThread().check()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQUEST_CODE) {
                if (data != null) {
                    contentURI = data.data

                    try {
                        // Here this is used to get a bitmap from URI
                        @Suppress("DEPRECATION")

                        val selectedImageBitmap =
                            MediaStore.Images.Media.getBitmap(activity?.contentResolver, contentURI)

                        saveImageToInternalStorageUri =
                            saveImageToInternalStorage(selectedImageBitmap)
                        Log.d("Saved image: ", "Path :: $saveImageToInternalStorageUri")

                        if(position == 1) {
                            loadDisplayImageIntoIV()
                           /* Glide.with(requireContext()).load(saveImageToInternalStorageUri)
                                .into(uploadFirstPhotosBinding.addedImage1)
                            uploadFirstPhotosBinding.addedImage1.visibility = View.VISIBLE
                            uploadFirstPhotosBinding.icAdd1.visibility = View.GONE
                            //uploadFirstPhotosBinding.addedImage2!!.setImageBitmap(thumbNail)
                            uploadFirstPhotosBinding.addedImage1.background  =
                                ResourcesCompat.getDrawable(resources, R.color.white, null )

                            saveDisplayImageToInternalStorageUri = saveImageToInternalStorageUri
                            displayImage = saveImageToInternalStorageUri.toString()
                            prefManager.setDisplayImage(coverImage, requireContext())
                            onClickListeners()
                            Log.d("N display ImageUri: ", displayImage)*/
                        }else {
                            loadCoverImageIntoIV()

                          /*  Glide.with(requireContext()).load(saveImageToInternalStorageUri)
                                .into(uploadFirstPhotosBinding.addedImage2)
                            uploadFirstPhotosBinding.addedImage2.visibility = View.VISIBLE
                            uploadFirstPhotosBinding.icAdd2.visibility = View.GONE

                            //uploadFirstPhotosBinding.addedImage2!!.setImageBitmap(thumbNail)
                            uploadFirstPhotosBinding.addedImage2.background  =
                                ResourcesCompat.getDrawable(resources, R.color.white, null )

                            saveCoverImageToInternalStorageUri = saveImageToInternalStorageUri
                            coverImage = saveImageToInternalStorageUri.toString()
                            prefManager.setCoverImage(coverImage, requireContext())
                            onClickListeners()
                            Log.d("N display ImageUri: ", coverImage)
*/
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Toast.makeText(
                            requireContext(),
                            "Failed to load the image from gallery", Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            } else if (requestCode == CAMERA_CODE) {
                val thumbNail: Bitmap = data!!.extras!!.get("data") as Bitmap

                saveImageToInternalStorageUri = saveImageToInternalStorage(thumbNail)

                Log.e("Saved image: ", "Path :: $saveImageToInternalStorageUri")

                if(position == 1) {
                    loadDisplayImageIntoIV()

                }else {
                  loadCoverImageIntoIV()
                }
            }
        }
    }

    /**
     * A function to save a copy of an image to internal storage for RoomyApp to use.
     */
    private fun saveImageToInternalStorage(bitmap: Bitmap): Uri {
        // Get the context wrapper instance
        val wrapper = ContextWrapper(activity as OnBoardingActivity)

        // Initializing a new file
        // The below line return a directory in internal storage

        /**
         * The Mode Private here is
         * File creation mode: the default mode, where the created file can only
         * be accessed by the calling application (or all applications sharing the
         * same user ID).
         */
        var file = wrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)

        // Create a file to save the image
        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            val stream: OutputStream =
                FileOutputStream(file) //Inorder to output an image to our phone
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)  // Compress bitmap

            stream.flush()   // Flush the stream

            stream.close()   // Close stream

        } catch (e: IOException) {
            e.printStackTrace()
        }

        //return Uri.parse(file.absolutePath)
        return Uri.fromFile(File(file.absolutePath))
    }

    private  fun onClickListeners(){
        uploadFirstPhotosBinding.icAdd1.setOnClickListener {
            position = 1
            imageDialogPicker()
        }
        uploadFirstPhotosBinding.addedImage1.setOnClickListener {
            position = 1
            imageDialogPicker()
        }
        uploadFirstPhotosBinding.icAdd2.setOnClickListener {
            position = 2
            imageDialogPicker()
        }
        uploadFirstPhotosBinding.addedImage2.setOnClickListener {
            position = 2
            imageDialogPicker()
        }

        if(prefManager.getCoverImage(requireContext()) != null){
        }

        if(prefManager.getCoverImage(requireContext()) != null && prefManager.getDisplayImage(requireContext()) != null) {

            resetBtnContinueToClickable(uploadFirstPhotosBinding.btnSignUp)
        }

        uploadFirstPhotosBinding.btnSignUp.setOnClickListener {
            Toast.makeText(requireContext(), "Signup Button clicked!", Toast.LENGTH_SHORT).show()
            kickOffProcess()
        }
    }


    /**the function kicks off the sign up process.*/

    private fun kickOffProcess() {
        //showProgressDialog(resources.getString(R.string.please_wait))  //First shows loading sign
        getUserDataObjectFromSharedPref()
        signUpUser()
        //uploadUserDisplayImage()

    }

    /**A function to upload the selected user images to firebase cloud storage.*/
    private fun uploadUserDisplayImage() {
        //showProgressDialog(resources.getString(R.string.please_wait))

        if(saveDisplayImageToInternalStorageUri != null){
            val sRef : StorageReference = FirebaseStorage.getInstance()
                .reference.child(Constants.DISPLAY_IMAGE
                        + System.currentTimeMillis() + "."
                        + Constants.getFileExtension(requireActivity(),
                    saveDisplayImageToInternalStorageUri!!)
                )

            Log.d("Image upload", "display Not null!")

            //adding the file to reference
            sRef.putFile(saveDisplayImageToInternalStorageUri!!).addOnSuccessListener {
                // The image upload is success
                    taskSnapshot ->
                Log.d("Image upload", "display upload Success!")

                displayImageUploaded = true

                Log.i( "Firebase Image URL", taskSnapshot.metadata!!
                    .reference!!.downloadUrl.toString())

                // Get the downloadable url from the task snapshot
                taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                        uri->
                    Log.d("Down Display ImageURL", uri.toString())

                    uploadUserCoverImage()
                    // assign the image url to the variable.
                    //displayImage = uri.toString()

                    //hideProgressDialog()
                }
            }.addOnFailureListener{
                    exception ->
                toastMessage(exception.message!!, requireContext())
                Log.d("Image upload", "display upload failed!")

                //hideProgressDialog()
            }
        }

    }

    /**A function to upload the selected user cover image to firebase cloud storage.*/
    private fun uploadUserCoverImage() {
        //showProgressDialog(resources.getString(R.string.please_wait))

        if(saveCoverImageToInternalStorageUri != null){
            val sRef : StorageReference = FirebaseStorage.getInstance()
                .reference.child(Constants.COVER_IMAGE
                        + System.currentTimeMillis() + "."
                        + Constants.getFileExtension(requireActivity(), saveCoverImageToInternalStorageUri!!)
                )
            Log.d("Image upload", "cover Not null!")

            //adding the file to reference
            sRef.putFile(saveCoverImageToInternalStorageUri!!).addOnSuccessListener {
                // The image upload is success
                    taskSnapshot ->

                Log.d("Image upload", "cover upload Success!")

                coverImageUploaded = true

                Log.i( "Firebase Image URL", taskSnapshot.metadata!!
                    .reference!!.downloadUrl.toString())

                // Get the downloadable url from the task snapshot
                taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                        uri->
                    Log.d("Down Display ImageURL", uri.toString())

                    //signUpUser()
                    // assign the image url to the variable.
                    coverImage = uri.toString()

                    //hideProgressDialog()
                }
            }.addOnFailureListener{
                    exception ->
                toastMessage(exception.message!!, requireContext())
                Log.d("Image upload", "cover upload failed!")

                //hideProgressDialog()
            }
        }

    }

    /**Method that gets the form filled from the validateForm() and uses the form to sign up a user using Firebase
     */
    private fun signUpUser() {
        //showProgressDialog(resources.getString(R.string.please_wait))

        //How to create the user account using email and password
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(
                    email, password
                )
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        uploadUserCoverImage()

                        val firebaseUser: FirebaseUser =
                            task.result!!.user!! //How to access the user
                        val registeredEmail = firebaseUser.email!!
                        val user = User(0,
                            firebaseUser.uid,
                            firstname,
                            lastname,
                            registeredEmail,
                            mobile.toLong(),
                            birthday,
                            interests,
                            displayImage,
                            coverImage
                        )

                        userViewModel.saveNewUser(user) //Save to local db
                        FirestoreClass().registerUser(user, requireContext(), this) //Save to Firebase

                    } else {
                        toastMessage("Registration failed", requireContext())

                    }
                }.addOnFailureListener { exception ->
                    Log.d("Registration failed", exception.message!!)

                }

/*
        if(displayImageUploaded && coverImageUploaded) {
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(
                    email, password
                ).addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser =
                            task.result!!.user!! //How to access the user
                        val registeredEmail = firebaseUser.email!!
                        val user = User(
                            firebaseUser.uid,
                            firstname,
                            lastname,
                            registeredEmail,
                            mobile.toLong(),
                            birthday,
                            interests,
                            displayImage,
                            coverImage
                        )

                        FirestoreClass().registerUser(user, requireContext(), this)

                    } else {
                        toastMessage("Registration failed", requireContext())

                    }
                }.addOnFailureListener { exception ->
                    Log.d("Registration failed", exception.message!!)

                }
        }
*/

    }



    /**A function to update the user profile details into the database.*/
/*
    fun updateUserProfileDataInProfAct(){
        val userHashMap = HashMap<String, Any>()
        var anyChangesMade = false

        if(mProfileImageFileUri.isNotEmpty()
            && mProfileImageFileUri != mUserDetails.image){
            userHashMap[Constants.IMAGE] = mProfileImageFileUri
            anyChangesMade = true
        }

        if(et_name_profile.text.toString() != mUserDetails.name){
            userHashMap[Constants.NAME] = et_name_profile.text.toString()
            anyChangesMade = true
        }

        if(et_mobile_profile.text.toString() != mUserDetails.mobile.toString()){
            userHashMap[Constants.MOBILE] = et_mobile_profile.text.toString().toLong()
            anyChangesMade = true
        }

        // Update the data in the database.
        if (anyChangesMade) {
            FirestoreClass().updateUserProfileData(this, userHashMap)
        }else{
            Toast.makeText(this, "No change was made", Toast.LENGTH_SHORT).show()
        }
    }
*/

    private fun loadDisplayImageIntoIV(){
        Glide.with(requireContext()).load(saveImageToInternalStorageUri)
            .into(uploadFirstPhotosBinding.addedImage1)

        setBackgroundAfterDisplayImageAdded()

        saveDisplayImageToInternalStorageUri = saveImageToInternalStorageUri
        displayImage = saveImageToInternalStorageUri.toString()

        prefManager.setDisplayImage(displayImage, requireContext())
        onClickListeners()
    }

    private fun loadCoverImageIntoIV(){
        Glide.with(requireContext()).load(saveImageToInternalStorageUri)
            .into(uploadFirstPhotosBinding.addedImage2)

        setBackgroundAfterCoverImageAdded()

        saveCoverImageToInternalStorageUri = saveImageToInternalStorageUri
        coverImage = saveImageToInternalStorageUri.toString()

        prefManager.setCoverImage(coverImage, requireContext())
        onClickListeners()
    }

    private fun getUserDataObjectFromSharedPref(){
        firstname = prefManager.getFirstName(requireContext())!!
        lastname = prefManager.getLastName(requireContext())!!
        email = prefManager.getEmail(requireContext())!!
        birthday= prefManager.getBirthday(requireContext())!!
        interests = prefManager.getInterestList(requireContext())
        password = prefManager.getPassword(requireContext())!!
        mobile = prefManager.getPhoneNo(requireContext())!!
    }

    //This loads an image if there are images already save in the shared pref
    private fun loadImageOnCreate(){

        if(prefManager.getDisplayImage(requireContext()) != null){
            Glide.with(requireContext()).load(prefManager.getDisplayImage(requireContext())!!.toUri())
                .into(uploadFirstPhotosBinding.addedImage1)
            setBackgroundAfterCoverImageAdded()
        }

        if(prefManager.getCoverImage(requireContext()) != null){
            Glide.with(requireContext()).load(prefManager.getCoverImage(requireContext())!!.toUri())
                .into(uploadFirstPhotosBinding.addedImage2)

            setBackgroundAfterDisplayImageAdded()
        }

    }

    fun setBackgroundAfterCoverImageAdded(){
        uploadFirstPhotosBinding.addedImage2.visibility = View.VISIBLE
        uploadFirstPhotosBinding.icAdd2.visibility = View.GONE
        uploadFirstPhotosBinding.addedImage2.background  =
            ResourcesCompat.getDrawable(resources, R.color.white, null )
    }

    fun setBackgroundAfterDisplayImageAdded(){
        uploadFirstPhotosBinding.addedImage1.visibility = View.VISIBLE
        uploadFirstPhotosBinding.icAdd1.visibility = View.GONE
        uploadFirstPhotosBinding.addedImage1.background  =
            ResourcesCompat.getDrawable(resources, R.color.white, null )
    }

    /**Method that handles the immediate afterwards of the sign in/up process*/
    fun signInUserSuccess(context: Context) {
        toastMessage("Welcome!", context)
        hideProgressDialog()

        startActivity(Intent(requireActivity(), MainActivity::class.java))
        requireActivity().finish()
    }
}
