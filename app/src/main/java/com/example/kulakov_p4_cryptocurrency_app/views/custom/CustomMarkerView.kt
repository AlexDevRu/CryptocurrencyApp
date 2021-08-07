package com.example.kulakov_p4_cryptocurrency_app.views.custom

import android.content.Context
import android.widget.TextView
import com.example.kulakov_p4_cryptocurrency_app.R
import com.example.kulakov_p4_cryptocurrency_app.binding_adapters.usd
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF

class CustomMarkerView(context: Context?, layoutResource: Int) :
    MarkerView(context, layoutResource) {
    private val tvContent: TextView = findViewById(R.id.tooltip)

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    override fun refreshContent(e: Entry, highlight: Highlight) {
        tvContent.usd(e.y.toDouble())
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF.getInstance(-(width / 2f), -height.toFloat())
    }
}