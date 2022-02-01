package com.mityushovn.chartdrawing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import android.graphics.Color
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 1) init LineChart
        val chart: LineChart = findViewById(R.id.line_chart)
        // 2) create some data
        val data1 = SomeData(150.0f, 150.0f)
        val data2 = SomeData(200.0f, 200.0f)
        // 3) add data in an array
        val array = arrayOf(data1, data2)
        // 4) create a list of entry
        val entries: MutableList<Entry> = mutableListOf()
        // 5) add array items x and y to the list of entries
        for (item in array) {
            entries.add(Entry(item.x, item.y))
        }
        // 6) create a dataset for styling
        val dataset = LineDataSet(entries, "Label111").apply {
            // styling
            color = Color.GREEN
            valueTextColor = Color.RED
        }
        // 7) ???
        val lineData = LineData(dataset)
        chart.data = lineData
        chart.invalidate()

    }
}

// data
data class SomeData(
    val x: Float = 0.0f,
    val y: Float = 0.0f
)