package com.my_project.moviesapp.utilities

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Point
import android.util.Base64
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.my_project.moviesapp.presentation.main.MainActivity

/**
 * Created Максим on 27.10.2019.
 * Copyright © Max
 */

val Fragment.mainActivity: MainActivity?
    get() = activity as MainActivity?


fun View.visible() {
    if (visibility != View.VISIBLE)
        visibility = View.VISIBLE
}

fun View.invisible() {
    if (visibility != View.INVISIBLE)
        visibility = View.INVISIBLE
}

fun View.gone() {
    if (visibility != View.GONE)
        visibility = View.GONE
}

fun Intent.addClearStackFlags(): Intent {
    flags = Intent.FLAG_ACTIVITY_NEW_TASK or
            Intent.FLAG_ACTIVITY_NO_ANIMATION or
            Intent.FLAG_ACTIVITY_CLEAR_TASK

    return this
}

fun Context.isAppInForeground(): Boolean {
    val activityManager =
        getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val appProcesses = activityManager.runningAppProcesses
    for (appProcess in appProcesses) {
        if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
            return true
        }
    }
    return false
}


fun Activity.hideSoftKeyboard(view: View) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.showSoftKeyboard(editTextAuthPhoneNumber: EditText) {
    val imm =
        editTextAuthPhoneNumber.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(editTextAuthPhoneNumber, InputMethodManager.SHOW_IMPLICIT)
}

fun Int.dpToPx(): Int {
    return Math.round(this * Resources.getSystem().displayMetrics.density)
}

fun Int.pxToDp(): Int {
    return Math.round(this / Resources.getSystem().displayMetrics.density)
}

inline fun Spinner.setOnItemSelectedListener(crossinline onItemSelected: (parent: AdapterView<*>?, view: View?, position: Int, id: Long) -> Unit)
        : AdapterView.OnItemSelectedListener {

    val listener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            onItemSelected(parent, view, position, id)
        }

    }

    onItemSelectedListener = listener

    return listener
}

fun Context.bitmapFromVectorDrawable(@DrawableRes drawableId: Int): Bitmap {
    val drawable = checkNotNull(ContextCompat.getDrawable(this, drawableId))
    val bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}

fun Context.decodeBitmap(image: String): Bitmap {
    val decodedString = Base64.decode(image, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
}


fun SwitchCompat.status(check: Boolean) {
    this.isChecked = check
}

fun Context.getScreenWidth(): Int {
    val size = Point()
    (this as Activity).windowManager.defaultDisplay.getSize(size)
    return size.x
}

fun Context.getScreenHeight(): Int {
    val size = Point()
    (this as Activity).windowManager.defaultDisplay.getSize(size)
    return size.y - getStatusBarHeight()
}

fun Context.getStatusBarHeight(): Int {
    var result = 0
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = resources.getDimensionPixelSize(resourceId)
    }
    return result
}
