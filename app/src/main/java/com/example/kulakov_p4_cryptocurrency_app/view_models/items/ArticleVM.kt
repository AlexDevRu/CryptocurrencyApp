package com.example.kulakov_p4_cryptocurrency_app.view_models.items

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.domain.models.news.Article

class ArticleVM: BaseObservable() {
    @Bindable
    var article: Article? = null
        set(value) {
            field = value
            notifyChange()
        }
}