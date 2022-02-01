package com.mityushovn.chartdrawing

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

private const val STROKE_WIDTH = 12f
private const val OFFSET = 0.1f // коэффициент для оффсета

class LinearChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
): View(context, attrs) {
    //
    var offsetX: Float = 0.0f
    var offsetY: Float = 0.0f

    var startAxisX: Float = 0.0f
    var startAxisY: Float = 0.0f
    var endAxisX: Float = 0.0f
    var endAxisY: Float = 0.0f


    private val paint: Paint = Paint().apply {
        strokeWidth = STROKE_WIDTH
        color = Color.GREEN
        isAntiAlias = true
        style = Paint.Style.STROKE // default: FILL
    }

    private val path = Path()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        offsetX = OFFSET * w.toFloat()
        offsetY = OFFSET * h.toFloat()

        startAxisY = h - offsetY
        startAxisX = offsetX

        endAxisY = offsetY
        endAxisX = w - offsetX
    }

    override fun onDraw(canvas: Canvas?) {
//        path.addCircle(120f, 120f, 100f, Path.Direction.CCW)
        // 1) draw axisX
        canvas?.drawLine(startAxisX, startAxisY, endAxisX, height -offsetY, paint)
        // 2) draw axisY
        canvas?.drawLine(startAxisX, startAxisY, offsetX, endAxisY, paint)
//        canvas?.drawPath(path, paint)
    }

    private fun drawAxis(canvas: Canvas) {

    }

}