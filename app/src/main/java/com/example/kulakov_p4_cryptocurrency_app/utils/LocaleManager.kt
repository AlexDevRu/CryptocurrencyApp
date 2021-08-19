package com.example.kulakov_p4_cryptocurrency_app.utils

import android.app.Activity
import android.content.res.Configuration
import java.util.*

object LocaleManager {
    fun setLocale(activity: Activity, lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        //activity.applicationContext.createConfigurationContext(config)
        activity.baseContext.resources.updateConfiguration(config, activity.baseContext.resources.displayMetrics)
        //activity.recreate()
    }
}