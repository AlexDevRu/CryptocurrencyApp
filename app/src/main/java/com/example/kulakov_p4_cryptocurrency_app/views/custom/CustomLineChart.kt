package com.example.kulakov_p4_cryptocurrency_app.views.custom

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.view.children
import com.example.kulakov_p4_cryptocurrency_app.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet


class CustomLineChart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {

    private var legendContainer: LinearLayout
    private var lineChart: LineChart

    companion object {
        val DEFAULT_COLORS = arrayOf(R.color.purple_700, R.color.teal_700)
    }

    private val checkBoxMap = mutableMapOf<CheckBox, LineDataSet>()

    private fun setDefaultConfig() {
        lineChart.apply {
            xAxis.setDrawGridLines(false)
            xAxis.textSize = 14f
            extraBottomOffset = 4f
            extraLeftOffset = 5f
            extraRightOffset = 5f
            axisLeft.setDrawGridLines(false)
            axisRight.setDrawGridLines(false)
            description.isEnabled = false
            legend.isEnabled = false
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            axisLeft.isEnabled = false
            marker = CustomMarkerView(context, R.layout.layout_tooltip)
            setNoDataText(context.getString(R.string.no_data))
        }
    }

    init {
        val view = inflate(context, R.layout.layout_linear_graph, this)
        legendContainer = view.findViewById(R.id.legendContainer)
        lineChart = view.findViewById(R.id.graph)
        setDefaultConfig()
    }

    fun setXAxisValues(labels: Array<String>) {
        val formatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                return labels[value.toInt()]
            }
        }

        lineChart.xAxis.valueFormatter = formatter
    }

    fun setDataSetList(valuesList: List<List<Double>?>) {
        if(valuesList.isNullOrEmpty())
            return

        legendContainer.removeAllViews()
        checkBoxMap.clear()

        val lineDataSets = mutableListOf<ILineDataSet>()

        for((i, values) in valuesList.withIndex()) {
            val color = DEFAULT_COLORS[i % DEFAULT_COLORS.size]
            val dataSet = setDataSet(values, color)
            if(dataSet != null) {
                lineDataSets.add(dataSet)
                val checkBox = addCheckBox(color)
                checkBoxMap.put(checkBox, dataSet)
            }
        }

        val lineData = LineData(lineDataSets)
        lineData.setDrawValues(false)

        lineChart.data = lineData

        lineChart.invalidate()
    }

    private fun addCheckBox(color: Int = DEFAULT_COLORS[0]): CheckBox {
        val checkBox = CheckBox(context)
        val colorValue = context.resources.getColor(color)
        checkBox.buttonTintList = ColorStateList.valueOf(colorValue)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.marginEnd = 10
        checkBox.layoutParams = layoutParams
        checkBox.isChecked = true
        checkBox.setOnCheckedChangeListener { checkbox, b ->
            onCheckedChangeCheckBox(checkBox, b)
        }
        legendContainer.addView(checkBox)
        return checkBox
    }

    fun addLegendLabels(legendLabels: List<String?>) {
        var i = 0
        legendContainer.children.forEach {
            if(legendLabels[i] != null)
                (it as CheckBox).text = legendLabels[i]
            ++i
        }
    }

    private fun onCheckedChangeCheckBox(checkBox: CheckBox, checked: Boolean) {
        if(checked)
            lineChart.lineData.addDataSet(checkBoxMap[checkBox])
        else
            lineChart.lineData.removeDataSet(checkBoxMap[checkBox])
        lineChart.invalidate()
    }

    fun clearAndSetSingleDataSet(values: List<Double>?) {
        legendContainer.removeAllViews()
        val dataSet = setDataSet(values)
        lineChart.data = LineData(dataSet)
        lineChart.lineData.setDrawValues(false)
        checkBoxMap.clear()
        legendContainer.removeAllViews()
        lineChart.invalidate()
    }

    private fun setDataSet(values: List<Double>?, color: Int = DEFAULT_COLORS[0]): LineDataSet? {

        if(values.isNullOrEmpty())
            return null

        val entries = mutableListOf<Entry>()
        for ((i, value) in values.withIndex()) {
            val entry = Entry(i.toFloat(), value.toFloat())
            entries.add(entry)
        }
        val dataSet = LineDataSet(entries, null)
        dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        dataSet.color = context.resources.getColor(color)
        dataSet.lineWidth = 3f
        dataSet.circleRadius = 4f
        dataSet.setCircleColor(Color.BLUE)
        return dataSet
    }
}
