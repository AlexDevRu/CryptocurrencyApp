package com.example.kulakov_p4_cryptocurrency_app.binding_adapters

import androidx.databinding.BindingAdapter
import com.example.kulakov_p4_cryptocurrency_app.views.custom.CustomLineChart


@BindingAdapter(value = ["data", "labels"], requireAll = true)
fun bindGraphViewData(lineChart: CustomLineChart, values: List<Double>, labels: Array<String>) {
    lineChart.clearAndSetSingleDataSet(values)
    lineChart.setXAxisValues(labels)
}

@BindingAdapter(value = ["dataList", "labels", "legendLabels"], requireAll = true)
fun bindGraphViewDataList(lineChart: CustomLineChart, valuesList: List<List<Double>?>, labels: Array<String>, legendLabels: List<String?>) {
    lineChart.setXAxisValues(labels)
    lineChart.setDataSetList(valuesList)
    lineChart.addLegendLabels(legendLabels)
}
