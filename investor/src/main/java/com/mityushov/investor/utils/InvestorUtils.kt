package com.mityushov.investor.utils

import android.widget.TextView
import com.mityushov.investor.R

class InvestorUtils {
}

fun setTextColorRedOrGreen(value: Float, view: TextView) {
    view.apply {
        if (value < 0) {
            setTextColor(resources.getColor(R.color.red))
        } else if (value > 0) {
            setTextColor(resources.getColor(R.color.green))
        }
    }
}