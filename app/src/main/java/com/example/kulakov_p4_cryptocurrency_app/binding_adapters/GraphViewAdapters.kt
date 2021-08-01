package com.example.kulakov_p4_cryptocurrency_app.binding_adapters

import androidx.databinding.BindingAdapter
import com.example.kulakov_p4_cryptocurrency_app.R
import com.example.kulakov_p4_cryptocurrency_app.views.custom.CustomMarkerView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter


@BindingAdapter(value = ["data", "labels"], requireAll = true)
fun bindGraphViewData(lineChart: LineChart, data: List<Double>, labels: Array<String>) {
    val entries = ArrayList<Entry>()
    for (i in data.indices) {
        entries.add(Entry(i.toFloat(), data[i].toFloat()))
    }
    val dataSet = LineDataSet(entries, null)
    dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
    dataSet.color = R.color.blue
    dataSet.lineWidth = 3f
    dataSet.circleRadius = 4f
    val lineData = LineData(dataSet)
    lineData.setDrawValues(false)
    lineChart.data = lineData
    lineChart.xAxis.setDrawGridLines(false)
    lineChart.xAxis.textSize = 14f
    lineChart.extraBottomOffset = 4f
    lineChart.axisLeft.setDrawGridLines(false)
    lineChart.axisRight.setDrawGridLines(false)
    lineChart.description.isEnabled = false
    lineChart.legend.isEnabled = false
    lineChart.setNoDataText(lineChart.resources.getString(R.string.no_data))

    val formatter = object : ValueFormatter() {
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return labels[value.toInt()]
        }
    }

    lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM

    lineChart.xAxis.valueFormatter = formatter

    lineChart.axisLeft.isEnabled = false

    lineChart.invalidate()

    val mv = CustomMarkerView(lineChart.context, R.layout.layout_tooltip)
    lineChart.marker = mv
}
