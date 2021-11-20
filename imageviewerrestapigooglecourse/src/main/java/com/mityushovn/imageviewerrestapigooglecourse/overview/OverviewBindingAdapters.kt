package com.mityushovn.imageviewerrestapigooglecourse.overview

import android.graphics.Color
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("textColorIfIWant")
fun textColorIfIWant(tv: TextView, i: Int) {
    when (i) {
        1 -> tv.setTextColor(Color.RED)
        else ->
            tv.setTextColor(Color.GREEN)

    }
}