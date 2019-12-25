package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {
    private const val NAME = "Music"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    // list of app specific preferences
    private val IS_FIRST_RUN_PREF = Pair("is_first_run", false)
    private val DB_INIT=false
    private val SONG_MODE="classic"
    var IMAGES=intArrayOf(R.drawable.i1, R.drawable.i2,R.drawable.i3,R.drawable.i4,R.drawable.i5,R.drawable.i6,R.drawable.i7,R.drawable.i8,R.drawable.i9,R.drawable.i10,
        R.drawable.i11,R.drawable.i12,R.drawable.i13,R.drawable.i14,R.drawable.i15,R.drawable.i16,
        R.drawable.i17,R.drawable.i18,R.drawable.i19,R.drawable.i20,R.drawable.i21,R.drawable.i22,
        R.drawable.i23,R.drawable.i24,R.drawable.i25,R.drawable.i26,R.drawable.i27,R.drawable.i28,
        R.drawable.i29,R.drawable.i30,R.drawable.i31,R.drawable.i32,R.drawable.i33,R.drawable.i34,
        R.drawable.i35,R.drawable.i36,R.drawable.i37,R.drawable.i38,R.drawable.i39)

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    /*
        SharedPreferences extension function, so I won't need to call edit() and apply()
       every SharedPreferences operation.
     */
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var firstRun: Boolean
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getBoolean(IS_FIRST_RUN_PREF.first, IS_FIRST_RUN_PREF.second)

        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putBoolean(IS_FIRST_RUN_PREF.first, value)
        }
    var songMode: String?
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getString(SONG_MODE,"classic")


        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putString(SONG_MODE, value)
        }
}