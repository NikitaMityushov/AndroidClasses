package com.mityushovn.canvaslab

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import timber.log.Timber

class GraphicView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
    ): View(context, attrs) {

    private val pointPosition: PointF = PointF(250.0f, 250.0f)
    private val radius = 100f
    private val path = Path()
    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.GREEN
        strokeWidth = 0.4f
        style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas?) {
//        super.onDraw(canvas)
        Timber.d("onDraw() is invoked")

        canvas?.drawCircle(pointPosition.x, pointPosition.y, radius, linePaint)

        path.addCircle(pointPosition.x + 50f, pointPosition.y + 100f, radius, Path.Direction.CW)
        canvas?.drawPath(path, linePaint)
    }

}