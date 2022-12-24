package ru.macdroid.binlist.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.openFragmentFromActivity(layout: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction().add(layout, fragment).commit()
}