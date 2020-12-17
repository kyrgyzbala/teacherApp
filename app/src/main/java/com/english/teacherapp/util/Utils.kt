package com.english.teacherapp.util

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.english.teacherapp.R
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

const val CHOOSE_IMAGE_REQUEST = 121

const val EXTRA_CODE_SENT_PWD = "EXTRA_CODE_SENT_PWD"
const val EXTRA_MODEL_TOKEN = "EXTRA_MODEL_TOKEN"
const val EXTRA_LEVEL = "EXTRA_LEVEL"
const val EXTRA_TYPE = "EXTRA_TYPE"
const val EXTRA_TEACHER_ID = "EXTRA_TEACHER_ID"
const val EXTRA_QUIZ = "EXTRA_QUIZ"
const val EXTRA_QUIZ_REF = "EXTRA_QUIZ_REF"

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun RelativeLayout.show() {
    visibility = View.VISIBLE
}

fun RelativeLayout.hide() {
    visibility = View.GONE
}

fun View.showRegSnackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).setTextColor(Color.WHITE).show()
}

fun ProgressBar.show() {
    visibility = View.VISIBLE
}

fun ProgressBar.hide() {
    visibility = View.GONE
}

fun Activity.freezeScreen() {
    window.setFlags(
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
    )
}

fun Activity.removeFreezeScreen() {
    window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}

fun Fragment.hideKeyboard() {
    view?.let {
        activity?.hideKeyboard(it)
    }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun getDateToday(context: Context, date: String, type: Int): String {

    val calendar = Calendar.getInstance()
    val sdf = if (type == 1)
        SimpleDateFormat(context.getString(R.string.ddMMyyyy), Locale.ROOT)
    else
        SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
    val today = sdf.format(calendar.time)
    val yesterday = sdf.format(calendar.time.time - 86400000)

    Log.d("NNNNN", "getDateToday (line 117): $date $today")

    return if (today == date)
        context.getString(R.string.today)
    else if (yesterday == date)
        context.getString(R.string.yesterday)
    else
        getDateMonth(date, context, type)
}

fun getDateMonth(date: String, context: Context, type: Int): String {

    if (type == 1) {
        val year = date.takeLast(4)
        val day = date.take(2)
        val temp = date.take(5)
        val month = getMonth(context, temp.takeLast(2))
        return "$day $month $year"
    } else {
        val year = date.take(4)
        val day = date.takeLast(2)
        val temp = date.takeLast(5)
        val month = getMonth(context, temp.take(2))
        return "$day $month $year"
    }
}

fun getMonth(context: Context, month: String): String {
    return when (month) {
        "01" -> context.getString(R.string.jan)
        "02" -> context.getString(R.string.feb)
        "03" -> context.getString(R.string.march)
        "04" -> context.getString(R.string.apr)
        "05" -> context.getString(R.string.may)
        "06" -> context.getString(R.string.june)
        "07" -> context.getString(R.string.july)
        "08" -> context.getString(R.string.aug)
        "09" -> context.getString(R.string.sep)
        "10" -> context.getString(R.string.oct)
        "11" -> context.getString(R.string.nov)
        else -> context.getString(R.string.dec)
    }
}

fun getTimeToShow(diff: Long, context: Context): String {
    val diffM = diff / 60
    val diffH = diffM / 60
    val diffD = diffH / 24
    val diffMonth = diffD / 30
    val diffYear = diffMonth / 12
    return when {
        diffYear > 0 -> "$diff ${context.getString(R.string.y)}"
        diffMonth > 0 -> "$diffMonth ${context.getString(R.string.m)}"
        diffD > 0 -> "$diffD ${context.getString(R.string.d)}"
        diffH > 0 -> "$diffH ${context.getString(R.string.h)}"
        diffM > 0 -> "$diffM ${context.getString(R.string.min)}"
        else -> "$diff ${context.getString(R.string.s)}"
    }
}