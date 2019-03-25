package com.goldenpiedevs.schedule.app.ui.view

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.hideSoftKeyboard() {
    try {
        val inputMethodManager = getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        val v = currentFocus
        if (v != null) {
            inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
        }
    } catch (e: Exception) {
    }

}

fun androidx.fragment.app.Fragment.hideSoftKeyboard() {
    try {
        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)
    } catch (e: Exception) {
    }

}

fun AppCompatActivity.showSoftKeyboard() {
    try {
        val inputMethodManager = getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(currentFocus, 0)
    } catch (e: Exception) {
    }

}

fun Context.showSoftKeyboard(editText: EditText) {
    try {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED)
    } catch (e: Exception) {
    }

}

fun Context.hideSoftKeyboard(editText: EditText?) {
    try {
        val inputMethodManager = getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (editText != null) {
            inputMethodManager.hideSoftInputFromWindow(editText.windowToken, 0)
        }
    } catch (e: Exception) {
    }

}

fun Context.hideSoftKeyboard(vararg views: View) {
    try {
        val inputMethodManager = getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        for (view in views) {
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    } catch (e: Exception) {
    }

}

fun AppCompatActivity.hideSoftKeyboard(vararg views: View) {
    hideSoftKeyboard()

    for (view in views) {
        view.clearFocus()
    }
}