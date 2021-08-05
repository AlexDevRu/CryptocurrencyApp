package com.example.kulakov_p4_cryptocurrency_app.binding_adapters

import android.graphics.Color
import androidx.databinding.BindingAdapter
import com.example.kulakov_p4_cryptocurrency_app.R
import com.example.kulakov_p4_cryptocurrency_app.views.custom.CustomMarkerView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter

@BindingAdapter("noDataMessage")
fun setNoDataMessage(lineChart: LineChart, message: String) {
    lineChart.setNoDataText(message)
    lineChart.invalidate()
}


@BindingAdapter(value = ["data", "labels"], requireAll = true)
fun bindGraphViewData(lineChart: LineChart, data: List<Double>, labels: Array<String>) {
    val entries = ArrayList<Entry>()
    for (i in data.indices) {
        entries.add(Entry(i.toFloat(), data[i].toFloat()))
    }
    val dataSet = LineDataSet(entries, null)
    dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
    dataSet.color = Color.MAGENTA
    dataSet.lineWidth = 3f
    dataSet.circleRadius = 4f
    dataSet.setCircleColor(Color.BLUE)
    val lineData = LineData(dataSet)
    lineData.setDrawValues(false)
    lineChart.data = lineData
    lineChart.xAxis.setDrawGridLines(false)
    lineChart.xAxis.textSize = 14f
    lineChart.extraBottomOffset = 4f
    lineChart.extraLeftOffset = 3f
    lineChart.axisLeft.setDrawGridLines(false)
    lineChart.axisRight.setDrawGridLines(false)
    lineChart.description.isEnabled = false
    lineChart.legend.isEnabled = false

    val formatter = object : ValueFormatter() {
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return labels[value.toInt()]
        }
    }
    lineChart.xAxis.valueFormatter = formatter

    lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM

    lineChart.axisLeft.isEnabled = false

    lineChart.invalidate()

    val mv = CustomMarkerView(lineChart.context, R.layout.layout_tooltip)
    lineChart.marker = mv
}

@BindingAdapter(value = ["dataList", "labels", "legendLabels"], requireAll = false)
fun bindGraphViewDataList(lineChart: LineChart, dataList: List<List<Double>?>, labels: Array<String>, legendLabels: List<String?>? = null) {

    val colors = arrayOf(R.color.purple_700, R.color.teal_700)

    if(dataList.isNullOrEmpty())
        return

    val dataSets = mutableListOf<LineDataSet>()

    var counter = 0

    for(data in dataList) {
        if(data == null) continue
        val entries = mutableListOf<Entry>()
        for (i in data.indices) {
            val entry = Entry(i.toFloat(), data[i].toFloat())
            entries.add(entry)
        }
        val dataSet = LineDataSet(entries, null)
        dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        dataSet.color = lineChart.context.resources.getColor(colors[counter++ % colors.size])
        dataSet.lineWidth = 3f
        dataSet.circleRadius = 4f
        dataSet.setCircleColor(Color.BLUE)
        dataSets.add(dataSet)
    }

    val lineData = LineData()
    for(dataSet in dataSets)
        lineData.addDataSet(dataSet)

    lineData.setDrawValues(false)

    lineChart.data = lineData
    lineChart.xAxis.setDrawGridLines(false)
    lineChart.xAxis.textSize = 14f
    lineChart.extraBottomOffset = 4f
    lineChart.extraLeftOffset = 5f
    lineChart.axisLeft.setDrawGridLines(false)
    lineChart.axisRight.setDrawGridLines(false)
    lineChart.description.isEnabled = false
    lineChart.legend.textSize = 16f

    val legendEntries = mutableListOf<LegendEntry>()

    if(legendLabels != null) {
        for(i in dataSets.indices) {
            val legendEntry = LegendEntry()
            legendEntry.label = legendLabels[i]
            legendEntry.form = Legend.LegendForm.SQUARE
            legendEntry.formSize = 16f
            legendEntry.formColor = lineChart.context.resources.getColor(colors[i])
            legendEntries.add(legendEntry)
        }

        lineChart.legend.yOffset = 15f

        lineChart.legend.setCustom(legendEntries)
    } else {
        lineChart.legend.isEnabled = false
    }

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