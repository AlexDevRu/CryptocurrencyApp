package com.example.kulakov_p4_cryptocurrency_app.binding_adapters

import android.graphics.Typeface
import androidx.databinding.BindingAdapter
import com.example.kulakov_p4_cryptocurrency_app.ui_models.DataTableItem
import ir.androidexception.datatable.DataTable
import ir.androidexception.datatable.model.DataTableHeader
import ir.androidexception.datatable.model.DataTableRow
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@BindingAdapter(value=["rowsList", "headerList"], requireAll = true)
fun DataTable.build(rowsList: List<DataTableItem>?, headerList: Array<String>) {
    if(rowsList.isNullOrEmpty() || headerList.isEmpty())
        return

    var headerBuilder = DataTableHeader.Builder()

    for(headerItem in headerList)
        headerBuilder = headerBuilder.item(headerItem, 1)

    val rows = ArrayList<DataTableRow>()
    for(rowItem in rowsList) {
        var row = DataTableRow.Builder()
            .value(context.resources.getString(rowItem.title))

        var value = when(rowItem.value) {
            is Double -> "%.4f".format(rowItem.value.toDouble()).replace(Regex(",?0*$"), "")
            is Date -> SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(rowItem.value)
            is Int -> rowItem.value.toString()
            else -> ""
        }

        if(rowItem.isMoney) value = "$${value}"

        row = row.value(value)

        rows.add(row.build())
    }

    isScrollContainer = false
    typeface = Typeface.SANS_SERIF
    rowTextSize = 14f
    headerTextSize = 14f
    header = headerBuilder.build()
    this.rows = rows
    inflate(context)
}