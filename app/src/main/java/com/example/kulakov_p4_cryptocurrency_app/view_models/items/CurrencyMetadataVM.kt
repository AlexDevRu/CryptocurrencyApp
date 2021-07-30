package com.example.kulakov_p4_cryptocurrency_app.view_models.items

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.domain.models.CurrencyMetadata

class CurrencyMetadataVM: BaseObservable() {

    @Bindable
    var currencyMetadata: CurrencyMetadata? = null
        set(value) {
            field = value
            notifyChange()
        }

    @get:Bindable
    val websiteUrls: List<String>?
        get() = currencyMetadata?.urls?.get("website")

    @get:Bindable
    val technicalDocUrls: List<String>?
        get() = currencyMetadata?.urls?.get("technical_doc")

    @get:Bindable
    val twitterUrls: List<String>?
        get() = currencyMetadata?.urls?.get("technical_doc")

    @get:Bindable
    val redditUrls: List<String>?
        get() = currencyMetadata?.urls?.get("reddit")

    @get:Bindable
    val messageBoardUrls: List<String>?
        get() = currencyMetadata?.urls?.get("message_board")

    @get:Bindable
    val announcementUrls: List<String>?
        get() = currencyMetadata?.urls?.get("announcement")

    @get:Bindable
    val chatUrls: List<String>?
        get() = currencyMetadata?.urls?.get("chat")

    @get:Bindable
    val explorerUrls: List<String>?
        get() = currencyMetadata?.urls?.get("explorer")

    @get:Bindable
    val sourceCodeUrls: List<String>?
        get() = currencyMetadata?.urls?.get("source_code")

    @get:Bindable
    val description:String?
        get() = currencyMetadata?.description
}