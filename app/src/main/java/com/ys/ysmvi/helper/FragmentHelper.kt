package com.example.mvvm.helper

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.setOnBackPressed(fragment: Fragment, result: () -> Unit) {
    this.onBackPressedDispatcher.addCallback(fragment, object: OnBackPressedCallback(true) {
        override fun handleOnBackPressed() { result() }
    })
}
