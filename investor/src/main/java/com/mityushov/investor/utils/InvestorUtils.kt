package com.mityushov.investor.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.mityushov.investor.R

fun setTextColorRedOrGreen(value: Float, view: TextView) {
    view.apply {
        if (value < 0) {
            setTextColor(Color.RED)
        } else if (value > 0) {
            setTextColor(Color.GREEN)
        }
    }
}

fun setArrowImageRedOrGreenOrGrey(value: Float, view: ImageView): Int {
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

fun ObjectAnimator.disableViewDuringAnimation(view: View) {
    addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationStart(animation: Animator?) {
            view.isEnabled = false
        }

        override fun onAnimationEnd(animation: Animator?) {
            view.isEnabled = true
        }
    })
}