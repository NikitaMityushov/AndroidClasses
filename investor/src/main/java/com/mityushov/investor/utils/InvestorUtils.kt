package com.mityushov.investor.utils

import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import com.mityushov.investor.R

class InvestorUtils {
}

fun setTextColorRedOrGreen(value: Float, view: TextView) {
    view.apply {
        if (value < 0) {
            setTextColor(Color.RED)
        } else if (value > 0) {
            setTextColor(Color.GREEN)
        }
    }
}

fun setArrowImageRedOrGreen(value: Float, view: ImageView): Int {
    view.apply {
        return if (value < 0) {
            R.drawable.red_arrow_down
        } else if (value > 0) {
            R.drawable.green_arrow_up
        } else {
            R.drawable.blue_arrow_right
        }
    }
}