package com.example.roomy.utils

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.io.IOException
import java.security.GeneralSecurityException

class PrefManager {
    private var pref: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private var context: Context? = null

    // shared pref mode
    private val PRIVATE_MODE = 0

    // Shared preferences file name
    private val PREF_NAME = "Roomy"

/*
    var mInstance: PrefManager? = null

    fun PrefManager(_context: Context?) {
        context = _context
        pref = context!!.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref?.edit()
    }

    @Synchronized
    fun getInstance(context: Context?): PrefManager? {
        if (mInstance == null) {
            mInstance = PrefManager(context)
        }
        return mInstance
    }*/

    fun getEmail(context: Context): String? {
        val preferences: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return preferences.getString(Constants.EMAIL, null)
    }

    fun setEmail(email: String?, context: Context) {
        val preferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(Constants.EMAIL, email)
        editor.apply()
    }

    fun getFirstName(context: Context): String? {
        val preferences: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return preferences.getString(Constants.FIRSTNAME, null)
    }

    fun setFirstname(firstName: String?, context: Context) {
        val preferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(Constants.FIRSTNAME, firstName)
        editor.apply()
    }

    fun getLastName(context: Context): String? {
        val preferences: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return preferences.getString(Constants.LASTNAME, null)
    }

    fun setLastname(lastName: String?, context: Context) {
        val preferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(Constants.LASTNAME, lastName)
        editor.apply()
    }

    fun getGender(context: Context): String? {
        val preferences: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return preferences.getString(Constants.GENDER, null)
    }

    fun setGender(gender: String?, context: Context) {
        val preferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(Constants.GENDER, gender)
        editor.apply()
    }

    fun getBirthday(context: Context): String? {
        val preferences: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return preferences.getString(Constants.BIRTHDAY, null)
    }

    fun setBirthday(birthday: String?, context: Context) {
        val preferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(Constants.BIRTHDAY, birthday)
        editor.apply()
    }

    fun getPhoneNo(context: Context): String? {
        val preferences: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return preferences.getString(Constants.PHONENO, null)
    }

    fun setPhoneNo(phoneNo: String?, context: Context) {
        val preferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(Constants.PHONENO, phoneNo)
        editor.apply()
    }

    fun getPassword(context: Context): String? {
        val preferences: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return preferences.getString(Constants.PHONENO, null)
    }

    fun setPassword(password: String?, context: Context) {
        val preferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(Constants.PASSWORD, password)
        editor.apply()
    }

    fun getCoverImage(context: Context): String? {
        val preferences: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return preferences.getString(Constants.COVER_IMAGE, null)
    }

    fun setCoverImage(password: String?, context: Context) {
        val preferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(Constants.COVER_IMAGE, password)
        editor.apply()
    }

    fun getDisplayImage(context: Context): String? {
        val preferences: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return preferences.getString(Constants.DISPLAY_IMAGE, null)
    }

    fun setDisplayImage(password: String?, context: Context) {
        val preferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(Constants.DISPLAY_IMAGE, password)
        editor.apply()
    }

    fun getInterestList(context: Context): MutableList<String> {
        val preferences: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        //Retrieve the values
        return preferences.getStringSet(Constants.INTERESTSLIST, null)!!.toMutableList()
    }

    fun setInterestList(interestList: MutableList<String>, context: Context) {
        val set = HashSet<String>();
        set.addAll(interestList);

        val preferences: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        val editor = preferences.edit()
        editor.putStringSet(Constants.INTERESTSLIST, set);
        editor.apply();
    }

    //Initialising the encrypted shared preference
   /* @Throws(GeneralSecurityException::class, IOException::class)

    fun initializeMasterKeyAndSharedPref(context: Context): SharedPreferences? {
        val masterKey: MasterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
        return EncryptedSharedPreferences.create(
            context,
            Constants.ENCRYPTED_SHARED_PREF, masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
*/


/*
    fun getEncyprtedPassword(prefManager: PrefManager, context: Context) : String?{

        //Initializing the sharedPreferences with the already set up encrypted shared pref
        val sharedPreferences = prefManager.initializeMasterKeyAndSharedPref(context);

        val passwordInSharedPref: String? = sharedPreferences
            ?.getString(Constants.ENCRYPTED_PASSWORD, "")

        return passwordInSharedPref!!
    }
*/

/*
    fun setEncryptedPassword(prefManager: PrefManager, context: Context, password: String?) {
        val sharedPreferences = prefManager.initializeMasterKeyAndSharedPref(context);

        val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.putString(Constants.ENCRYPTED_PASSWORD, password)
        editor.apply()
    }
*/
}


/*
private SharedPreferences pref;
private SharedPreferences.Editor editor;
private Context _context;

// shared pref mode
private int PRIVATE_MODE = 0;

// Shared preferences file name
private static final String PREF_NAME = "FieldMaxPro";

private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
private static final String KEY_USERID = "keyuserid";
private static final String KEY_TOKEN = "tokenkey";
private static final String KEY_PIN = "keypin";
private static final String FIRST_LAUNCH = "firstLaunch";

private static PrefManager mInstance;

public PrefManager(Context context) {
this._context = context;
pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
editor = pref.edit();
}

public static synchronized PrefManager getInstance(Context context) {
if (mInstance == null) {
mInstance = new PrefManager(context);
}
return mInstance;
}*/
