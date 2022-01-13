package com.mityushovn.canvaslab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val myViewCanvas = MyViewCanvas(this)
        myViewCanvas.systemUiVisibility = SYSTEM_UI_FLAG_FULLSCREEN
        myViewCanvas.contentDescription = getString(R.string.canvasContentDescription)

        setContentView(myViewCanvas)
    }
}